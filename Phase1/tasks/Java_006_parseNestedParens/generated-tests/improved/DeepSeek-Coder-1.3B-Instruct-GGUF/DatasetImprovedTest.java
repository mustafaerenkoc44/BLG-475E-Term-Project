/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
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
                        s.parseNestedParens("(()()) ((())) () ((())()())").equals(Arrays.asList(2, 3, 1, 3)),
                        s.parseNestedParens("() (()) ((())) (((())))").equals(Arrays.asList(1, 2, 3, 4)),
                        s.parseNestedParens("(()(())((())))").equals(Arrays.asList(4))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedHandlesSingleDeepGroup() {
        Solution s = new Solution();
        Assertions.assertEquals(Arrays.asList(4), s.parseNestedParens("(((())))"));
    }
}


