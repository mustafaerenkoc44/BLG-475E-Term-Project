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

## Scenario-to-Test Mapping

The table below maps every scenario in the model's response to the exact
JUnit 6 test method that encodes it in the final repository. Scenarios not
covered directly by the suggestion were added later from the black-box
assessment and the original-prompt failure analysis.

| # | Scenario suggested by the model | Final test method | File |
|---|---|---|---|
| 1 | word-length scan over several lines, total counts + matching-lines list | `scanByWordLengthAggregatesCountsAndLinesAcrossText` | `BookScanIntegrationTest.java` |
| 2 | token-boundary preservation for `can't` and `co-op` | `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords` | `BookScanIntegrationTest.java` |
| 3 | whole-word `echo` must not count `echoic` | `scanWordDoesNotMatchInsideLongerWords` | `BookScanIntegrationTest.java` |
| 4 | punctuation next to a word still matches after trimming | `scanWordFindsTrimmedQueriesNextToPunctuation` | `BookScanIntegrationTest.java` |
| 5a | case-insensitive search (companion to whole-word behavior) | `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines` | `BookScanIntegrationTest.java` |
| 5b | invalid inputs: null / blank text | `scanWordReturnsEmptyResultForNullInputsAndBlankTexts` | `BookScanIntegrationTest.java` |
| 5c | invalid inputs: blank / multi-token query | `scanWordRejectsBlankOrMultiTokenQueries` | `BookScanIntegrationTest.java` |
| 5d | invalid inputs for `scanByWordLength` | `scanByWordLengthReturnsEmptyResultForInvalidInputs` | `BookScanIntegrationTest.java` |
| (added during repair) | uppercase query canonicalization via `flipCase` | `scanWordCanonicalizesUppercaseQueriesBeforeWholeWordMatching` | `BookScanIntegrationTest.java` |
| (added during repair) | alphanumeric whole-word tokens | `scanWordHandlesAlphanumericWholeWordQueries` | `BookScanIntegrationTest.java` |
| (added during repair) | line-list uniqueness under repeated hits | `scanByWordLengthKeepsMatchingLinesUniqueEvenWithManyHits` | `BookScanIntegrationTest.java` |
| 6a | overlapping substring counting regression | `howManyTimesCountsOverlappingSubstrings` | `BookScanRegressionTest.java` |
| 6b | `strlen(null)` regression | `strlenThrowsOnNullAndReturnsLengthOtherwise` | `BookScanRegressionTest.java` |
| 6c | mixed-case `flipCase` regression | `flipCasePreservesSymbolsWhileTogglingLetters` | `BookScanRegressionTest.java` |
| 6d (added during repair) | guard on blank / null / oversized substring needles | `howManyTimesReturnsZeroForEmptyNullOrOversizedNeedles` | `BookScanRegressionTest.java` |
