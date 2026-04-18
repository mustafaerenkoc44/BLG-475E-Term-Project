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
6. validate the selected final implementation again through `mvn clean verify`.

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
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | yes | no | 15 | 8 | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | yes | no | 15 | 6 | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | yes | yes | 15 | 0 | `66 / 68 = 97.06%` |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | yes | yes | 15 | 0 | `61 / 64 = 95.31%` |
| selected-final | Manual-Selected | yes | yes | 15 | 0 | `70 / 72 = 97.22%` |

## Failure Analysis

### Original Combined Prompt, Qwen

Qwen passed only `7 / 15` tests. The failing cases showed four systematic
integration problems:

- zero-based and non-unique line numbering in `scanByWordLength`
- raw space-splitting that breaks apostrophe and hyphen tokens
- case-sensitive/raw-substring search in `scanWord`
- no canonicalization for uppercase or alphanumeric queries

Representative failures:

- `scanByWordLengthKeepsMatchingLinesUniqueEvenWithManyHits`
- `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords`
- `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines`
- `scanWordFindsTrimmedQueriesNextToPunctuation`
- `scanWordHandlesAlphanumericWholeWordQueries`

### Original Combined Prompt, DeepSeek

DeepSeek passed `9 / 15` tests. It was stronger than Qwen on line numbering but
still produced integration defects:

- punctuation stripping that breaks apostrophe/hyphen preservation
- raw substring matching inside longer words
- over-counting in case-insensitive search
- no precise whole-word behavior around punctuation
- alphanumeric whole-word over-counting

Representative failures:

- `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords`
- `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines`
- `scanWordDoesNotMatchInsideLongerWords`
- `scanWordFindsTrimmedQueriesNextToPunctuation`
- `scanWordHandlesAlphanumericWholeWordQueries`

### Edited Combined Prompt

Both edited-prompt variants passed the full `15 / 15` suite. The edited prompt
made the following requirements explicit:

- line numbers must be one-based
- matching line lists must not contain duplicates
- apostrophes and hyphens must stay inside tokens
- `scanWord` must count whole-word matches only
- search must be case-insensitive
- trimmed queries and blank/null inputs must be handled explicitly
- helper methods from tasks `18/23/27` must remain reusable inside the class

The edited prompt therefore removed the ambiguity that caused the original
integration failures.

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

- `15 / 15` tests passed
- `70 / 72 = 97.22%` branch coverage
- `109 / 110 = 99.09%` line coverage

## Residual Coverage Gaps

Only two branches remain uncovered in the final Maven JaCoCo report:

- `canonicalizeWord`: the `trimmed.isEmpty()` defensive guard
- `tokenizeWords`: the `line == null` defensive guard

These branches are private helper defenses and are not reachable through the
public API under the current specification:

- tokenization never emits blank tokens into `canonicalizeWord`
- public entry points either return early for invalid inputs or split concrete
  strings into non-null lines before tokenization

For that reason, the residual misses are documented as intentional defensive
branches rather than missing test obligations.

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

PITest generates mutants against `BookScan` and re-runs the 15 JUnit 6
integration/regression tests against each one. Reports are written to
`Phase2/target/pit-reports/` in HTML, XML, and CSV form. The GitHub Actions
workflow `phase2-ci.yml` also runs this profile as a best-effort step and
uploads the PITest reports alongside the other Phase 2 artefacts. The
combination of PITest (automated operator coverage on `BookScan`) and the
Phase 1 hand-crafted guardrails (targeted operator coverage on
`howManyTimes`, `strlen`, `flipCase`) gives Phase 2 two complementary
mutation lenses.

## Assessment

Phase 2 confirms the main hypothesis from Phase 1: correctness depends at least
as much on the surrounding workflow as on the raw model output. The original
combined prompt was not detailed enough for reliable integration behavior, but a
small amount of explicit prompt engineering was enough to bring both models to
full JUnit success. The final selected implementation slightly outperformed the
edited-prompt variants on coverage while keeping the same observable behavior.
