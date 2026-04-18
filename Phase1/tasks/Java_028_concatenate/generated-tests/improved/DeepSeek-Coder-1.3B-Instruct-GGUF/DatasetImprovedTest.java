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
                        Objects.equals(s.concatenate(new ArrayList<>(List.of())), ""),
                        Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("x", "y", "z"))), "xyz"),
                        Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("x", "y", "z", "w", "k"))), "xyzwk")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSingletonEqualsOnlyElement_V2() {
        Solution s = new Solution();
        Assertions.assertEquals("abc", s.concatenate(new ArrayList<>(Arrays.asList("abc"))),
                "Valid class V2: singleton list returns its only element unchanged");
    }

    @Test
    void improvedEmptyElementsPreserveOrderWithoutSeparators_B1() {
        Solution s = new Solution();
        Assertions.assertEquals("abcd", s.concatenate(new ArrayList<>(Arrays.asList("ab", "", "cd"))),
                "Boundary B1: empty strings in the middle must not add separators to the result");
    }

    @Test
    void improvedAllEmptyElementsReturnEmpty_B2() {
        Solution s = new Solution();
        Assertions.assertEquals("", s.concatenate(new ArrayList<>(Arrays.asList("", "", ""))),
                "Boundary B2: a list of only empty strings aggregates to the empty string");
    }

    @Test
    void improvedMutationNullListThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.concatenate(null),
                "Invalid class I1 (mutation): the enhanced-for on a null list must throw NPE");
    }
}
