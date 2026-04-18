/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;

class Solution {
    /**
    Test if given string is a palindrome
     */
    public boolean isPalindrome(String string) {
        int i = 0;
        int j = string.length() - 1;
        while (i < j) {
            if (string.charAt(i)!= string.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    public String makePalindrome(String string) {
