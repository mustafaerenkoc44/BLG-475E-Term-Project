/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Check if in given list of numbers, are any two numbers closer to each other than given threshold.
    >>> hasCloseElements(Arrays.asList(1.0, 2.0, 3.0), 0.5)
    false
    >>> hasCloseElements(Arrays.asList(1.0, 2.8, 3.0, 4.0, 5.0, 2.0), 0.3)
    true
     */
    public boolean hasCloseElements(List<Double> numbers, double threshold) {        
        Collections.sort(numbers);
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (Math.abs(numbers.get(i) - numbers.get(i + 1)) < threshold) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.hasCloseElements(Arrays.asList(1.0, 2.0, 3.0), 0.5)); // false
        System.out.println(s.hasCloseElements(Arrays.asList(1.0, 2.8, 3.0, 4.0, 5.0, 2.0), 0.3)); // true
    }
}
