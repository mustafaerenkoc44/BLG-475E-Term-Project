/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Given a string, find out how many distinct characters (regardless of case) does it consist of
    >>> countDistinctCharacters("xyzXYZ")
    3
    >>> countDistinctCharacters("Jerry")
    4
     */
    public int countDistinctCharacters(String string) {
Set<Character> distinctChars = new HashSet<Character>();
        for(int i=0; i<string.length(); i++) {
            distinctChars.add(Character.toLowerCase(string.charAt(i)));
        }
        return distinctChars.size();
    }
}


