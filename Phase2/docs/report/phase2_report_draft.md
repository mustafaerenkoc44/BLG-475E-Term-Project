# Phase 2 Report Draft

## Title

LLM-Based Integration Testing for a Composite Java `BookScan` Class:
Prompt Combination, Prompt Refinement, and Coverage-Guided Validation

## Authors

- Mustafa Eren KOC - 150190805 (approx. 65%)
- Onat Baris ERCAN - 150210075 (approx. 35%)

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
matching, and invalid-input behavior explicit; both models then pass the full
suite. The selected final implementation passes `15/15` JUnit 6 tests and
achieves `70/72 = 97.22%` branch coverage with JaCoCo. The results show that
for integration-heavy classes, prompt clarity and coverage-guided validation
are at least as important as the underlying model choice.

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
- Maven 3.9+
- JUnit 6
- JaCoCo 0.8.12
- PowerShell 7 + Python 3.11 helper scripts

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

The Phase 2 suite contains eleven integration tests and four focused regression
tests. The integration tests check:

- aggregation across multiple lines,
- uniqueness of matching-line lists,
- apostrophe and hyphen preservation,
- punctuation-aware whole-word matching,
- rejection of blank and multi-token queries,
- case-insensitive search,
- alphanumeric whole-word behavior,
- invalid-input handling.

The regression tests preserve the helper semantics inherited from Phase 1:

- overlapping substring counting,
- null/empty/oversized substring safeguards,
- symbol-preserving case flipping,
- null behavior for `strlen`.

## V. Results

### A. Prompt-Comparison Results

| Strategy | Model | Tests Passed | Pass Rate | Branch Coverage |
|---|---|---:|---:|---:|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `7 / 15` | `46.67%` | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `9 / 15` | `60.00%` | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `15 / 15` | `100.00%` | `97.06%` |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `15 / 15` | `100.00%` | `95.31%` |
| selected-final | Manual-Selected | `15 / 15` | `100.00%` | `97.22%` |

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

### C. Edited Prompt Improvement

The edited prompt removed these ambiguities and both models immediately reached
full JUnit success. This is the clearest quantitative result of Phase 2:
prompt engineering closed more defects than switching between the two models.

## VI. Coverage and Test Adequacy

The selected final implementation achieved:

- branch coverage: `70 / 72 = 97.22%`
- line coverage: `109 / 110 = 99.09%`

Manual black-box assessment shows that all public equivalence classes are now
covered. The two remaining uncovered branches are both private defensive helper
paths:

- `canonicalizeWord` when a blank token somehow reaches normalization
- `tokenizeWords` when a null line somehow reaches tokenization

These states are unreachable through the public API because invalid public
inputs are filtered earlier and tokenization never emits blank tokens. Their
presence is therefore a design-level guard rather than a missing test target.

## VII. Discussion

Phase 2 highlights a practical difference between unit-style code generation
and integration-style code generation. When a prompt describes a single
function, an LLM can often infer the hidden assumptions from examples or common
benchmark patterns. When a prompt asks for a larger class, those assumptions
become unstable: line numbering, token boundaries, and result-schema details
must be stated explicitly or the model will fill the gaps with inconsistent
defaults.

Another important result is that the better edited-prompt variants were already
very strong. The manually selected final implementation did not need to repair
large functional defects; instead it consolidated the result types, helper
layout, and immutable-return behavior while pushing coverage slightly higher.

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
- Group member roles and workload distribution:
  - Mustafa Eren KOC (150190805, approx. 65%): repository setup, local model
    workflow, Phase 2 implementation, integration-test automation, prompt
    comparison runner, coverage analysis, result aggregation.
  - Onat Baris ERCAN (150210075, approx. 35%): prompt review, black-box
    assessment support, failure classification, report refinement, final
    submission checks, CI cross-review.
