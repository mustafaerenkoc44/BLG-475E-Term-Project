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
}

