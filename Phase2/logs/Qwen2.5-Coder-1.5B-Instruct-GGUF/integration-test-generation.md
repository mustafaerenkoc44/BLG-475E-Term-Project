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
result maps, line-number ordering, and private-helper hardening rules that were
discovered during the final PITest-guided review.

## Scenario-to-Test Mapping

The table below maps every scenario in the model's response to the exact
JUnit 6 test method that encodes it in the final repository. Scenarios without
an existing method are explicitly listed as "not adopted" with a reason.

| # | Scenario suggested by the model | Final test method | File |
|---|---|---|---|
| 1 | multi-line aggregation with per-line counts | `scanByWordLengthAggregatesCountsAndLinesAcrossText` | `BookScanIntegrationTest.java` |
| 2 | `can't` / `co-op` token preservation | `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords` | `BookScanIntegrationTest.java` |
| 3a | case-insensitive whole-word search | `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines` | `BookScanIntegrationTest.java` |
| 3b | whole-word rejection inside longer words (`echoic`) | `scanWordDoesNotMatchInsideLongerWords` | `BookScanIntegrationTest.java` |
| 4 | trimmed query surrounded by punctuation | `scanWordFindsTrimmedQueriesNextToPunctuation` | `BookScanIntegrationTest.java` |
| 5a | blank / multi-token query rejected | `scanWordRejectsBlankOrMultiTokenQueries` | `BookScanIntegrationTest.java` |
| 5b | null / blank text or query handled | `scanWordReturnsEmptyResultForNullInputsAndBlankTexts` | `BookScanIntegrationTest.java` |
| 5c | invalid `scanByWordLength` inputs (null / blank / non-positive length) | `scanByWordLengthReturnsEmptyResultForInvalidInputs` | `BookScanIntegrationTest.java` |
| (added during repair) | uppercase query canonicalization via `flipCase` | `scanWordCanonicalizesUppercaseQueriesBeforeWholeWordMatching` | `BookScanIntegrationTest.java` |
| (added during repair) | alphanumeric whole-word tokens | `scanWordHandlesAlphanumericWholeWordQueries` | `BookScanIntegrationTest.java` |
| (added during hardening) | mixed-case token normalization without uppercase-only artifacts | `scanWordNormalizesMixedCaseTokensWithoutFlipArtifacts` | `BookScanIntegrationTest.java` |
| (added during repair) | line-list uniqueness under repeated hits | `scanByWordLengthKeepsMatchingLinesUniqueEvenWithManyHits` | `BookScanIntegrationTest.java` |
| 6a | overlapping substring counting regression | `howManyTimesCountsOverlappingSubstrings` | `BookScanRegressionTest.java` |
| 6b | `strlen(null)` regression | `strlenThrowsOnNullAndReturnsLengthOtherwise` | `BookScanRegressionTest.java` |
| 6c | symbol-preserving `flipCase` regression | `flipCasePreservesSymbolsWhileTogglingLetters` | `BookScanRegressionTest.java` |
| 6d (added during repair) | guard on blank / null / oversized substring needles | `howManyTimesReturnsZeroForEmptyNullOrOversizedNeedles` | `BookScanRegressionTest.java` |
| (added during hardening) | `tokenizeWords(null)` must return a fresh mutable empty list | `tokenizeWordsReturnsFreshMutableEmptyListForNullInput` | `BookScanRegressionTest.java` |
| (added during hardening) | blank private token canonicalization must collapse to `\"\"` | `privateCanonicalizeWordRejectsBlankTokens` | `BookScanRegressionTest.java` |

Scenarios "added during repair" came from the original-prompt failure analysis
and the black-box assessment; scenarios "added during hardening" came from the
final JaCoCo/PITest review and are the checks that distinguish the selected
final implementation from the raw edited-prompt variants.
