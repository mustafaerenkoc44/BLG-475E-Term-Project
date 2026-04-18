/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatasetBaseTest {
    @Test
    void datasetBaseTest() {
        Solution s = new Solution();
                List<Boolean> correct = Arrays.asList(
                        s.filterByPrefix(new ArrayList<>(List.of()), "john").equals(List.of()),
                        s.filterByPrefix(new ArrayList<>(Arrays.asList("xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx")), "xxx").equals(Arrays.asList("xxx", "xxxAAA", "xxx"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}

