# Phase 2 Summary

- `BookScan` final implementation completed in `src/main/java/BookScan.java`
- `15 / 15` integration and regression tests pass under both the prompt runner
  and Maven Surefire
- selected final branch coverage: `97.22%`
- original combined prompt:
  - Qwen: `7 / 15`
  - DeepSeek: `9 / 15`
- edited combined prompt:
  - Qwen: `15 / 15`, `97.06%` branch coverage
  - DeepSeek: `15 / 15`, `95.31%` branch coverage
- final selected implementation:
  - `15 / 15`, `97.22%` branch coverage

Main conclusion: prompt engineering mattered more in Phase 2 than model choice.
Once token boundaries, line numbering, and whole-word semantics were specified
explicitly, both models produced integration-test-clean code.
