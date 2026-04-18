/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatasetImprovedTest {
    @Test
    void datasetBaseTest() {
        Solution s = new Solution();
                List<Boolean> correct = Arrays.asList(
                        s.primeFib(1) == 2,
                        s.primeFib(2) == 3,
                        s.primeFib(3) == 5,
                        s.primeFib(4) == 13,
                        s.primeFib(5) == 89,
                        s.primeFib(6) == 233,
                        s.primeFib(7) == 1597,
                        s.primeFib(8) == 28657,
                        s.primeFib(9) == 514229,
                        s.primeFib(10) == 433494437
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedFirstAndSecondPrimeFibConsecutive_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(2, s.primeFib(1),
                "Boundary B1: the (previous=0, current=1) seed advances to 1+0 then 1+1=2 which is prime");
        Assertions.assertEquals(3, s.primeFib(2),
                "Boundary B1: the second iteration reaches 2+1=3 and matches count==2");
    }

    @Test
    void improvedModerateIndexCompletesQuickly_B2_V3() {
        Solution s = new Solution();
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(2),
                () -> Assertions.assertEquals(233, s.primeFib(6),
                        "Valid V3 / Boundary B2: deeper indices must not trigger runaway searches"));
    }

    @Test
    void improvedIsPrimeRejectsCompositeFibonacci_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(13, s.primeFib(4),
                "Valid class V2: isPrime filters out composite Fibonacci values so n=4 returns 13");
    }

    @Test
    void improvedMutationNonPositiveIndexLoopsForever_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(
                org.opentest4j.AssertionFailedError.class,
                () -> Assertions.assertTimeoutPreemptively(Duration.ofMillis(500),
                        () -> s.primeFib(0),
                        "Invalid class I1 (mutation): n=0 makes the count==n guard unreachable and the loop never exits"));
    }
}
