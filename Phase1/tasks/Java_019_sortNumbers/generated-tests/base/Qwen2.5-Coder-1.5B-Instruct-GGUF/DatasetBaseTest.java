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
                        s.sortNumbers("").equals(""),
                        s.sortNumbers("three").equals("three"),
                        s.sortNumbers("three five nine").equals("three five nine"),
                        s.sortNumbers("five zero four seven nine eight").equals("zero four five seven eight nine")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
