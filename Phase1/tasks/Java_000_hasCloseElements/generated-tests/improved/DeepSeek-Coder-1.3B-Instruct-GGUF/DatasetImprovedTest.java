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
                s.hasCloseElements(new ArrayList<>(Arrays.asList(11.0, 2.0, 3.9, 4.0, 5.0, 2.2)), 0.3),
                !s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.9, 4.0, 5.0, 2.2)), 0.05),
                s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 5.9, 4.0, 5.0)), 0.95),
                !s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 5.9, 4.0, 5.0)), 0.8),
                s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.0)), 0.1),
                s.hasCloseElements(new ArrayList<>(Arrays.asList(1.1, 2.2, 3.1, 4.1, 5.1)), 1.0),
                !s.hasCloseElements(new ArrayList<>(Arrays.asList(1.1, 2.2, 3.1, 4.1, 5.1)), 0.5)
        );
        if (correct.contains(false)) {
            Assertions.fail("Dataset assertion failed");
        }
    }

    @Test
    void improvedHandlesExactThresholdBoundary_B2() {
        Solution s = new Solution();
        Assertions.assertFalse(
                s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 1.3)), 0.3),
                "Boundary B2: difference exactly equal to threshold must be treated as not close under < semantics");
        Assertions.assertTrue(
                s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 1.3)), 0.30001),
                "Just above the equal-threshold boundary should return true");
    }

    @Test
    void improvedHandlesDegenerateListSizes_B1() {
        Solution s = new Solution();
        Assertions.assertFalse(
                s.hasCloseElements(new ArrayList<>(), 0.5),
                "Boundary B1a: empty list has no pairs and must be false");
        Assertions.assertFalse(
                s.hasCloseElements(new ArrayList<>(Arrays.asList(42.0)), 0.5),
                "Boundary B1b: singleton list has no pair and must be false");
    }

    @Test
    void improvedHandlesDuplicateValues_V3() {
        Solution s = new Solution();
        Assertions.assertTrue(
                s.hasCloseElements(new ArrayList<>(Arrays.asList(2.0, 2.0, 5.0)), 0.1),
                "Duplicates produce a zero distance which is always less than a positive threshold");
    }

    @Test
    void improvedMutationNegativeValuesAndNegativeThreshold() {
        Solution s = new Solution();
        Assertions.assertTrue(
                s.hasCloseElements(new ArrayList<>(Arrays.asList(-1.0, -1.05, 3.0)), 0.1),
                "Mutation: negative values preserve the absolute-distance oracle");
        Assertions.assertFalse(
                s.hasCloseElements(new ArrayList<>(Arrays.asList(1.0, 2.0)), -0.1),
                "Invalid class I2: negative threshold makes every distance fail the < check");
    }
}
