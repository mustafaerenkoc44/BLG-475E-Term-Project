/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
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
                        s.separateParenGroups("(()()) ((())) () ((())()())").equals(Arrays.asList(
                                "(()())", "((()))", "()", "((())()())"
                        )),
                        s.separateParenGroups("() (()) ((())) (((())))").equals(Arrays.asList(
                                "()", "(())", "((()))", "(((())))"
                        )),
                        s.separateParenGroups("(()(())((())))").equals(Arrays.asList(
                                "(()(())((())))"
                        )),
                        s.separateParenGroups("( ) (( )) (( )( ))").equals(Arrays.asList("()", "(())", "(()())"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedHandlesCompactAndEmptyInput() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.separateParenGroups(""));
        Assertions.assertEquals(Arrays.asList("(()())"), s.separateParenGroups("(()())"));
    }
}

