/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Return the number of times the digit 7 appears in integers less than n which are divisible by 11 or 13.
    >>> fizzBuzz(50)
    0
    >>> fizzBuzz(78)
    2
    >>> fizzBuzz(79)
    3
     */
    public int fizzBuzz(int n) {
int count = 0;
        for(int i = 1; i < n; i++) {
            if(i % 11 == 0 || i % 13 == 0) {
                String num = Integer.toString(i);
                for(int j = 0; j < num.length(); j++) {
                    if(num.charAt(j) == '7') {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}


