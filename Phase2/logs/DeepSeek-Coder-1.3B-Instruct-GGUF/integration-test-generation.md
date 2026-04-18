# DeepSeek - Integration Test Generation

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

1. Build one scenario for word-length scanning over several lines and assert
   both total counts and the list of matching lines.
2. Add token-boundary cases using `can't` and `co-op`.
3. Verify that searching for `echo` does not count `echoic`.
4. Verify that punctuation next to a word still allows a match after trimming.
5. Add invalid-input tests for null/blank text and null/blank query values.
6. Keep helper-level regression checks for overlap counting, `strlen(null)`,
   and mixed-case `flipCase`.

## Usage Note

The response was used as a scenario checklist. The final repository turns that
checklist into stricter executable assertions with exact line maps and
whole-word expectations.
