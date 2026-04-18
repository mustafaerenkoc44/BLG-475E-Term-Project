# Phase 2 Summary

- `BookScan` final implementation completed in `src/main/java/BookScan.java`
- `18 / 18` integration and regression tests pass under both the prompt runner
  and Maven Surefire
- selected final branch coverage: `100.00%`
- selected final line coverage: `100.00%`
- selected final mutation score: `84.95%` (`79 / 93`)
- original combined prompt:
  - Qwen: `6 / 18`
  - DeepSeek: `8 / 18`
- edited combined prompt:
  - Qwen: `17 / 18`
  - DeepSeek: `17 / 18`
- final selected implementation:
  - `18 / 18`, `100.00%` branch coverage

Main conclusion: prompt engineering mattered more in Phase 2 than model choice.
Once token boundaries, line numbering, and whole-word semantics were specified
explicitly, both models produced nearly integration-test-clean code. The final
manual selection was still needed to close one private-helper normalization
regression and to reach full JaCoCo closure.
