/* @Authors
* Student Names: Mustafa Eren KOC, Onat Baris ERCAN
* Student IDs: 150190805, 150210075
*/

import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookScanRegressionTest {
    @Test
    void howManyTimesCountsOverlappingSubstrings() {
        BookScan scanner = new BookScan();

        Assertions.assertEquals(3, scanner.howManyTimes("aaaa", "aa"));
        Assertions.assertEquals(3, scanner.howManyTimes("aaa", "a"));
        Assertions.assertEquals(1, scanner.howManyTimes("echo", "echo"));
    }

    @Test
    void howManyTimesReturnsZeroForEmptyNullOrOversizedNeedles() {
        BookScan scanner = new BookScan();

        Assertions.assertEquals(0, scanner.howManyTimes("aaaa", ""));
        Assertions.assertEquals(0, scanner.howManyTimes("aa", "aaaa"));
        Assertions.assertEquals(0, scanner.howManyTimes("", "a"));
        Assertions.assertEquals(0, scanner.howManyTimes(null, "a"));
        Assertions.assertEquals(0, scanner.howManyTimes("aaaa", null));
    }

    @Test
    void flipCasePreservesSymbolsWhileTogglingLetters() {
        BookScan scanner = new BookScan();

        Assertions.assertEquals("aBc-19!", scanner.flipCase("AbC-19!"));
        Assertions.assertEquals("hello, WORLD.", scanner.flipCase("HELLO, world."));
    }

    @Test
    void strlenThrowsOnNullAndReturnsLengthOtherwise() {
        BookScan scanner = new BookScan();

        Assertions.assertEquals(5, scanner.strlen("co-op"));
        Assertions.assertThrows(NullPointerException.class, () -> scanner.strlen(null));
    }

    @Test
    void tokenizeWordsReturnsFreshMutableEmptyListForNullInput() throws Exception {
        BookScan scanner = new BookScan();
        Method tokenizeWords = BookScan.class.getDeclaredMethod("tokenizeWords", String.class);
        tokenizeWords.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<String> tokens = (List<String>) tokenizeWords.invoke(scanner, new Object[] {null});

        Assertions.assertNotNull(tokens);
        Assertions.assertTrue(tokens.isEmpty());
        tokens.add("probe");
        Assertions.assertEquals(List.of("probe"), tokens);
    }

    @Test
    void privateCanonicalizeWordRejectsBlankTokens() throws Exception {
        BookScan scanner = new BookScan();
        Method canonicalizeWord = BookScan.class.getDeclaredMethod("canonicalizeWord", String.class);
        canonicalizeWord.setAccessible(true);

        String canonical = (String) canonicalizeWord.invoke(scanner, "   ");

        Assertions.assertEquals("", canonical);
    }
}
