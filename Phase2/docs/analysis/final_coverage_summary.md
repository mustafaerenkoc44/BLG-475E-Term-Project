# Phase 2 Coverage Summary

## Selected Final `BookScan`

- test suites: `18`
- successful tests: `18`
- branch coverage: `72 / 72 = 100.00%`
- line coverage: `110 / 110 = 100.00%`
- complexity covered: `51 / 51 = 100.00%`
- mutation score: `79 / 93 = 84.95%`

## Edited Prompt Variants

- Qwen edited-combined: `17 / 18` tests passed
- DeepSeek edited-combined: `17 / 18` tests passed

## JaCoCo Closure

The final Maven JaCoCo report leaves no missed branches or lines. The last
coverage gap was closed by adding helper-hardening regressions for blank-token
canonicalization and null tokenization.

## Residual Mutation Survivors

PITest still reports `14` surviving mutants. They cluster around equivalent or
defensive behavior in `canonicalizeWord`, `scanByWordLength`, `scanWord`,
`howManyTimes`, `tokenizeWords`, and `flipCase`, and are documented in
`mutation_assessment.md`.
