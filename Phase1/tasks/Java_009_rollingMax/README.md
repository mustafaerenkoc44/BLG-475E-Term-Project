# Java/9 - rollingMax

Difficulty: moderate
Domain: sequence-analysis
Phase 2 dependency: no
Selection reason: Prefix-dependent behavior is good for branch coverage

## Status Snapshot

- `Qwen2.5-Coder-1.5B`: base `7/8 branches (87.50%)`, improved `8/8 branches (100.00%)`
- `DeepSeek-Coder-1.3B`: base `7/8 branches (87.50%)`, improved `8/8 branches (100.00%)`
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
