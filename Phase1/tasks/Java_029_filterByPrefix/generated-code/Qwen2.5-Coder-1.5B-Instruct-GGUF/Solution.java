/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;


class Solution {
    /**
    Filter an input list of strings only for ones that start with a given prefix.
    >>> filterByPrefix(List.of(), "a")
    []
    >>> filterByPrefix(Arrays.asList("abc", "bcd", "cde", "array"), "a")
    ["abc", "array"]
     */
    public List<String> filterByPrefix(List<String> strings, String prefix) {        
        return strings.stream()
                      .filter(s -> s.startsWith(prefix))
                      .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.filterByPrefix(List.of(), "a")); // Should print []
        System.out.println(sol.filterByPrefix(Arrays.asList("abc", "bcd", "cde", "array"), "a")); // Should print ["abc", "array"]
    }
}


