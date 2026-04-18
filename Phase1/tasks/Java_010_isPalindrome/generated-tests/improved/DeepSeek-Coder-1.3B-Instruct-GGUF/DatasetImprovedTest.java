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
    void improvedAlreadyPalindromeIsUnchanged_V1() {
        Solution s = new Solution();
        Assertions.assertEquals("racecar", s.makePalindrome("racecar"),
                "Valid class V1: an already palindromic input matches at start=0 and appends an empty reversed prefix");
        Assertions.assertEquals("aba", s.makePalindrome("aba"),
                "Valid class V1: the same early-exit happens for the smallest non-trivial palindrome");
    }

    @Test
    void improvedSingleCharacterIsOwnPalindrome_B2() {
        Solution s = new Solution();
        Assertions.assertEquals("x", s.makePalindrome("x"),
                "Boundary B2: a single character is trivially palindromic so no suffix is appended");
    }

    @Test
    void improvedNeedsOneAppendedCharacter_V2() {
        Solution s = new Solution();
        Assertions.assertEquals("catac", s.makePalindrome("cata"),
                "Valid class V2: the shortest palindrome appends only one character from the reversed prefix");
    }

    @Test
    void improvedMutationNullStringThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.makePalindrome(null),
                "Invalid class I1 (mutation): calling string.length() on null raises NPE");
    }
}
