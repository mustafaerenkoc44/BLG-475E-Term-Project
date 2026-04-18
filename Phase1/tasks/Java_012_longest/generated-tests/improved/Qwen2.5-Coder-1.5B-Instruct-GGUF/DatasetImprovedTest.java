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
                        s.longest(new ArrayList<>(List.of())).isEmpty(),
                        Objects.equals(s.longest(new ArrayList<>(Arrays.asList("x", "y", "z"))).get(), "x"),
                        Objects.equals(s.longest(new ArrayList<>(Arrays.asList("x", "yyy", "zzzz", "www", "kkkk", "abc"))).get(), "zzzz")
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSingletonReturnsItsOwnValue_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Optional.of("solo"),
                s.longest(new ArrayList<>(Arrays.asList("solo"))),
                "Boundary B1: a singleton must wrap its only element in Optional.of");
    }

    @Test
    void improvedTieKeepsFirstLongest_B2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Optional.of("bb"),
                s.longest(new ArrayList<>(Arrays.asList("a", "bb", "cc"))),
                "Boundary B2: strict '>' comparison keeps the earliest occurrence of the maximum length");
    }

    @Test
    void improvedMultipleMaxKeepsFirstSeen_V2() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Optional.of("delta"),
                s.longest(new ArrayList<>(Arrays.asList("a", "delta", "omega", "gamma"))),
                "Valid class V2: when three strings share max length, the first in encounter order wins");
    }

    @Test
    void improvedMutationNullListThrowsNpe_I1() {
        Solution s = new Solution();
        Assertions.assertThrows(
                NullPointerException.class,
                () -> s.longest(null),
                "Invalid class I1 (mutation): calling isEmpty on null must raise NPE");
    }
}
