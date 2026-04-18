/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
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
List<String> prefixes = new ArrayList<String>();
        for (int i = 0; i < string.length(); i++) {
            prefixes.add(string.substring(0, i + 1));
        }
        return prefixes;
    }
}


