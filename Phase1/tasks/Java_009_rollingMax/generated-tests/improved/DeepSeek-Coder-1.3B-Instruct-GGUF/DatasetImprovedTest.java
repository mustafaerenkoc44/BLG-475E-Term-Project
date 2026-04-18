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
                        s.rollingMax(new ArrayList<>(List.of())).equals(List.of()),
                        s.rollingMax(new ArrayList<>(Arrays.asList(1, 2, 3, 4))).equals(Arrays.asList(1, 2, 3, 4)),
                        s.rollingMax(new ArrayList<>(Arrays.asList(4, 3, 2, 1))).equals(Arrays.asList(4, 4, 4, 4)),
                        s.rollingMax(new ArrayList<>(Arrays.asList(3, 2, 3, 100, 3))).equals(Arrays.asList(3, 3, 3, 100, 100))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSingletonListIsPassedThrough_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(Arrays.asList(7), s.rollingMax(new ArrayList<>(Arrays.asList(7))),
                "Boundary B2: DeepSeek seeds the running max with numbers.get(0) and adds it immediately");
    }

    @Test
    void improvedMutationNullAndEmptyShortCircuit_I1_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.rollingMax(new ArrayList<>(List.of())),
                "Boundary B1 / Invalid class I1 (mutation): the null-or-empty short-circuit must still return an empty list for empty input");
        Assertions.assertEquals(List.of(), s.rollingMax(null),
                "Invalid class I1 (mutation): replacing the null-or-empty guard with a fall-through path would stop null from returning an empty list");
    }

    @Test
    void improvedDuplicatesKeepMaxStable_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(2, 2, 2, 2),
                s.rollingMax(new ArrayList<>(Arrays.asList(2, 2, 1, 2))),
                "Valid class V3: strict '>' means duplicate peaks keep the existing maximum without change");
    }

    @Test
    void improvedNegativeOnlySequenceTracksHighestNegative_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(-3, -2, -2),
                s.rollingMax(new ArrayList<>(Arrays.asList(-3, -2, -5))),
                "Valid class V2: negative sequences still update the running maximum when a less-negative value appears");
    }
}
