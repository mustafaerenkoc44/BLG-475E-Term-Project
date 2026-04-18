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
                        s.fizzBuzz(50) == 0,
                        s.fizzBuzz(78) == 2,
                        s.fizzBuzz(79) == 3,
                        s.fizzBuzz(100) == 3,
                        s.fizzBuzz(200) == 6,
                        s.fizzBuzz(4000) == 192,
                        s.fizzBuzz(10000) == 639,
                        s.fizzBuzz(100000) == 8026
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedRangeBelowFirstMultiple_V1() {
        Solution s = new Solution();
        Assertions.assertEquals(0, s.fizzBuzz(10),
                "Valid class V1: n=10 excludes 11 and 13 so no multiples qualify");
    }

    @Test
    void improvedFirstQualifyingMultiple_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(0, s.fizzBuzz(12),
                "Boundary B2a: n=12 includes 11 but '11' contains no '7'");
        Assertions.assertEquals(0, s.fizzBuzz(14),
                "Boundary B2b: n=14 includes 11 and 13 but neither contains '7'");
    }

    @Test
    void improvedLowerBoundaryAtOne_I1() {
        Solution s = new Solution();
        Assertions.assertEquals(0, s.fizzBuzz(1),
                "Invalid class I1 (documentation): n=1 yields an empty range 1..0");
    }

    @Test
    void improvedMutationNegativeUpperBound_I1() {
        Solution s = new Solution();
        Assertions.assertEquals(0, s.fizzBuzz(-5),
                "Invalid class I1 (mutation): negative upper bound skips the loop body entirely and returns 0");
    }
}
