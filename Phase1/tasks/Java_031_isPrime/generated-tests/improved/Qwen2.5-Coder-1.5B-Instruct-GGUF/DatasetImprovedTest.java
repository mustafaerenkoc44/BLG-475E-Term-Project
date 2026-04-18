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
                        !s.isPrime(6),
                        s.isPrime(101),
                        s.isPrime(11),
                        s.isPrime(13441),
                        s.isPrime(61),
                        !s.isPrime(4),
                        !s.isPrime(1),
                        s.isPrime(5),
                        s.isPrime(11),
                        s.isPrime(17),
                        !s.isPrime(5 * 17),
                        !s.isPrime(11 * 7),
                        !s.isPrime(13441 * 19)
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSmallestPrime_V1() {
        Solution s = new Solution();
        Assertions.assertTrue(s.isPrime(2),
                "Valid class V1: 2 is the smallest prime and skips the divisor loop");
    }

    @Test
    void improvedPerfectSquareOfPrimeRejected_B1() {
        Solution s = new Solution();
        Assertions.assertFalse(s.isPrime(49),
                "Boundary B1: divisor loop must still detect sqrt(n) as a factor");
        Assertions.assertFalse(s.isPrime(9),
                "Boundary B1: 3*3 is likewise rejected at the sqrt boundary");
    }

    @Test
    void improvedBoundaryZeroIsNotPrime_I1() {
        Solution s = new Solution();
        Assertions.assertFalse(s.isPrime(0),
                "Invalid class I1: zero is not prime and short-circuits at the n<=1 guard");
    }

    @Test
    void improvedMutationNegativeInputsAreNotPrime_I2() {
        Solution s = new Solution();
        Assertions.assertFalse(s.isPrime(-7),
                "Invalid class I2 (mutation): the n<=1 guard correctly rejects negative integers");
        Assertions.assertFalse(s.isPrime(-1),
                "Invalid class I2 (mutation): -1 is below the guard threshold");
    }
}
