# Black-Box Assessment for BookScan

This document mirrors the Phase 1 manual-assessment style for the integrated
`BookScan` case.

## `scanByWordLength`

| Class | Boundary / Example | Covered By | Final Status |
|---|---|---|---|
| valid text, exact-length tokens | `"Echo code"` with length `4` | `scanByWordLengthAggregatesCountsAndLinesAcrossText` | covered |
| repeated hits on same line | `"same size size"` | `scanByWordLengthKeepsMatchingLinesUniqueEvenWithManyHits` | covered |
| apostrophe token preserved | `"can't"` with length `5` | `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords` | covered |
| hyphen token preserved | `"co-op"` with length `5` | `scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords` | covered |
| null text | `null` | `scanByWordLengthReturnsEmptyResultForInvalidInputs` | covered |
| blank text | `"   \n\t"` | `scanByWordLengthReturnsEmptyResultForInvalidInputs` | covered |
| non-positive length | `0` | `scanByWordLengthReturnsEmptyResultForInvalidInputs` | covered |

## `scanWord`

| Class | Boundary / Example | Covered By | Final Status |
|---|---|---|---|
| exact whole-word match across lines | `"Echo echo\nECHO?"` | `scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines` | covered |
| trimmed query next to punctuation | `"  test  "` | `scanWordFindsTrimmedQueriesNextToPunctuation` | covered |
| blank query | `"   "` | `scanWordRejectsBlankOrMultiTokenQueries` | covered |
| multi-token query | `"two words"` | `scanWordRejectsBlankOrMultiTokenQueries` | covered |
| null query | `null` | `scanWordReturnsEmptyResultForNullInputsAndBlankTexts` | covered |
| null text | `null` | `scanWordReturnsEmptyResultForNullInputsAndBlankTexts` | covered |
| blank text | `"   \n\t"` | `scanWordReturnsEmptyResultForNullInputsAndBlankTexts` | covered |
| uppercase query normalization | query `"ECHO"` | `scanWordCanonicalizesUppercaseQueriesBeforeWholeWordMatching` | covered |
| no inside-word matches | `the` vs `theme/there` | `scanWordDoesNotMatchInsideLongerWords` | covered |
| alphanumeric whole-word token | `ABC123` vs `abc123x` | `scanWordHandlesAlphanumericWholeWordQueries` | covered |

## Helper Methods

| Method | Class | Covered By | Final Status |
|---|---|---|---|
| `howManyTimes` | overlapping substring | `howManyTimesCountsOverlappingSubstrings` | covered |
| `howManyTimes` | null/empty/oversized needles | `howManyTimesReturnsZeroForEmptyNullOrOversizedNeedles` | covered |
| `strlen` | normal string | `strlenThrowsOnNullAndReturnsLengthOtherwise` | covered |
| `strlen` | null input | `strlenThrowsOnNullAndReturnsLengthOtherwise` | covered |
| `flipCase` | mixed-case letters | `flipCasePreservesSymbolsWhileTogglingLetters` | covered |
| `flipCase` | symbols and digits unchanged | `flipCasePreservesSymbolsWhileTogglingLetters` | covered |

## Interpretation

The final test suite covers all public-spec equivalence classes that emerged
from manual analysis. The remaining JaCoCo misses are not on public behavioral
classes; they are private defensive branches for helper methods that cannot be
reached through the current public API contract.

The original combined-prompt candidates were insufficient for several of the
classes above:

- Qwen failed 8 classes, most notably line numbering and token preservation.
- DeepSeek failed 6 classes, mostly around whole-word semantics and punctuation.

The edited prompt and the final selected implementation close all public
behavioral gaps.
