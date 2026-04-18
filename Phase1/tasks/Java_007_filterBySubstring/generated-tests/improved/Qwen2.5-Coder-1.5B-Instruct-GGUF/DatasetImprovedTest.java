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
                        s.filterBySubstring(new ArrayList<>(List.of()), "john").equals(List.of()),
                        s.filterBySubstring(new ArrayList<>(Arrays.asList("xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx")), "xxx").equals(Arrays.asList("xxx", "xxxAAA", "xxx")),
                        s.filterBySubstring(new ArrayList<>(Arrays.asList("xxx", "asd", "aaaxxy", "john doe", "xxxAAA", "xxx")), "xx").equals(Arrays.asList("xxx", "aaaxxy", "xxxAAA", "xxx")),
                        s.filterBySubstring(new ArrayList<>(Arrays.asList("grunt", "trumpet", "prune", "gruesome")), "run").equals(Arrays.asList("grunt", "prune"))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedCaseSensitivityIsEnforced_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("abc"),
                s.filterBySubstring(new ArrayList<>(Arrays.asList("Abc", "abc", "ABC")), "ab"),
                "Valid class V3: String.contains is case-sensitive so only the lower-case match survives");
    }

    @Test
    void improvedEmptySubstringReturnsAll_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("abc", "", "xyz"),
                s.filterBySubstring(new ArrayList<>(Arrays.asList("abc", "", "xyz")), ""),
                "Boundary B2: empty substring is contained in every string so the input is returned intact");
    }

    @Test
    void improvedPreservesOrderAndDuplicates_V1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList("alpha", "alpha", "alphabet"),
                s.filterBySubstring(new ArrayList<>(Arrays.asList("alpha", "beta", "alpha", "alphabet")), "alp"),
                "Valid class V1: filter must preserve original order and keep duplicate matches");
    }

    @Test
    void improvedMutationNullInputsThrowNpe_I1_I2() {
        Solution s = new Solution();
        Assertions.assertThrows(NullPointerException.class,
                () -> s.filterBySubstring(null, "ab"),
                "Invalid class I1 (mutation): null source list must throw NPE when iterated");
        Assertions.assertThrows(NullPointerException.class,
                () -> s.filterBySubstring(new ArrayList<>(Arrays.asList("abc")), null),
                "Invalid class I2 (mutation): String.contains(null) throws NPE");
    }
}
