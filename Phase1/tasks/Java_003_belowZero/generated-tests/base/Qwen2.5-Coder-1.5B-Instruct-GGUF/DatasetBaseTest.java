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
                        !s.belowZero(new ArrayList<>(Arrays.asList())),
                        !s.belowZero(new ArrayList<>(Arrays.asList(1, 2, -3, 1, 2, -3))),
                        s.belowZero(new ArrayList<>(Arrays.asList(1, 2, -4, 5, 6))),
                        !s.belowZero(new ArrayList<>(Arrays.asList(1, -1, 2, -2, 5, -5, 4, -4))),
                        s.belowZero(new ArrayList<>(Arrays.asList(1, -1, 2, -2, 5, -5, 4, -5))),
                        s.belowZero(new ArrayList<>(Arrays.asList(1, -2, 2, -2, 5, -5, 4, -4)))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
