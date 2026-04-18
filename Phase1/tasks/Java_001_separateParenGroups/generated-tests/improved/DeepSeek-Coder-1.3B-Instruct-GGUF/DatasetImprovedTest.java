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
    void improvedHandlesCompactAndEmptyInput_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.separateParenGroups(""),
                "Boundary B1a: empty input yields no groups and skips the loop");
        Assertions.assertEquals(List.of(), s.separateParenGroups("   "),
                "Boundary B1b: whitespace-only input skips all characters and yields no groups");
    }

    @Test
    void improvedAdjacentGroupsSeparatedByDepth_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("()", "()", "()"),
                s.separateParenGroups("()()()"),
                "Boundary B2: group separation depends on nesting depth, not spaces");
    }

    @Test
    void improvedNestedGroupRetainedAsOne_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("((()))"),
                s.separateParenGroups("((()))"),
                "Valid class V3: nested groups remain a single entry until balance returns to zero");
    }

    @Test
    void improvedMutationNonParenthesisTokensAreIgnored_I2() {
        Solution s = new Solution();
        // DeepSeek's else-if chain silently ignores any character that is neither '(' nor ')'.
        Assertions.assertEquals(
                Arrays.asList("()", "(())"),
                s.separateParenGroups("() abc (())"),
                "Invalid class I2 (mutation): non-parenthesis tokens are skipped without contributing to any group");
    }
}
