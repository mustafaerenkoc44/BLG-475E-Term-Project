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
                        Objects.equals(s.makePalindrome(""), ""),
                        Objects.equals(s.makePalindrome("x"), "x"),
                        Objects.equals(s.makePalindrome("xyz"), "xyzyx"),
                        Objects.equals(s.makePalindrome("xyx"), "xyx"),
                        Objects.equals(s.makePalindrome("jerry"), "jerryrrej")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedKeepsPalindromeAndHandlesSingleCharacter() {
        Solution s = new Solution();
        Assertions.assertEquals("racecar", s.makePalindrome("racecar"));
        Assertions.assertEquals("a", s.makePalindrome("a"));
    }
}


