/* @Authors
* Student Names: Mustafa Eren KOC, Onat Baris ERCAN
* Student IDs: 150190805, 150210075
*/

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BookScanRegressionTest {
    @Test
    void howManyTimesCountsOverlappingSubstrings() {
        BookScan scanner = new BookScan();

        Assertions.assertEquals(3, scanner.howManyTimes("aaaa", "aa"));
        Assertions.assertEquals(3, scanner.howManyTimes("aaa", "a"));
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
}
