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
                        s.strlen("") == 0,
                        s.strlen("x") == 1,
                        s.strlen("asdasnakj") == 9
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedChecksWhitespaceAndPunctuation() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.strlen(" "));
        Assertions.assertEquals(5, s.strlen("a! b?"));
    }
}
