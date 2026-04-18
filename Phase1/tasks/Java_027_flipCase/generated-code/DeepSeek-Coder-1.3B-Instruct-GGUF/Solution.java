/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    For a given string, flip lowercase characters to uppercase and uppercase to lowercase.
    >>> flipCase("Hello")
    "hELLO"
     */
    public String flipCase(String string) {
StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }
}
