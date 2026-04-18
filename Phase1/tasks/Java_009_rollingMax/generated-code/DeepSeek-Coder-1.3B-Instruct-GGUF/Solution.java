/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    From a given list of integers, generate a list of rolling maximum element found until given moment
    in the sequence.
    >>> rollingMax(Arrays.asList(1, 2, 3, 2, 3, 4, 2))
    [1, 2, 3, 3, 3, 4, 4]
     */
    public List<Integer> rollingMax(List<Integer> numbers) {
List<Integer> result = new ArrayList<>();
        if (numbers == null || numbers.size() == 0) {
            return result;
        }

        int max = numbers.get(0);
        result.add(max);

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > max) {
                max = numbers.get(i);
            }
            result.add(max);
        }

        return result;
    }
}


