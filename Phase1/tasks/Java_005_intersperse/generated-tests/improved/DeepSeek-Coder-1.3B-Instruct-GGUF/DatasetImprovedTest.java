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
                        s.intersperse(new ArrayList<>(List.of()), 7).equals(List.of()),
                        s.intersperse(new ArrayList<>(Arrays.asList(5, 6, 3, 2)), 8).equals(Arrays.asList(5, 8, 6, 8, 3, 8, 2)),
                        s.intersperse(new ArrayList<>(Arrays.asList(2, 2, 2)), 2).equals(Arrays.asList(2, 2, 2, 2, 2))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
