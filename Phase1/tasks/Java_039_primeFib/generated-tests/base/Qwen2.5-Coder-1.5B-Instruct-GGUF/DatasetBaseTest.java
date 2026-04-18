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
}

