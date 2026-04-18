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
                        s.factorize(2).equals(List.of(2)),
                        s.factorize(4).equals(Arrays.asList(2, 2)),
                        s.factorize(8).equals(Arrays.asList(2, 2, 2)),
                        s.factorize(3 * 19).equals(Arrays.asList(3, 19)),
                        s.factorize(3 * 19 * 3 * 19).equals(Arrays.asList(3, 3, 19, 19)),
                        s.factorize(3 * 19 * 3 * 19 * 3 * 19).equals(Arrays.asList(3, 3, 3, 19, 19, 19)),
                        s.factorize(3 * 19 * 19 * 19).equals(Arrays.asList(3, 19, 19, 19)),
                        s.factorize(3 * 2 * 3).equals(Arrays.asList(2, 3, 3))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
