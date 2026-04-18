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
                        s.sortNumbers("").equals(""),
                        s.sortNumbers("three").equals("three"),
                        s.sortNumbers("three five nine").equals("three five nine"),
                        s.sortNumbers("five zero four seven nine eight").equals("zero four five seven eight nine")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedAlreadySortedIsIdempotent_V2() {
        Solution s = new Solution();
        Assertions.assertEquals("zero one two", s.sortNumbers("zero one two"),
                "Valid class V2: already sorted input must remain stable under the comparator");
    }

    @Test
    void improvedPreservesDuplicates_V3() {
        Solution s = new Solution();
        Assertions.assertEquals("one two two", s.sortNumbers("two one two"),
                "Valid class V3: the output preserves multiplicity of repeated numeral tokens");
    }

    @Test
    void improvedSingletonTokenIsUnchanged_B2() {
        Solution s = new Solution();
        Assertions.assertEquals("seven", s.sortNumbers("seven"),
                "Boundary B2: single numeral token has nothing to reorder");
    }

    @Test
    void improvedMutationUnknownTokenTriggersNpe_I1() {
        Solution s = new Solution();
        // DeepSeek builds a HashMap and then calls numberMap.get(token) - 
        // unknown tokens return null and auto-unboxing throws NPE.
        Assertions.assertThrows(NullPointerException.class,
                () -> s.sortNumbers("one ten two"),
                "Invalid class I1 (mutation): DeepSeek's HashMap.get returns null for unknown tokens, raising NPE on unboxing");
    }
}
