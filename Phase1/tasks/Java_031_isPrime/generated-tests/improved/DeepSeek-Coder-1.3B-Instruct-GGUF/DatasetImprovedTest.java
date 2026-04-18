/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
                        !s.isPrime(6),
                        s.isPrime(101),
                        s.isPrime(11),
                        s.isPrime(13441),
                        s.isPrime(61),
                        !s.isPrime(4),
                        !s.isPrime(1),
                        s.isPrime(5),
                        s.isPrime(11),
                        s.isPrime(17),
                        !s.isPrime(5 * 17),
                        !s.isPrime(11 * 7),
                        !s.isPrime(13441 * 19)
                );
                if (correct.contains(false)) {
                    Assertions.fail("Dataset assertion failed");
                }
    }
}
