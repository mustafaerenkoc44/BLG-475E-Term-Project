# Phase 2 Report Draft

## Title

LLM-Based Integration Testing for a Composite Java `BookScan` Class:
Prompt Combination, Prompt Refinement, and Coverage-Guided Validation

## Authors

- Mustafa Eren KOC - 150190805
- Onat Baris ERCAN - 150210075

## Abstract

Phase 2 extends the first phase of the BLG 475E term project from isolated
HumanEval-X tasks to a single integrated Java class named `BookScan`. The class
must determine how many words of a given length appear in a text, on which
lines they appear, and how often a requested whole word occurs across lines. To
construct this class, we combine the semantics of three earlier HumanEval-X
tasks: substring counting (`Java/18`), string length (`Java/23`), and
upper-lower case conversion (`Java/27`). Two local public coder models,
`Qwen2.5-Coder-1.5B-Instruct-GGUF` and `DeepSeek-Coder-1.3B-Instruct-GGUF`, are
evaluated under two prompt strategies: an original combined prompt and an
edited combined prompt. The original prompt leaves several integration
contracts implicit and both models therefore fail multiple test cases. The
edited prompt makes token boundaries, line-number semantics, whole-word
matching, and invalid-input behavior explicit; both models then reach `17/18`
under the strengthened suite. The selected final implementation closes the
remaining private-helper normalization gap, passes `18/18` JUnit 6 tests,
achieves `72/72 = 100.00%` branch coverage with `110/110 = 100.00%` line
coverage in JaCoCo, and records a `79/93 = 84.95%` PITest mutation score. The
results show that for integration-heavy classes, prompt clarity and
coverage-guided validation are at least as important as the underlying model
choice.

## I. Introduction

Large language models can generate short algorithmic functions reasonably well,
but integrated classes impose a harder requirement: helper methods that are
individually correct must also compose correctly when they share data,
normalization rules, and result structures. Phase 2 therefore moves beyond the
single-method benchmark style of Phase 1 and studies a more realistic
integration problem through a custom Java class called `BookScan`.

The class is intentionally designed so that three previously isolated behaviors
must now cooperate:

- substring counting for occurrence search,
- string length calculation for word-length filtering,
- case normalization for case-insensitive lookup.

This setting lets us evaluate not only whether an LLM can generate valid code,
but whether it can preserve behavioral contracts across method boundaries.

## II. Experimental Setup

### A. Models

The same two local models from Phase 1 were retained:

1. `Qwen2.5-Coder-1.5B-Instruct-GGUF`
2. `DeepSeek-Coder-1.3B-Instruct-GGUF`

Keeping the model set fixed isolates the effect of prompt design.

### B. Prompt Strategies

Two prompt families were used.

The original combined prompt merged the three task goals into one request but
did not define many integration details. The edited combined prompt clarified:

- one-based line numbering,
- uniqueness of line lists,
- whole-word matching,
- punctuation and token boundaries,
- case-insensitive search,
- null/blank handling,
- immutable result structures.

### C. Toolchain

- Java 21
- Maven 3.9+ (Surefire 3.5, JaCoCo 0.8.12)
- JUnit 6 (`junit-platform-console-standalone-6.0.3`)
- PITest 1.20.5 with `pitest-junit5-plugin` 1.2.3 exposed through an opt-in
  Maven profile `mutation` (`mvn -P mutation test`) so that an automated
  mutation score can be produced on top of the hand-crafted Phase 1
  guardrails without slowing down the default `mvn clean verify` cycle
- PowerShell 7 + Python 3.11 helper scripts
- GitHub Actions workflow `.github/workflows/phase2-ci.yml`

## III. `BookScan` Design

The final `BookScan` API exposes two public record-based results:

- `ScanResult` for word-length scans
- `WordSearchResult` for whole-word search

The public methods are:

- `scanByWordLength`
- `scanWord`
- `howManyTimes`
- `strlen`
- `flipCase`

The design intentionally preserves helper visibility because the assignment
requires the class to contain the semantics of the three source tasks. The
integration challenge lies in how these helpers interact:

- `scanByWordLength` relies on `strlen`
- `scanWord` relies on `howManyTimes`
- uppercase token normalization can route through `flipCase`

## IV. Integration Test Strategy

The Phase 2 suite contains twelve integration tests and six focused regression
or hardening tests. The integration tests check:

- aggregation across multiple lines,
- uniqueness of matching-line lists,
- apostrophe and hyphen preservation,
- punctuation-aware whole-word matching,
- rejection of blank and multi-token queries,
- case-insensitive search,
- alphanumeric whole-word behavior,
- mixed-case normalization behavior,
- invalid-input handling.

