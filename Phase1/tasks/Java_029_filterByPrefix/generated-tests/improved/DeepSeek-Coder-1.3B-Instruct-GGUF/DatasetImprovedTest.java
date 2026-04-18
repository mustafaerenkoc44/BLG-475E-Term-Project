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
                        s.filterByPrefix(new ArrayList<>(List.of()), "john").equals(List.of()),
                        s.filterByPrefix(new ArrayList<>(Arrays.asList("xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx")), "xxx").equals(Arrays.asList("xxx", "xxxAAA", "xxx"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedPrefixEqualsFullString_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("a", "ab"),
                s.filterByPrefix(new ArrayList<>(Arrays.asList("a", "ab", "ba")), "a"),
                "Valid class V3: startsWith accepts a prefix equal to the full candidate string");
    }

    @Test
    void improvedEmptyPrefixMatchesEverything_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("abc", "", "xyz"),
                s.filterByPrefix(new ArrayList<>(Arrays.asList("abc", "", "xyz")), ""),
                "Boundary B2: the empty prefix is a prefix of every string so the list is returned intact");
    }

    @Test
    void improvedCaseSensitivityFiltersMismatchedCase_V1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("abc", "abcdef"),
                s.filterByPrefix(new ArrayList<>(Arrays.asList("abc", "Abc", "abcdef", "ABCxyz")), "abc"),
                "Valid class V1: startsWith is case-sensitive so uppercase candidates are discarded");
    }

    @Test
    void improvedMutationNullInputsThrowNpe_I1_I2() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.filterByPrefix(null, "a"),
                "Invalid class I1 (mutation): null source list raises NPE when .stream() is called");
        Assertions.assertThrows(NullPointerException.class,
                () -> s.filterByPrefix(new ArrayList<>(Arrays.asList("abc")), null),
                "Invalid class I2 (mutation): startsWith(null) triggers NPE inside the filter predicate");
    }
}
