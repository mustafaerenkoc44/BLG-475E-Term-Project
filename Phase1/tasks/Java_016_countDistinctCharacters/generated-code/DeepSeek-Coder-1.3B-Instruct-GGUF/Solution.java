/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
