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
        int index = 0;
        while ((index = string.indexOf(substring, index)) != -1) {
            count++;
            index++; // Move to the next character to allow for overlapping matches
        }
        return count;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.howManyTimes("", "a")); // Expected output: 0
        System.out.println(sol.howManyTimes("aaa", "a")); // Expected output: 3
        System.out.println(sol.howManyTimes("aaaa", "aa")); // Expected output: 3
    }
}

