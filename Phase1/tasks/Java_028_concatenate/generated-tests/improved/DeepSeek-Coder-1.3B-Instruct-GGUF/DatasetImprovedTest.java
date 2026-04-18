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
                        Objects.equals(s.concatenate(new ArrayList<>(List.of())), ""),
                        Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("x", "y", "z"))), "xyz"),
                        Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("x", "y", "z", "w", "k"))), "xyzwk")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
