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
                        s.rescaleToUnit(new ArrayList<>(Arrays.asList(2.0, 49.9))).equals(Arrays.asList(0.0, 1.0)),
                        s.rescaleToUnit(new ArrayList<>(Arrays.asList(100.0, 49.9))).equals(Arrays.asList(1.0, 0.0)),
                        s.rescaleToUnit(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0))).equals(Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0)),
                        s.rescaleToUnit(new ArrayList<>(Arrays.asList(2.0, 1.0, 5.0, 3.0, 4.0))).equals(Arrays.asList(0.25, 0.0, 1.0, 0.5, 0.75)),
                        s.rescaleToUnit(new ArrayList<>(Arrays.asList(12.0, 11.0, 15.0, 13.0, 14.0))).equals(Arrays.asList(0.25, 0.0, 1.0, 0.5, 0.75))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSpansNegativeAndPositive_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(0.0, 1.0, 0.6),
                s.rescaleToUnit(new ArrayList<>(Arrays.asList(-2.0, 3.0, 1.0))),
                "Valid class V2: min/max are correctly discovered when inputs straddle zero");
    }

    @Test
    void improvedRepeatedInteriorMapsConsistently_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(0.0, 0.0, 1.0, 0.5),
                s.rescaleToUnit(new ArrayList<>(Arrays.asList(1.0, 1.0, 5.0, 3.0))),
                "Valid class V3: identical interior values keep identical rescaled positions");
    }

    @Test
    void improvedInputIsMutatedInPlace_B1() {
        Solution s = new Solution();
        List<Double> input = new ArrayList<>(Arrays.asList(2.0, 6.0));
        List<Double> output = s.rescaleToUnit(input);
        Assertions.assertSame(input, output,
                "Boundary B1 (mutation): DeepSeek's implementation reuses and returns the same list instance, mutating the caller's input");
        Assertions.assertEquals(Arrays.asList(0.0, 1.0), output,
                "Smallest valid rescaling: the two-element input maps to {0.0, 1.0}");
    }

    @Test
    void improvedMutationAllEqualTriggersNaN_I1() {
        Solution s = new Solution();
        List<Double> out = s.rescaleToUnit(new ArrayList<>(Arrays.asList(2.0, 2.0, 2.0)));
        for (Double value : out) {
            Assertions.assertTrue(Double.isNaN(value),
                    "Invalid class I1 (mutation): when max == min the divisor is zero and every entry becomes NaN");
        }
    }
}
