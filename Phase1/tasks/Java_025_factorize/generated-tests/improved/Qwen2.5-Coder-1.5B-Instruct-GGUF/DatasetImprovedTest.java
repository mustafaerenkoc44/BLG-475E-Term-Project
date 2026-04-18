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
                        s.factorize(2).equals(List.of(2)),
                        s.factorize(4).equals(Arrays.asList(2, 2)),
                        s.factorize(8).equals(Arrays.asList(2, 2, 2)),
                        s.factorize(3 * 19).equals(Arrays.asList(3, 19)),
                        s.factorize(3 * 19 * 3 * 19).equals(Arrays.asList(3, 3, 19, 19)),
                        s.factorize(3 * 19 * 3 * 19 * 3 * 19).equals(Arrays.asList(3, 3, 3, 19, 19, 19)),
                        s.factorize(3 * 19 * 19 * 19).equals(Arrays.asList(3, 19, 19, 19)),
                        s.factorize(3 * 2 * 3).equals(Arrays.asList(2, 3, 3))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedLargeRepeatedFactorCount_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(2, 2, 2, 2, 2),
                s.factorize(32),
                "Boundary B2: 2^5 exercises multiple iterations of the inner division loop");
    }

    @Test
    void improvedSquareOfPrime_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(5, 5),
                s.factorize(25),
                "Valid class V2: a prime square factorizes into two copies of the prime");
    }

    @Test
    void improvedCompositeWithDistinctPrimes_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(2, 5, 7),
                s.factorize(70),
                "Valid class V3: the result lists distinct primes in ascending order");
    }

    @Test
    void improvedMutationBelowTwoReturnsEmptyList_I1_I2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                List.of(),
                s.factorize(1),
                "Invalid class I1 (mutation): n=1 skips the outer while and the n>1 guard, returning an empty list");
        Assertions.assertEquals(
                List.of(),
                s.factorize(0),
                "Invalid class I2 (mutation): n=0 never enters the loop so no factors are emitted");
    }
}
