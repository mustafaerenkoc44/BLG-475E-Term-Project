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
                        s.strlen("") == 0,
                        s.strlen("x") == 1,
                        s.strlen("asdasnakj") == 9
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedWhitespaceAndPunctuationCount_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(5, s.strlen("a! b?"),
                "Valid class V3: every whitespace and punctuation character still contributes to length()");
    }

    @Test
    void improvedSingleCharacter_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.strlen("x"),
                "Boundary B1: the smallest non-empty string reports a length of one");
    }

    @Test
    void improvedOnlySpaces_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(3, s.strlen("   "),
                "Boundary B2: whitespace-only strings are still measured by raw character count");
    }

    @Test
    void improvedMutationNullStringThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.strlen(null),
                "Invalid class I1 (mutation): calling .length() on a null reference raises an NPE");
    }
}
