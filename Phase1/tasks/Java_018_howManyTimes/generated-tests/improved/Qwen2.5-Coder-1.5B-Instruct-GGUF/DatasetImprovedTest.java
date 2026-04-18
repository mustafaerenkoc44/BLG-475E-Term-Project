/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;
import java.time.Duration;

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
                "Valid class V3: overlapping matches are counted because the pointer advances by one character");
    }

    @Test
    void improvedSubstringLongerThanSource_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(0, s.howManyTimes("ab", "abc"),
                "Boundary B1: indexOf cannot find a longer substring and returns -1 immediately");
    }

    @Test
    void improvedMutationEmptySubstringLoopsForever_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(
                org.opentest4j.AssertionFailedError.class,
                () -> Assertions.assertTimeoutPreemptively(
                        Duration.ofMillis(500),
                        () -> s.howManyTimes("abc", "")),
                "Invalid class I1 (mutation): String.indexOf(\"\", fromIndex) clamps fromIndex to length(), so Qwen's while loop never returns -1 and diverges for an empty substring");
    }
}
