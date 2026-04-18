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
                        s.stringSequence(0).equals("0"),
                        s.stringSequence(3).equals("0 1 2 3"),
                        s.stringSequence(10).equals("0 1 2 3 4 5 6 7 8 9 10")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSmallestExpansionBeyondBase_B1() {
        Solution s = new Solution();
        Assertions.assertEquals("0 1", s.stringSequence(1),
                "Boundary B1: n=1 exercises the first iteration beyond the base case and trailing-space trimming");
    }

    @Test
    void improvedTwoDigitSeparatorsRemainSingleSpace_B2() {
        Solution s = new Solution();
        Assertions.assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12", s.stringSequence(12),
                "Boundary B2: transition across two-digit tokens must still use a single space separator");
    }

    @Test
    void improvedInclusiveUpperBound_V3() {
        Solution s = new Solution();
        Assertions.assertEquals("0 1 2", s.stringSequence(2),
                "Valid class V3: the sequence includes n itself because the loop condition is i <= n");
    }

    @Test
    void improvedMutationNegativeInputYieldsEmptyString_I1() {
        Solution s = new Solution();
        Assertions.assertEquals("", s.stringSequence(-1),
                "Invalid class I1 (mutation): a negative n skips the loop, leaving only a trimmed empty string");
    }
}
