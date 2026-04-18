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
                "Boundary B1b: whitespace-only input filters every character and yields no groups");
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
    void improvedMutationUnbalancedInputYieldsPartialOutput_I1() {
        Solution s = new Solution();
        // Qwen's loop never emits a trailing unbalanced remainder because the final balance != 0.
        Assertions.assertEquals(
                List.of(),
                s.separateParenGroups("(()"),
                "Invalid class I1 (mutation): unbalanced input leaves the current group buffered and un-emitted");
    }
}
