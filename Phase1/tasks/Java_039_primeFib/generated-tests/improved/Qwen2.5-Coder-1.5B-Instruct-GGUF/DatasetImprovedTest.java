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
                "Boundary B1: first prime Fibonacci anchors the sequence before any search iteration");
        Assertions.assertEquals(3, s.primeFib(2),
                "Boundary B1: advancing to n=2 confirms correct 1-based indexing");
    }

    @Test
    void improvedModerateIndexCompletesQuickly_B2_V3() {
        Solution s = new Solution();
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(2),
                () -> Assertions.assertEquals(233, s.primeFib(6),
                        "Valid V3 / Boundary B2: a moderately deep index still terminates quickly"));
    }

    @Test
    void improvedIsPrimeRejectsCompositeFibonacci_V2() {
        Solution s = new Solution();
        // Fibonacci 8 is composite (2*2*2); verifying the first index that skips it implicitly checks the filter.
        Assertions.assertEquals(13, s.primeFib(4),
                "Valid class V2: composite Fibonacci values are filtered out so n=4 returns 13, not 8");
    }

    @Test
    void improvedMutationNonPositiveIndexLoopsForever_I1() {
        Solution s = new Solution();
        // The guard count==n never fires when n<=0, so the search loop runs indefinitely.
        Assertions.assertThrows(
                org.opentest4j.AssertionFailedError.class,
                () -> Assertions.assertTimeoutPreemptively(Duration.ofMillis(500),
                        () -> s.primeFib(0),
                        "Invalid class I1 (mutation): n=0 makes count==n unreachable and the search loop never terminates"));
    }
}
