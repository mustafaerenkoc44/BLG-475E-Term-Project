# Java/21 - rescaleToUnit

Difficulty: hard
Domain: numeric-normalization
Phase 2 dependency: no
Selection reason: Normalization logic includes min max edge cases

## Status Snapshot

- `Qwen2.5-Coder-1.5B`: base `2/2 branches (100.00%)`, improved `2/2 branches (100.00%)`
- `DeepSeek-Coder-1.3B`: base `2/2 branches (100.00%)`, improved `2/2 branches (100.00%)`
- Refactoring needed: no

## Checklist

- [x] Raw prompt sent to model A is logged.
- [x] Raw prompt sent to model B is logged.
- [x] Raw generated code for both models is stored without edits.
- [x] Base dataset tests are executed.
- [x] Minor base test compatibility fixes are documented.
- [x] Improved tests are created after smell review and coverage analysis.
- [x] Equivalence classes and boundary conditions are recorded.
- [x] Black-box weaknesses are listed.
- [x] Refactoring decision is documented.
