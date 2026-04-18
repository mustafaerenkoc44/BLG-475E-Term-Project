# Phase 2 Coverage Summary

## Selected Final `BookScan`

- test suites: `15`
- successful tests: `15`
- branch coverage: `70 / 72 = 97.22%`
- line coverage: `109 / 110 = 99.09%`
- complexity covered: `49 / 51 = 96.08%`

## Edited Prompt Variants

- Qwen edited-combined: `66 / 68 = 97.06%`
- DeepSeek edited-combined: `61 / 64 = 95.31%`

## Residual Misses

The final Maven JaCoCo report leaves only two missed branches:

- `canonicalizeWord`: unreachable empty-token defensive guard
- `tokenizeWords`: unreachable null-line defensive guard

These branches remain documented rather than force-covered because the public
API cannot legally produce those states.
