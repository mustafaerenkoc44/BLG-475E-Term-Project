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
                        Objects.equals(s.flipCase(""), ""),
                        Objects.equals(s.flipCase("Hello!"), "hELLO!"),
                        Objects.equals(s.flipCase("These violent delights have violent ends"), "tHESE VIOLENT DELIGHTS HAVE VIOLENT ENDS")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedPreservesDigitsAndPunctuation() {
        Solution s = new Solution();
        Assertions.assertEquals("123!aB", s.flipCase("123!Ab"));
    }
}
