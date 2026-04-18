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
                        s.greatestCommonDivisor(3, 7) == 1,
                        s.greatestCommonDivisor(10, 15) == 5,
                        s.greatestCommonDivisor(49, 14) == 7,
                        s.greatestCommonDivisor(144, 60) == 12
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedEqualInputsReturnInput_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(7, s.greatestCommonDivisor(7, 7),
                "Boundary B1: gcd(n, n) = n for any positive n");
    }

    @Test
    void improvedLoopTerminatesAtOne_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.greatestCommonDivisor(35, 64),
                "Boundary B2: stresses several modulo steps to reach gcd = 1");
    }

    @Test
    void improvedLargeSharedDivisor_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(6, s.greatestCommonDivisor(84, 18),
                "Valid class V3: gcd(84, 18) = 6 covers the multi-iteration Euclidean loop");
    }

    @Test
    void improvedMutationZeroAndNegativeInputs_I1_I2() {
        Solution s = new Solution();
        Assertions.assertEquals(5, s.greatestCommonDivisor(0, 5),
                "Invalid class I1a: gcd(0, b) returns b because the base case triggers immediately after one recursion");
        Assertions.assertEquals(5, s.greatestCommonDivisor(5, 0),
                "Invalid class I1b: gcd(a, 0) short-circuits to a without recursion");
        Assertions.assertEquals(2, s.greatestCommonDivisor(-4, 6),
                "Invalid class I2 (mutation): a negative 'a' still converges to the positive gcd because the recursion swaps to gcd(6, -4), gcd(-4, 2), gcd(2, 0)=2");
    }
}
