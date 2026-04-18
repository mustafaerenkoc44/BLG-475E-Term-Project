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
                        s.howManyTimes("", "x") == 0,
                        s.howManyTimes("xyxyxyx", "x") == 4,
                        s.howManyTimes("cacacacac", "cac") == 4,
                        s.howManyTimes("john doe", "john") == 1
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedFullStringMatch_B2_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.howManyTimes("john", "john"),
                "Boundary B2 / Valid V3: source and substring of equal length yield exactly one match");
    }

    @Test
    void improvedOverlappingLongerSubstring_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(3, s.howManyTimes("aaaa", "aa"),
                "Valid class V3: overlapping windows at every starting index capture each occurrence");
    }

    @Test
    void improvedSubstringLongerThanSource_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(0, s.howManyTimes("ab", "abc"),
                "Boundary B1: the upper bound i <= string.length()-substring.length() is negative so the loop never runs");
    }

    @Test
    void improvedMutationEmptySubstringCountsPositions_I1() {
        Solution s = new Solution();
        // DeepSeek's loop substring(i, i+0) produces "" and equals("") is true at every position including length().
        Assertions.assertEquals(4, s.howManyTimes("abc", ""),
                "Invalid class I1 (mutation): every empty window counts as a match for source of length 3");
    }
}
