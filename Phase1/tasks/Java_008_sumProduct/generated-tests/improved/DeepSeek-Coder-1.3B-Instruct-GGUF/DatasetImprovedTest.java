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
                        s.sumProduct(new ArrayList<>(List.of())).equals(Arrays.asList(0, 1)),
                        s.sumProduct(new ArrayList<>(Arrays.asList(1, 1, 1))).equals(Arrays.asList(3, 1)),
                        s.sumProduct(new ArrayList<>(Arrays.asList(100, 0))).equals(Arrays.asList(100, 0)),
                        s.sumProduct(new ArrayList<>(Arrays.asList(3, 5, 7))).equals(Arrays.asList(3 + 5 + 7, 3 * 5 * 7)),
                        s.sumProduct(new ArrayList<>(List.of(10))).equals(Arrays.asList(10, 10))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSingleZeroCollapsesProduct_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(0, 0),
                s.sumProduct(new ArrayList<>(Arrays.asList(0))),
                "Boundary B1: single zero sets sum=0 and triggers absorbing product=0");
    }

    @Test
    void improvedMixedSignProducesSignedProduct_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(0, 6),
                s.sumProduct(new ArrayList<>(Arrays.asList(-2, 3, -1))),
                "Boundary B2: an even count of negatives yields a positive product and sum reflects sign cancellation");
    }

    @Test
    void improvedZeroInsideNonZeroSequenceAbsorbsProduct_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(7, 0),
                s.sumProduct(new ArrayList<>(Arrays.asList(2, 0, 5))),
                "Valid class V3: a single zero absorbs the product while sum remains the plain sum");
    }

    @Test
    void improvedMutationNullListThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(
                NullPointerException.class,
                () -> s.sumProduct(null),
                "Invalid class I1 (mutation): null list must raise NPE from the enhanced-for iterator in DeepSeek's implementation");
    }
}
