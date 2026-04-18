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
                        s.parseMusic("").equals(List.of()),
                        s.parseMusic("o o o o").equals(Arrays.asList(4, 4, 4, 4)),
                        s.parseMusic(".| .| .| .|").equals(Arrays.asList(1, 1, 1, 1)),
                        s.parseMusic("o| o| .| .| o o o o").equals(Arrays.asList(2, 2, 1, 1, 4, 4, 4, 4)),
                        s.parseMusic("o| .| o| .| o o| o o|").equals(Arrays.asList(2, 1, 2, 1, 4, 2, 4, 2))
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }

    @Test
    void improvedSingleTokenInput_B1() {
        Solution s = new Solution();
        Assertions.assertEquals(Arrays.asList(4), s.parseMusic("o"),
                "Boundary B1: the smallest non-empty sequence emits a single whole-note beat count");
    }

    @Test
    void improvedRepeatedQuarterNotes_V3() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(1, 1, 1),
                s.parseMusic(".| .| .|"),
                "Valid class V3: compact-token streams consistently yield a quarter-note beat count");
    }

    @Test
    void improvedExtraInternalSpacingProducesEmptyTokens_B2() {
        Solution s = new Solution();
        // DeepSeek splits on a single space so consecutive spaces yield empty tokens that the switch ignores.
        Assertions.assertEquals(
                Arrays.asList(4, 2, 1),
                s.parseMusic("o   o|   .|"),
                "Boundary B2 (mutation): DeepSeek's split(\" \") creates empty tokens that the switch silently ignores");
    }

    @Test
    void improvedMutationUnknownTokenIsSilentlyIgnored_I1() {
        Solution s = new Solution();
        Assertions.assertEquals(
                Arrays.asList(2),
                s.parseMusic("x o|"),
                "Invalid class I1 (mutation): the switch has no default case so unknown tokens do not emit anything");
    }
}
