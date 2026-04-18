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
                Math.abs(s.meanAbsoluteDeviation(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0))) - 2.0 / 3.0) < 1e-6,
                Math.abs(s.meanAbsoluteDeviation(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0))) - 1.0) < 1e-6,
                Math.abs(s.meanAbsoluteDeviation(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0))) - 6.0 / 5.0) < 1e-6
        );
        if (correct.contains(false)) {
            Assertions.fail("Dataset assertion failed");
        }
    }

    @Test
    void improvedHandlesSingletonCollapsesToZero_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(0.0, s.meanAbsoluteDeviation(Arrays.asList(4.0)), 1e-9,
                "Boundary B1: a singleton list has zero deviation by construction");
    }

    @Test
    void improvedHandlesTwoElementSymmetry_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(2.0, s.meanAbsoluteDeviation(Arrays.asList(1.0, 5.0)), 1e-9,
                "Boundary B2: two-element MAD equals half the spread");
    }

    @Test
    void improvedHandlesRepeatedValuesWithNonZeroDeviation_V2() {
        Solution s = new Solution();
        // mean([1,1,3]) = 5/3, dev = |1-5/3| + |1-5/3| + |3-5/3| = 2/3 + 2/3 + 4/3 = 8/3, mad = 8/9
        Assertions.assertEquals(8.0 / 9.0, s.meanAbsoluteDeviation(Arrays.asList(1.0, 1.0, 3.0)), 1e-6,
                "Valid class V2: repeated values still produce a non-zero MAD when the outlier pulls the mean");
    }

    @Test
    void improvedHandlesDecimalPrecision_V3() {
        Solution s = new Solution();
        // mean([0.5, 1.5, 2.5]) = 1.5, dev = 1.0 + 0.0 + 1.0 = 2.0, mad = 2/3
        Assertions.assertEquals(2.0 / 3.0, s.meanAbsoluteDeviation(Arrays.asList(0.5, 1.5, 2.5)), 1e-6,
                "Valid class V3: non-integer inputs preserve averaging precision");
    }

    @Test
    void improvedMutationEmptyListFallsBackToZeroForQwenStreamSemantics_I1() {
        Solution s = new Solution();
        // Qwen uses .orElse(0.0) on the mean stream so an empty list produces 0.0 without throwing.
        Assertions.assertEquals(0.0, s.meanAbsoluteDeviation(new ArrayList<>()), 1e-9,
                "Invalid class I1 (mutation): stream-based mean returns 0 on empty list via orElse");
    }
}
