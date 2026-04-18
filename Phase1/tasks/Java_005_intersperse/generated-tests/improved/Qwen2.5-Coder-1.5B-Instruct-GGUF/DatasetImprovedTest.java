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
                        s.intersperse(new ArrayList<>(List.of()), 7).equals(List.of()),
                        s.intersperse(new ArrayList<>(Arrays.asList(5, 6, 3, 2)), 8).equals(Arrays.asList(5, 8, 6, 8, 3, 8, 2)),
                        s.intersperse(new ArrayList<>(Arrays.asList(2, 2, 2)), 2).equals(Arrays.asList(2, 2, 2, 2, 2))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedHandlesSingletonList_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(9),
                s.intersperse(new ArrayList<>(Arrays.asList(9)), 4),
                "Boundary B1: size-one list has no internal gap and must stay unchanged");
    }

    @Test
    void improvedDelimiterAlreadyPresent_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(0, 0, 1, 0, 0),
                s.intersperse(new ArrayList<>(Arrays.asList(0, 1, 0)), 0),
                "Boundary B2: inserted and original zeros co-exist but positions must remain deterministic");
    }

    @Test
    void improvedHandlesNegativeDelimiterAndValues_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(-3, -1, -5, -1, -7),
                s.intersperse(new ArrayList<>(Arrays.asList(-3, -5, -7)), -1),
                "Valid class V3: interleaving works for negative delimiters and negative elements alike");
    }

    @Test
    void improvedMutationNullListThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(
                NullPointerException.class,
                () -> s.intersperse(null, 0),
                "Invalid class I1 (mutation): dereferencing numbers.size() on null list must throw an NPE");
    }
}