The regression tests preserve the helper semantics inherited from Phase 1:

- overlapping substring counting,
- null/empty/oversized substring safeguards,
- symbol-preserving case flipping,
- null behavior for `strlen`,
- fresh mutable empty-list behavior for `tokenizeWords(null)`,
- blank-token collapse for `canonicalizeWord`.

## V. Results

### A. Prompt-Comparison Results

| Strategy | Model | Tests Passed | Pass Rate | Branch Coverage |
|---|---|---:|---:|---:|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `6 / 18` | `33.33%` | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `8 / 18` | `44.44%` | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `17 / 18` | `94.44%` | n/a |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `17 / 18` | `94.44%` | n/a |
| selected-final | Manual-Selected | `18 / 18` | `100.00%` | `100.00%` |

### B. Original Prompt Failure Modes

The original prompt produced integration failures that were not visible at the
single-method level.

For Qwen, the main defects were:

- zero-based and duplicated line numbers,
- broken hyphen/apostrophe handling,
- case-sensitive or raw substring search,
- missing canonicalization for uppercase and alphanumeric queries.

For DeepSeek, the main defects were:

- punctuation stripping that changes token identity,
- substring counting inside larger words,
- over-counting in case-insensitive search,
- weak punctuation handling around the query boundary.

Both original variants also failed the final helper-hardening reflection checks
because they did not expose the expected `tokenizeWords` /
`canonicalizeWord` helper behavior.

### C. Edited Prompt Improvement

The edited prompt removed these ambiguities and both models immediately cleared
all public integration scenarios. Under the final strengthened suite they stop
at `17 / 18` because they still fail one white-box hardening regression:
`privateCanonicalizeWordRejectsBlankTokens`. This is still the clearest
quantitative result of Phase 2: prompt engineering closed far more defects than
switching between the two models, but the camera-ready implementation required
one final manual tightening of a helper contract.

## VI. Coverage and Test Adequacy

The selected final implementation achieved:

- branch coverage: `72 / 72 = 100.00%`
- line coverage: `110 / 110 = 100.00%`
- mutation score: `79 / 93 = 84.95%`

Manual black-box assessment shows that all public equivalence classes are now
covered. The final hardening regressions also close the last private-helper
coverage gaps that had previously remained in JaCoCo.

PITest still reports `14` surviving mutants, but they are concentrated in
equivalent or defensive helper behavior rather than in untested public
features. In particular, uppercase-only normalization predicates in
`canonicalizeWord` and blank-input early-return predicates in `scanWord` /
`scanByWordLength` often collapse to the same externally observable result.

## VII. Discussion

Phase 2 highlights a practical difference between unit-style code generation
and integration-style code generation. When a prompt describes a single
function, an LLM can often infer the hidden assumptions from examples or common
benchmark patterns. When a prompt asks for a larger class, those assumptions
become unstable: line numbering, token boundaries, and result-schema details
must be stated explicitly or the model will fill the gaps with inconsistent
defaults.

Another important result is that the better edited-prompt variants were already
very strong on the public API. The manually selected final implementation did
not need to repair large user-visible defects; instead it consolidated the
result types, helper layout, immutable-return behavior, and blank-token
normalization rule while pushing JaCoCo to full closure.

## VIII. Conclusion

Phase 2 completes the transition from isolated HumanEval-X tasks to a more
realistic integration setting. Both models can generate a valid `BookScan`
implementation, but only after the prompt specifies the interaction contracts
that the original combined prompt left open. The final repository therefore
demonstrates two complementary lessons:

1. LLMs can be effective for integration-level code generation.
2. They still require explicit specifications, systematic integration tests, and
   coverage-aware validation to be trustworthy.

## Acknowledgement

- GitHub repository URL:
  `https://github.com/mustafaerenkoc44/BLG-475E-Term-Project`
- The Phase 1 split into engineering and analysis tracks carried over to
  Phase 2. Both authors contributed equally and cross-reviewed each
  other's deliverables before they were merged.
- Group member roles and workload distribution:
  - **Mustafa Eren KOÇ (150190805) — engineering and automation track**:
    Maven and PITest configuration, prompt-comparison harness
    (`run_phase2_prompt_comparison.py`), `BookScan` reference implementation
    and helper hardening, JaCoCo automation, GitHub Actions workflow,
    integration-test driver.
  - **Onat Barış ERCAN (150210075) — quality and analysis track**:
    original-versus-edited prompt-strategy design, `BookScan` black-box
    assessment with per-method equivalence classes, failure-mode
    classification for the original-prompt variants, integration-test
    specification, IEEE journal manuscript.
