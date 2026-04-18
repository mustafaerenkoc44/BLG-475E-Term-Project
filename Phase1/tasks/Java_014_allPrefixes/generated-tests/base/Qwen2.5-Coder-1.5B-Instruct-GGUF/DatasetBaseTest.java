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
                        s.allPrefixes("").equals(List.of()),
                        s.allPrefixes("asdfgh").equals(Arrays.asList("a", "as", "asd", "asdf", "asdfg", "asdfgh")),
                        s.allPrefixes("WWW").equals(Arrays.asList("W", "WW", "WWW"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
