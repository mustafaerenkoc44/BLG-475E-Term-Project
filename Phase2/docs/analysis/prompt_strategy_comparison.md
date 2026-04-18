# Prompt Strategy Comparison

## Compared Strategies

Phase 2 compares two prompt-construction strategies for generating `BookScan`.

### 1. Original Combined Prompt

The original strategy simply merged the semantic goals of HumanEval-X tasks
`Java/18`, `Java/23`, and `Java/27` into one larger request and asked the model
to build a `BookScan` class around them.

Characteristics:

- high-level task description only
- no explicit output schema for result objects
- no precise definition of line numbering
- no precise definition of token boundaries
- no explicit rule for punctuation, apostrophes, or hyphens
- no explicit rule for whole-word matching vs raw substring matching

### 2. Edited Combined Prompt

The edited strategy kept the same overall goal but removed the ambiguity that
caused the original failures.

Added constraints:

- use one-based line numbering
- keep matching lines unique
- preserve apostrophes and hyphens as part of a token
- count whole-word matches only
- make search case-insensitive
- reject blank and multi-token queries cleanly
- return immutable result structures
- keep helper semantics from `howManyTimes`, `strlen`, and `flipCase`

## Metric Comparison

| Strategy | Model | Tests Passed | Pass Rate | Branch Coverage |
|---|---|---:|---:|---:|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `6 / 18` | `33.33%` | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `8 / 18` | `44.44%` | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `17 / 18` | `94.44%` | n/a |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `17 / 18` | `94.44%` | n/a |

## What the Original Prompt Missed

The original prompt allowed the models to make conflicting assumptions about
integration behavior:

- Qwen assumed line indices should behave like zero-based internal loop
  counters, then accidentally appended duplicates when a line had multiple
  matches.
- DeepSeek normalized case but still used substring matching, so it counted
  target words inside larger words such as `theme` and `echoic`.
- Both original variants lacked a precise punctuation policy and therefore
  mishandled `can't`, `co-op`, and trimmed queries next to punctuation.
- Both original variants also left internal helper structure under-specified,
  which is why the final helper-hardening regressions hit
  `NoSuchMethodException` for `tokenizeWords` / `canonicalizeWord`.

These are not isolated unit bugs. They are integration defects caused by
implicit contracts between tokenization, search, and result aggregation.

## Why the Edited Prompt Worked

The edited prompt improved the generation quality because it translated hidden
testing assumptions into explicit requirements. In other words, it pushed the
specification closer to the tests.

Three improvements mattered most:

1. it stated exactly what counts as a word;
2. it stated exactly what shape the results should have;
3. it stated exactly how helper behaviors should be reused across the class.

Once those requirements were explicit, both models converged toward
integration-clean public behavior. The only remaining gap under the final
strengthened suite was a white-box regression:
`privateCanonicalizeWordRejectsBlankTokens`. That final guard was not stated
explicitly in the edited prompt and therefore remained under-specified for both
models.

## Selected Final Variant

The repository keeps a manually selected final implementation in
`src/main/java/BookScan.java`. This implementation is closest to the edited
Qwen variant in coverage and to both edited variants in behavior, but adds:

- clearer helper extraction,
- immutable maps/lists at the API boundary,
- more explicit empty-result factories,
- a slightly stronger normalization path for whole-word counting.

The selected final variant therefore acts as the "camera-ready" implementation
for the report, while the prompt-comparison variants remain as experimental
artefacts. It is also the only variant that clears the full 18-test suite,
which now includes helper-hardening regressions in addition to the public
integration contract.
