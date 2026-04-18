# Phase 2 Execution Report

## Scope

Phase 2 extends the Phase 1 workflow from isolated HumanEval-X methods to a
single integrated Java class named `BookScan`. The class combines the behavior
of:

- `Java/18` substring counting,
- `Java/23` string length,
- `Java/27` upper-lower case conversion.

The goal was not only to produce a working class, but also to compare how two
LLMs behave when three related prompts are combined into one larger prompt and
then integration-tested.

## Models and Workflow

The same two local models used in Phase 1 were retained so the comparison stays
consistent across the project:

- `Qwen2.5-Coder-1.5B-Instruct-GGUF`
- `DeepSeek-Coder-1.3B-Instruct-GGUF`

The workflow remained semi-agentic:

1. generate candidate `BookScan` code from an original combined prompt;
2. run the shared JUnit 6 integration/regression suite;
3. identify integration failures;
4. improve the prompt manually and regenerate the candidate;
5. compare the edited-prompt variants against a manually selected final
   implementation;
6. validate the selected final implementation again through `mvn clean verify`
   and `mvn -P mutation test`.

## Final Deliverables

- final production class: `src/main/java/BookScan.java`
- integration suite: `src/test/java/BookScanIntegrationTest.java`
- regression suite: `src/test/java/BookScanRegressionTest.java`
- prompt-comparison runner:
  `scripts/python/run_phase2_prompt_comparison.py`
- result CSV:
  `results/prompt_comparison_results.csv`
- raw artefacts:
  `results/artifacts/prompt-comparison/**`

## Results Overview

| Strategy | Model | Compiled | JUnit | Tests | Failed | Branch Coverage |
|---|---|---|---|---:|---:|---:|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | yes | no | 18 | 12 | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | yes | no | 18 | 10 | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | yes | no | 18 | 1 | n/a |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | yes | no | 18 | 1 | n/a |
| selected-final | Manual-Selected | yes | yes | 18 | 0 | `72 / 72 = 100.00%` |

## Failure Analysis

### Original Combined Prompt, Qwen

Qwen passed only `6 / 18` tests. The failing cases showed four systematic
integration problems:

- zero-based and non-unique line numbering in `scanByWordLength`
- raw space-splitting that breaks apostrophe and hyphen tokens
- case-sensitive/raw-substring search in `scanWord`
- no canonicalization for uppercase or alphanumeric queries
- no extracted `tokenizeWords` / `canonicalizeWord` helpers, so the final
  helper-hardening regressions fail with `NoSuchMethodException`

Representative failures:

- `scanByWordLengthKeepsMatchingLinesUniqueEvenWithManyHits`
- `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords`
- `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines`
- `scanWordFindsTrimmedQueriesNextToPunctuation`
- `scanWordHandlesAlphanumericWholeWordQueries`

### Original Combined Prompt, DeepSeek

DeepSeek passed `8 / 18` tests. It was stronger than Qwen on line numbering but
still produced integration defects:

- punctuation stripping that breaks apostrophe/hyphen preservation
- raw substring matching inside longer words
- over-counting in case-insensitive search
- no precise whole-word behavior around punctuation
- alphanumeric whole-word over-counting
- no extracted `tokenizeWords` / `canonicalizeWord` helpers, so the final
  helper-hardening regressions fail with `NoSuchMethodException`

Representative failures:

- `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords`
- `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines`
- `scanWordDoesNotMatchInsideLongerWords`
- `scanWordFindsTrimmedQueriesNextToPunctuation`
- `scanWordHandlesAlphanumericWholeWordQueries`

### Edited Combined Prompt

The edited prompt fixed the public integration bugs and reduced both models to
exactly one remaining failure. The strengthened 18-test suite now exposes a
single shared blind spot:

- `privateCanonicalizeWordRejectsBlankTokens`

This regression checks that the private `canonicalizeWord` helper collapses a
blank token to the empty string. Both edited-prompt variants returned the
original blank token instead, so they stopped at `17 / 18` instead of reaching
the selected final baseline. The edited prompt still made the following
requirements explicit and solved the large public-facing defects:

- line numbers must be one-based
- matching line lists must not contain duplicates
- apostrophes and hyphens must stay inside tokens
- `scanWord` must count whole-word matches only
- search must be case-insensitive
- trimmed queries and blank/null inputs must be handled explicitly
- helper methods from tasks `18/23/27` must remain reusable inside the class

The edited prompt therefore removed the ambiguity that caused the original
integration failures, but it still left one internal normalization rule
implicit.

## Selected Final Implementation

The final selected `BookScan` implementation preserves the behavior of the
better edited-prompt variants but makes the public API and return structures
clearer:

- immutable `ScanResult` and `WordSearchResult` records
- explicit empty-result factories for invalid inputs
- regex tokenization that preserves apostrophes and hyphens
- whole-word counting implemented by normalizing lines before using
  `howManyTimes`
- direct reuse of `strlen` and `flipCase`

`mvn clean verify` confirms:

- `18 / 18` tests passed
- `72 / 72 = 100.00%` branch coverage
- `110 / 110 = 100.00%` line coverage

## JaCoCo Closure

No JaCoCo branches or lines remain uncovered in the final Maven report. The
last gap was closed by adding helper-hardening regressions for blank-token
normalization and null tokenization behavior. Those checks separate the final
selected implementation from the raw edited-prompt candidates, which still
miss the blank-token normalization rule.

## Automated Mutation Coverage (PITest)

In addition to the Phase 1 hand-crafted `improvedMutation...` guardrails
inherited through tasks `Java/18`, `Java/23`, and `Java/27`, Phase 2 wires
PITest (`org.pitest:pitest-maven` 1.20.5 with `pitest-junit5-plugin` 1.2.3)
into an opt-in Maven profile named `mutation`. This profile is disabled by
default so that `mvn clean verify` stays fast, and it is invoked explicitly
when an automated mutation score is required:

```
mvn -f Phase2/pom.xml -P mutation test
```

PITest generates mutants against `BookScan` and re-runs the 18 JUnit 6
integration/regression tests against each one. Reports are written to
`Phase2/target/pit-reports/` in HTML, XML, and CSV form. The latest measured
result is:

- `93` mutants generated
- `79` mutants killed
- `14` mutants survived
- mutation score `84.95%`
- mutated-line coverage `110 / 110 = 100.00%`

The remaining survivors are concentrated in equivalent or defensive helper
paths:

- `canonicalizeWord`: uppercase-only and blank-token predicate mutations that
  still normalize to the same lowercase observable value
- `scanByWordLength` and `scanWord`: blank-input early-return mutations that
  collapse to the same empty observable result
- `howManyTimes`, `tokenizeWords`, and `flipCase`: defensive or effectively
  equivalent branch changes around empty input and non-letter characters

The combination of PITest (automated operator coverage on `BookScan`) and the
Phase 1 hand-crafted guardrails (targeted operator coverage on
`howManyTimes`, `strlen`, `flipCase`) therefore gives Phase 2 two
complementary mutation lenses: public-behavior regression and equivalent-mutant
triage.

## Assessment

Phase 2 confirms the main hypothesis from Phase 1: correctness depends at least
as much on the surrounding workflow as on the raw model output. The original
combined prompt was not detailed enough for reliable integration behavior, but a
small amount of explicit prompt engineering was enough to bring both models to
full JUnit success. The final selected implementation slightly outperformed the
edited-prompt variants on coverage while keeping the same observable behavior.
