/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
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
        // sort the list
        Collections.sort(numbers);
        
        // find the closest pair
        double minDiff = Double.MAX_VALUE;
        List<Double> closestPair = new ArrayList<>();
        for (int i = 0; i < numbers.size() - 1; i++) {
            double diff = numbers.get(i + 1) - numbers.get(i);
            if (diff < minDiff) {
                minDiff = diff;
                closestPair = Arrays.asList(numbers.get(i), numbers.get(i + 1));
            }
        }
        
        // return the closest pair
        return closestPair;
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findClosestElements(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.2))); // [2.0, 2.2]
        System.out.println(solution.findClosestElements(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.0))); // [2.0, 2.0]
    }
}


