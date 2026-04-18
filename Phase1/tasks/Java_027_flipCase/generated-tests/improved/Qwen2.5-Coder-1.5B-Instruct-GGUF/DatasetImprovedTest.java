/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatasetImprovedTest {
    @Test
    void datasetBaseTest() {
        Solution s = new Solution();
                List<Boolean> correct = Arrays.asList(
                        Objects.equals(s.flipCase(""), ""),
                        Objects.equals(s.flipCase("Hello!"), "hELLO!"),
                        Objects.equals(s.flipCase("These violent delights have violent ends"), "tHESE VIOLENT DELIGHTS HAVE VIOLENT ENDS")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedPreservesDigitsAndPunctuation_V3() {
        Solution s = new Solution();
        Assertions.assertEquals("123!aB", s.flipCase("123!Ab"),
                "Valid class V3: digits and punctuation fall through the else branch and are appended unchanged");
    }

    @Test
    void improvedEmptyStringIsPassThrough_B1() {
        Solution s = new Solution();
        Assertions.assertEquals("", s.flipCase(""),
                "Boundary B1: an empty input skips the for-each loop and yields an empty output");
    }

    @Test
    void improvedPureDigitsAndSymbolsRemainIdentical_B2() {
        Solution s = new Solution();
        Assertions.assertEquals("123!?", s.flipCase("123!?"),
                "Boundary B2: a string without alphabetic characters bypasses both case-flip branches");
    }

    @Test
    void improvedMutationNullStringThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.flipCase(null),
                "Invalid class I1 (mutation): calling string.length() on null throws an NPE before the loop starts");
    }
}
