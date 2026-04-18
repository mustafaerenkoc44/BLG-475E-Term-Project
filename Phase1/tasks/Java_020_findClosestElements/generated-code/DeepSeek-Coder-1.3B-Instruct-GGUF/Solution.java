/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    From a supplied list of numbers (of length at least two) select and return two that are the closest to each
    other and return them in order (smaller number, larger number).
    >>> findClosestElements(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.2))
    [2.0, 2.2]
    >>> findClosestElements(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.0))
    [2.0, 2.0]
     */
    public List<Double> findClosestElements(List<Double> numbers) {
Collections.sort(numbers);
        double minDiff = Double.MAX_VALUE;
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < numbers.size() - 1; i++) {
            double diff = numbers.get(i + 1) - numbers.get(i);
            if (diff < minDiff) {
                minDiff = diff;
                result.clear();
                result.add(numbers.get(i));
                result.add(numbers.get(i + 1));
            } else if (diff == minDiff) {
                result.add(numbers.get(i));
                result.add(numbers.get(i + 1));
            }
        }
        return result;
    }
}
