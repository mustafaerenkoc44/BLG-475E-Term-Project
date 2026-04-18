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
                        s.findClosestElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.9, 4.0, 5.0, 2.2))).equals(Arrays.asList(3.9, 4.0)),
                        s.findClosestElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 5.9, 4.0, 5.0))).equals(Arrays.asList(5.0, 5.9)),
                        s.findClosestElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.2))).equals(Arrays.asList(2.0, 2.2)),
                        s.findClosestElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.0))).equals(Arrays.asList(2.0, 2.0)),
                        s.findClosestElements(new ArrayList<>(Arrays.asList(1.1, 2.2, 3.1, 4.1, 5.1))).equals(Arrays.asList(2.2, 3.1))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedTwoElementInputReturnsOnlyPair_B1_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(-1.0, 4.5),
                s.findClosestElements(new ArrayList<>(Arrays.asList(-1.0, 4.5))),
                "Boundary B1 / Valid V3: the only possible pair must be returned in ascending order");
    }

    @Test
    void improvedTieKeepsFirstPair_B2() {
        Solution s = new Solution();
        // Qwen uses strict `<` so only the first pair meeting minDiff is kept.
        Assertions.assertEquals(
                Arrays.asList(1.0, 2.0),
                s.findClosestElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0))),
                "Boundary B2: strict < comparator freezes the earliest minimum-diff pair on a tie");
    }

    @Test
    void improvedNegativeValuesSortedBeforeMatching_V1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(-3.0, -2.9),
                s.findClosestElements(new ArrayList<>(Arrays.asList(-3.0, 0.0, -2.9, 10.0))),
                "Valid class V1: negative numbers must be sorted first before the closest-pair scan");
    }

    @Test
    void improvedMutationSingletonReturnsEmptyList_I1() {
        Solution s = new Solution();
        // Qwen's for loop never executes when size == 1, so the method returns the initial empty list.
        Assertions.assertEquals(
                new ArrayList<Double>(),
                s.findClosestElements(new ArrayList<>(Arrays.asList(1.0))),
                "Invalid class I1 (mutation): with fewer than two elements the method silently returns an empty list");
    }
}
