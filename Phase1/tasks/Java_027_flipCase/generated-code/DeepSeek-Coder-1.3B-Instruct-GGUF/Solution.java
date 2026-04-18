/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
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

