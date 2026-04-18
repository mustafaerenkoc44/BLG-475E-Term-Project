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
                        s.countDistinctCharacters("") == 0,
                        s.countDistinctCharacters("abcde") == 5,
                        s.countDistinctCharacters("abcde" + "cade" + "CADE") == 5,
                        s.countDistinctCharacters("aaaaAAAAaaaa") == 1,
                        s.countDistinctCharacters("Jerry jERRY JeRRRY") == 5
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}

