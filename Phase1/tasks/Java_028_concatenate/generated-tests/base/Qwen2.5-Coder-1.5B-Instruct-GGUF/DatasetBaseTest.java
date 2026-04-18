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
                        Objects.equals(s.concatenate(new ArrayList<>(List.of())), ""),
                        Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("x", "y", "z"))), "xyz"),
                        Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("x", "y", "z", "w", "k"))), "xyzwk")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}

