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
                        Objects.equals(s.stringXor("111000", "101010"), "010010"),
                        Objects.equals(s.stringXor("1", "1"), "0"),
                        Objects.equals(s.stringXor("0101", "0000"), "0101")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedEmptyStringsProduceEmptyResult_B1() {
        Solution s = new Solution();
        Assertions.assertEquals("", s.stringXor("", ""),
                "Boundary B1: equal-length empty inputs degenerate to an empty result");
    }

    @Test
    void improvedLengthOneBitExercised_B2() {
        Solution s = new Solution();
        Assertions.assertEquals("1", s.stringXor("1", "0"),
                "Boundary B2a: smallest non-empty input triggers the mismatched branch");
        Assertions.assertEquals("0", s.stringXor("0", "0"),
                "Boundary B2b: smallest non-empty equal inputs stay at '0'");
    }

    @Test
    void improvedNonBinaryCharactersStillCompareLexically_I2() {
        Solution s = new Solution();
        Assertions.assertEquals("01", s.stringXor("a1", "a0"),
                "Invalid class I2 (documentation): char-by-char equality still drives the branch, so letters coincidentally xor to '0'");
    }

    @Test
    void improvedMutationDifferentLengthsTruncatesToShorter() {
        Solution s = new Solution();
        Assertions.assertThrows(StringIndexOutOfBoundsException.class,
                () -> s.stringXor("101", "10"),
                "Invalid class I1 (mutation): DeepSeek iterates a.length(), so a shorter b.charAt raises out-of-bounds");
    }
}
