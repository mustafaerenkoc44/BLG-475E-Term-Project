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
    void improvedHandlesSingleDeepGroup_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(Arrays.asList(4), s.parseNestedParens("(((())))"),
                "Valid class V3: single deeply nested group must report its maximum depth");
    }

    @Test
    void improvedDepthOneVsTwoAlternation_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(1, 2),
                s.parseNestedParens("() (())"),
                "Boundary B2: depth counter must reset between groups and observe maxima independently");
    }

    @Test
    void improvedEmptyInputProducesSingleZero_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(0),
                s.parseNestedParens(""),
                "Boundary B1: split(\" \") on an empty string yields a single empty token whose max depth is 0");
    }

    @Test
    void improvedMutationNonParenthesisTokensAreIgnored_I2() {
        Solution s = new Solution();
        // Both implementations filter only '(' and ')' characters, so a stray 'abc' inside a group is ignored.
        Assertions.assertEquals(
                Arrays.asList(1, 0, 2),
                s.parseNestedParens("() abc (())"),
                "Invalid class I2 (mutation): non-parenthesis tokens split by whitespace still produce a zero-depth entry");
    }
}
