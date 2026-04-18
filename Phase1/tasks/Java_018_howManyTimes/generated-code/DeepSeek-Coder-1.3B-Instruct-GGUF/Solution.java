/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Find how many times a given substring can be found in the original string. Count overlaping cases.
    >>> howManyTimes("", "a")
    0
    >>> howManyTimes("aaa", "a")
    3
    >>> howManyTimes("aaaa", "aa")
    3
     */
    public int howManyTimes(String string, String substring) {
int count = 0;
        for (int i = 0; i <= string.length() - substring.length(); i++) {
            String sub = string.substring(i, i + substring.length());
            if (sub.equals(substring)) {
                count++;
            }
        }
        return count;
    }
}

