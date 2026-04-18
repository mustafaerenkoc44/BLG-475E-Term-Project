/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
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
    void improvedCoversEmptyNullAndNegativeInputs() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.rollingMax(List.of()));
        Assertions.assertEquals(List.of(), s.rollingMax(null));
        Assertions.assertEquals(Arrays.asList(-3, -2, -2), s.rollingMax(Arrays.asList(-3, -2, -5)));
    }
}

