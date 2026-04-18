/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Return a string containing space-delimited numbers starting from 0 upto n inclusive.
    >>> stringSequence(0)
    "0"
    >>> stringSequence(5)
    "0 1 2 3 4 5"
     */
    public String stringSequence(int n) {
StringBuilder sequence = new StringBuilder();
        for(int i = 0; i <= n; i++) {
            sequence.append(i).append(" ");
        }
        return sequence.toString().trim();
    }
}
