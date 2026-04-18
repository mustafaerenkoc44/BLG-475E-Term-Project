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
                        s.longest(new ArrayList<>(List.of())).isEmpty(),
                        Objects.equals(s.longest(new ArrayList<>(Arrays.asList("x", "y", "z"))).get(), "x"),
                        Objects.equals(s.longest(new ArrayList<>(Arrays.asList("x", "yyy", "zzzz", "www", "kkkk", "abc"))).get(), "zzzz")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}

