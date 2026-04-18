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
                        s.fizzBuzz(50) == 0,
                        s.fizzBuzz(78) == 2,
                        s.fizzBuzz(79) == 3,
                        s.fizzBuzz(100) == 3,
                        s.fizzBuzz(200) == 6,
                        s.fizzBuzz(4000) == 192,
                        s.fizzBuzz(10000) == 639,
                        s.fizzBuzz(100000) == 8026
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}

