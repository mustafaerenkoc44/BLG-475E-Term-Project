# Qwen - Integration Test Generation

## Prompt

Generate JUnit 6 integration tests for the `BookScan` API. Focus on
cross-method behavior rather than isolated helper tests only. Cover:

- multi-line aggregation,
- apostrophe and hyphen words,
- whole-word matching,
- punctuation near the query,
- uppercase queries,
- null and blank inputs,
- one regression check each for substring counting, string length, and case
  flipping.

## Response

1. Create a multi-line fixture where `scanByWordLength` must aggregate several
   line hits and preserve per-line counts.
2. Add a case with `can't` and `co-op` so tokenization proves that apostrophes
   and hyphens stay inside a word.
3. Add a case with `Echo`, `echo`, `ECHO?`, and `echoic` to ensure the search is
   case-insensitive but still whole-word only.
4. Add a case where the query is trimmed and surrounded by punctuation.
5. Add negative cases for blank, multi-token, and null inputs.
6. Keep three regression tests: overlapping substring counting, null behavior
   for `strlen`, and symbol-preserving `flipCase`.

## Usage Note

The scenario list was merged with the manual black-box assessment and then
implemented as `BookScanIntegrationTest.java` plus `BookScanRegressionTest.java`.
The final test code is stricter than the raw suggestion because it also checks
result maps and line-number ordering.
