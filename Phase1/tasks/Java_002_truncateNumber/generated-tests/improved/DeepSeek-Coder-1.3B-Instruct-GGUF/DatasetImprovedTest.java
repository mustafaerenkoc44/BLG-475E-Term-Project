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
                s.truncateNumber(3.5) == 0.5,
                Math.abs(s.truncateNumber(1.33) - 0.33) < 1e-6,
                Math.abs(s.truncateNumber(123.456) - 0.456) < 1e-6
        );
        if (correct.contains(false)) {
            Assertions.fail("Dataset assertion failed");
        }
    }

    @Test
    void improvedHandlesZero_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(0.0, s.truncateNumber(0.0), 1e-9,
                "Boundary B1: zero input must yield zero fractional part");
    }

    @Test
    void improvedHandlesWholeNumber_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(0.0, s.truncateNumber(7.0), 1e-9,
                "Valid class V3: a whole-number input has a zero fractional part");
    }

    @Test
    void improvedHandlesValueJustBelowInteger_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(0.999999, s.truncateNumber(4.999999), 1e-6,
                "Boundary B2: values immediately below an integer preserve high-precision fractions");
    }

    @Test
    void improvedMutationNegativeInputsDeepSeekCastSemantics_V2() {
        Solution s = new Solution();
        // DeepSeek uses (int)number cast which truncates toward zero => -2.75 - (-2) = -0.75
        Assertions.assertEquals(-0.75, s.truncateNumber(-2.75), 1e-6,
                "Valid class V2 (mutation): cast-to-int semantics produce a signed fractional remainder for negative inputs");
        Assertions.assertEquals(0.0, s.truncateNumber(-3.0), 1e-9,
                "A negative whole number still produces a zero fractional part");
    }
}
