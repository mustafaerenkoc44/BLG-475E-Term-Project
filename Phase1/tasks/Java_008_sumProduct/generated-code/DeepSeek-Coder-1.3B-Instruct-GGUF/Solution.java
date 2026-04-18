/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    For a given list of integers, return a tuple consisting of a sum and a product of all the integers in a list.
    Empty sum should be equal to 0 and empty product should be equal to 1.
    >>> sumProduct(List.of())
    [0, 1]
    >>> sumProduct(Arrays.asList(1, 2, 3, 4))
    [10, 24]
     */
    public List<Integer> sumProduct(List<Integer> numbers) {
int sum = 0;
        int product = 1;
        for (int num : numbers) {
            sum += num;
            product *= num;
        }
        return Arrays.asList(sum, product);
    }
}

