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
                        s.countDistinctCharacters("") == 0,
                        s.countDistinctCharacters("abcde") == 5,
                        s.countDistinctCharacters("abcde" + "cade" + "CADE") == 5,
                        s.countDistinctCharacters("aaaaAAAAaaaa") == 1,
                        s.countDistinctCharacters("Jerry jERRY JeRRRY") == 5
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedAllCharactersSameIgnoringCase_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.countDistinctCharacters("aAaA"),
                "Valid class V3: case-insensitive normalization collapses 'a'/'A' to a single distinct character");
    }

    @Test
    void improvedSingleCharacterYieldsOne_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.countDistinctCharacters("Q"),
                "Boundary B2: smallest non-empty string reports exactly one distinct character");
    }

    @Test
    void improvedWhitespaceAndPunctuationCountAsDistinct_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(2, s.countDistinctCharacters("a A "),
                "Valid class V2: whitespace is a character too; 'a'/'A' collapse after Character.toLowerCase, leaving the set { 'a', ' ' } with size 2");
        Assertions.assertEquals(4, s.countDistinctCharacters("a?A! "),
                "Valid class V2b: non-alphabetic characters survive toLowerCase and add to the distinct count");
    }

    @Test
    void improvedMutationNullStringThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.countDistinctCharacters(null),
                "Invalid class I1 (mutation): DeepSeek iterates string.length() so null reference throws NPE");
    }
}
