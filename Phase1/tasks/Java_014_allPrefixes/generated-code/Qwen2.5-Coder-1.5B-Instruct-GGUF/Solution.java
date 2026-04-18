/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Return list of all prefixes from shortest to longest of the input string
    >>> allPrefixes("abc")
    ["a", "ab", "abc"]
     */
    public List<String> allPrefixes(String string) { 
        List<String> result = new ArrayList<String>();
        for (int i = 1; i <= string.length(); i++) {
            result.add(string.substring(0, i));
        }
        return result;
    }
}
