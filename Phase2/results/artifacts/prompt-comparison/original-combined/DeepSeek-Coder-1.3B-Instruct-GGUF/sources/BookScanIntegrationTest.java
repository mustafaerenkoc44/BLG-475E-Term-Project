/* @Authors
* Student Names: Mustafa Eren KOC, Onat Baris ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookScanIntegrationTest {
    @Test
    void scanByWordLengthAggregatesCountsAndLinesAcrossText() {
        BookScan scanner = new BookScan();
        String text = """
                Echo code
                More echo, data.
                tiny bits and co-op
                """;

        BookScan.ScanResult result = scanner.scanByWordLength(text, 4);

        Assertions.assertEquals(7, result.totalOccurrences());
        Assertions.assertEquals(List.of(1, 2, 3), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 2, 2, 3, 3, 2), result.occurrencesByLine());
        Assertions.assertEquals(
                Map.of(
                        1, List.of("Echo", "code"),
                        2, List.of("More", "echo", "data"),
                        3, List.of("tiny", "bits")),
                result.matchedWordsByLine());
    }

    @Test
    void scanByWordLengthTreatsApostrophesAndHyphensAsSingleWords() {
        BookScan scanner = new BookScan();
        String text = """
                can't stop
                co-op rocks
                """;

        BookScan.ScanResult result = scanner.scanByWordLength(text, 5);

        Assertions.assertEquals(3, result.totalOccurrences());
        Assertions.assertEquals(List.of(1, 2), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 1, 2, 2), result.occurrencesByLine());
        Assertions.assertEquals(
                Map.of(
                        1, List.of("can't"),
                        2, List.of("co-op", "rocks")),
                result.matchedWordsByLine());
    }

    @Test
    void scanByWordLengthReturnsEmptyResultForInvalidInputs() {
        BookScan scanner = new BookScan();

        BookScan.ScanResult nullText = scanner.scanByWordLength(null, 4);
        BookScan.ScanResult blankText = scanner.scanByWordLength("   \n\t", 4);
        BookScan.ScanResult invalidLength = scanner.scanByWordLength("Echo code", 0);

        Assertions.assertEquals(0, nullText.totalOccurrences());
        Assertions.assertEquals(List.of(), nullText.matchingLines());
        Assertions.assertEquals(Map.of(), nullText.occurrencesByLine());
        Assertions.assertEquals(0, blankText.totalOccurrences());
        Assertions.assertEquals(0, invalidLength.totalOccurrences());
    }

    @Test
    void scanByWordLengthKeepsMatchingLinesUniqueEvenWithManyHits() {
        BookScan scanner = new BookScan();
        String text = """
                same size size
                none
                """;

        BookScan.ScanResult result = scanner.scanByWordLength(text, 4);

        Assertions.assertEquals(4, result.totalOccurrences());
        Assertions.assertEquals(List.of(1, 2), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 3, 2, 1), result.occurrencesByLine());
    }

    @Test
    void scanWordCountsWholeWordMatchesIgnoringCaseAcrossLines() {
        BookScan scanner = new BookScan();
        String text = """
                Echo echo
                ECHO? eco echoic
                """;

        BookScan.WordSearchResult result = scanner.scanWord(text, "echo");

        Assertions.assertEquals("echo", result.word());
        Assertions.assertEquals(3, result.totalOccurrences());
        Assertions.assertEquals(List.of(1, 2), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 2, 2, 1), result.occurrencesByLine());
    }

    @Test
    void scanWordFindsTrimmedQueriesNextToPunctuation() {
        BookScan scanner = new BookScan();
        String text = "Test, test; test.";

        BookScan.WordSearchResult result = scanner.scanWord(text, "  test  ");

        Assertions.assertEquals(3, result.totalOccurrences());
        Assertions.assertEquals(List.of(1), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 3), result.occurrencesByLine());
    }

    @Test
    void scanWordRejectsBlankOrMultiTokenQueries() {
        BookScan scanner = new BookScan();
        String text = "Echo theme the";

        BookScan.WordSearchResult blank = scanner.scanWord(text, "   ");
        BookScan.WordSearchResult multiToken = scanner.scanWord(text, "two words");

        Assertions.assertEquals("", blank.word());
        Assertions.assertEquals(0, blank.totalOccurrences());
        Assertions.assertEquals(List.of(), blank.matchingLines());
        Assertions.assertEquals("two words", multiToken.word());
        Assertions.assertEquals(0, multiToken.totalOccurrences());
        Assertions.assertEquals(List.of(), multiToken.matchingLines());
    }

    @Test
    void scanWordReturnsEmptyResultForNullInputsAndBlankTexts() {
        BookScan scanner = new BookScan();

        BookScan.WordSearchResult nullWord = scanner.scanWord("Echo line", null);
        BookScan.WordSearchResult nullText = scanner.scanWord(null, "echo");
        BookScan.WordSearchResult blankText = scanner.scanWord("   \n\t", "echo");

        Assertions.assertEquals("", nullWord.word());
        Assertions.assertEquals(0, nullWord.totalOccurrences());
        Assertions.assertEquals(List.of(), nullWord.matchingLines());
        Assertions.assertEquals("echo", nullText.word());
        Assertions.assertEquals(0, nullText.totalOccurrences());
        Assertions.assertEquals(List.of(), nullText.matchingLines());
        Assertions.assertEquals("echo", blankText.word());
        Assertions.assertEquals(0, blankText.totalOccurrences());
    }

    @Test
    void scanWordDoesNotMatchInsideLongerWords() {
        BookScan scanner = new BookScan();
        String text = """
                the theme there
                The the.
                """;

        BookScan.WordSearchResult result = scanner.scanWord(text, "the");

        Assertions.assertEquals(3, result.totalOccurrences());
        Assertions.assertEquals(List.of(1, 2), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 1, 2, 2), result.occurrencesByLine());
    }

    @Test
    void scanWordCanonicalizesUppercaseQueriesBeforeWholeWordMatching() {
        BookScan scanner = new BookScan();
        String text = """
                echo ECHO
                Echoic echo
                """;

        BookScan.WordSearchResult result = scanner.scanWord(text, "ECHO");

        Assertions.assertEquals("echo", result.word());
        Assertions.assertEquals(3, result.totalOccurrences());
        Assertions.assertEquals(List.of(1, 2), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 2, 2, 1), result.occurrencesByLine());
    }

    @Test
    void scanWordHandlesAlphanumericWholeWordQueries() {
        BookScan scanner = new BookScan();
        String text = """
                abc123 ABC123
                abc123x 123abc
                """;

        BookScan.WordSearchResult result = scanner.scanWord(text, "ABC123");

        Assertions.assertEquals("abc123", result.word());
        Assertions.assertEquals(2, result.totalOccurrences());
        Assertions.assertEquals(List.of(1), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 2), result.occurrencesByLine());
    }

    @Test
    void scanWordNormalizesMixedCaseTokensWithoutFlipArtifacts() {
        BookScan scanner = new BookScan();
        String text = "AbC aBc ABC abcX";

        BookScan.WordSearchResult result = scanner.scanWord(text, "abc");

        Assertions.assertEquals("abc", result.word());
        Assertions.assertEquals(3, result.totalOccurrences());
        Assertions.assertEquals(List.of(1), result.matchingLines());
        Assertions.assertEquals(Map.of(1, 3), result.occurrencesByLine());
    }
}
