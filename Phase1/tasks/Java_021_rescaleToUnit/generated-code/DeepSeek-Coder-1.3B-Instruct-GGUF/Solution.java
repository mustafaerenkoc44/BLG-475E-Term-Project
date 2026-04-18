/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Given list of numbers (of at least two elements), apply a linear transform to that list,
    such that the smallest number will become 0 and the largest will become 1
    >>> rescaleToUnit(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0))
    [0.0, 0.25, 0.5, 0.75, 1.0]
     */
    public List<Double> rescaleToUnit(List<Double> numbers) {
double min = Collections.min(numbers);
        double max = Collections.max(numbers);
        double range = max - min;
        
        for(int i = 0; i < numbers.size(); i++) {
            numbers.set(i, (numbers.get(i) - min) / range);
        }
        
        return numbers;
    }
}

