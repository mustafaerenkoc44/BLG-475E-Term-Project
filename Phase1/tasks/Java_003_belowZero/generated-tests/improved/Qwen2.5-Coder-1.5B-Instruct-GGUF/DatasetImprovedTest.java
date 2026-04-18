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
                !s.belowZero(new ArrayList<>(Arrays.asList())),
                !s.belowZero(new ArrayList<>(Arrays.asList(1, 2, -3, 1, 2, -3))),
                s.belowZero(new ArrayList<>(Arrays.asList(1, 2, -4, 5, 6))),
                !s.belowZero(new ArrayList<>(Arrays.asList(1, -1, 2, -2, 5, -5, 4, -4))),
                s.belowZero(new ArrayList<>(Arrays.asList(1, -1, 2, -2, 5, -5, 4, -5))),
                s.belowZero(new ArrayList<>(Arrays.asList(1, -2, 2, -2, 5, -5, 4, -4)))
        );
        if (correct.contains(false)) {
            Assertions.fail("Dataset assertion failed");
        }
    }

    @Test
    void improvedHandlesEmptyList_B1() {
        Solution s = new Solution();
        Assertions.assertFalse(
                s.belowZero(new ArrayList<>()),
                "Boundary B1: an empty operation stream never leaves the zero baseline");
    }

    @Test
    void improvedHandlesFirstOperationBreaksBalance_B2() {
        Solution s = new Solution();
        Assertions.assertTrue(
                s.belowZero(new ArrayList<>(Arrays.asList(-1))),
                "Boundary B2: the very first operation going negative must short-circuit to true");
        Assertions.assertTrue(
                s.belowZero(new ArrayList<>(Arrays.asList(-5, 100))),
                "A later compensating deposit must not hide an earlier negative excursion");
    }

    @Test
    void improvedHandlesOscillatingBalance_V3() {
        Solution s = new Solution();
        Assertions.assertFalse(
                s.belowZero(new ArrayList<>(Arrays.asList(2, -1, -1, 3, -2, -1))),
                "Valid class V3a: oscillating balance stays non-negative at every step (running sums 2,1,0,3,1,0)");
        Assertions.assertTrue(
                s.belowZero(new ArrayList<>(Arrays.asList(2, -1, -2, 5))),
                "Valid class V3b: a transient negative state still triggers true even if later positive");
    }

    @Test
    void improvedMutationNullOperationsThrows_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(
                NullPointerException.class,
                () -> s.belowZero(null),
                "Invalid class I1: the generated iterative code dereferences the list and must throw NPE on null");
    }
}
