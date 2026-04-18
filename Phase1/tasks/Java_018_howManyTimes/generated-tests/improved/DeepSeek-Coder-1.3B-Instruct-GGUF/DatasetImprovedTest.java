/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
                        s.howManyTimes("", "x") == 0,
                        s.howManyTimes("xyxyxyx", "x") == 4,
                        s.howManyTimes("cacacacac", "cac") == 4,
                        s.howManyTimes("john doe", "john") == 1
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedChecksOverlapAndEmptyStringCases() {
        Solution s = new Solution();
        Assertions.assertEquals(4, s.howManyTimes("aaaa", "a"));
        Assertions.assertEquals(0, s.howManyTimes("", "abc"));
    }
}
