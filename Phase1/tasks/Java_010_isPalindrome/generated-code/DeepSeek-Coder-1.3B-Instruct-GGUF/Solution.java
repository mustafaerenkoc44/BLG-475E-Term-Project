/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;

class Solution {
    /**
    Find the shortest palindrome that begins with a supplied string.
    Algorithm idea is simple:
    - Find the longest postfix of supplied string that is a palindrome.
    - Append to the end of the string reverse of a string prefix that comes before the palindromic suffix.
    >>> makePalindrome("")
    ""
    >>> makePalindrome("cat")
    "catac"
    >>> makePalindrome("cata")
    "catac"
     */
    public String makePalindrome(String string) {
        for (int start = 0; start < string.length(); start++) {
            if (isPalindrome(string, start, string.length() - 1)) {
                String prefix = string.substring(0, start);
                return string + new StringBuilder(prefix).reverse();
            }
        }
        return string;
    }

    private boolean isPalindrome(String value, int left, int right) {
        while (left < right) {
            if (value.charAt(left) != value.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}


