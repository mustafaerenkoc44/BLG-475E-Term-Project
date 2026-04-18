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
                        s.allPrefixes("").equals(List.of()),
                        s.allPrefixes("asdfgh").equals(Arrays.asList("a", "as", "asd", "asdf", "asdfg", "asdfgh")),
                        s.allPrefixes("WWW").equals(Arrays.asList("W", "WW", "WWW"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedEmptyStringReturnsEmptyList_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.allPrefixes(""),
                "Boundary B1: empty input degenerates to an empty prefix list and skips the loop body");
    }

    @Test
    void improvedSingleCharacterReturnsOnePrefix_B2_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of("x"), s.allPrefixes("x"),
                "Boundary B2 / Valid V2: smallest non-empty input produces a singleton list containing itself");
    }

    @Test
    void improvedRepeatedCharactersPreservePositions_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(Arrays.asList("a", "aa", "aaa"), s.allPrefixes("aaa"),
                "Valid class V3: repeated characters still yield position-dependent distinct prefixes");
    }

    @Test
    void improvedMutationNullStringThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.allPrefixes(null),
                "Invalid class I1 (mutation): calling string.length() on null raises NPE");
    }
}
