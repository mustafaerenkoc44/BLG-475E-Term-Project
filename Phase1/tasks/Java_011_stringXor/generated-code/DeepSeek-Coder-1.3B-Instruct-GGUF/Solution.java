/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Input are two strings a and b consisting only of 1s and 0s.
    Perform binary XOR on these inputs and return result also as a string.
    >>> stringXor("010", "110")
    "100"
     */
    public String stringXor(String a, String b) {
StringBuilder sb = new StringBuilder();
        for(int i=0; i<a.length(); i++){
            if(a.charAt(i) == b.charAt(i))
                sb.append('0');
            else
                sb.append('1');
        }
        return sb.toString();
    }
}

