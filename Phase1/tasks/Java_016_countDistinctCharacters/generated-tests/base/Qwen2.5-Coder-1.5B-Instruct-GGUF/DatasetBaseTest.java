/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
