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
                        Objects.equals(s.stringXor("111000", "101010"), "010010"),
                        Objects.equals(s.stringXor("1", "1"), "0"),
                        Objects.equals(s.stringXor("0101", "0000"), "0101")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
