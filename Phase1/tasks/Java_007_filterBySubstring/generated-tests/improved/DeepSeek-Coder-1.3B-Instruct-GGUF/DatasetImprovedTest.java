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
                        s.filterBySubstring(new ArrayList<>(List.of()), "john").equals(List.of()),
                        s.filterBySubstring(new ArrayList<>(Arrays.asList("xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx")), "xxx").equals(Arrays.asList("xxx", "xxxAAA", "xxx")),
                        s.filterBySubstring(new ArrayList<>(Arrays.asList("xxx", "asd", "aaaxxy", "john doe", "xxxAAA", "xxx")), "xx").equals(Arrays.asList("xxx", "aaaxxy", "xxxAAA", "xxx")),
                        s.filterBySubstring(new ArrayList<>(Arrays.asList("grunt", "trumpet", "prune", "gruesome")), "run").equals(Arrays.asList("grunt", "prune"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
