/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Insert a number 'delimeter' between every two consecutive elements of input list `numbers'
    >>> intersperse(List.of(), 4)
    []
    >>> intersperse(Arrays.asList(1, 2, 3), 4)
    [1, 4, 2, 4, 3]
     */
    public List<Integer> intersperse(List<Integer> numbers, int delimiter) {
List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            result.add(numbers.get(i));
            if (i != numbers.size() - 1) {
                result.add(delimiter);
            }
        }
        return result;
    }
}
