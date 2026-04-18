/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;


class Solution {
    /**
    Filter an input list of strings only for ones that contain given substring
    >>> filterBySubstring(List.of(), "a")
    []
    >>> filterBySubstring(Arrays.asList("abc", "bacd", "cde", "array"), "a")
    ["abc", "bacd", "array"]
     */
    public List<String> filterBySubstring(List<String> strings, String substring) {        
        return strings.stream().filter(s -> s.contains(substring)).collect(Collectors.toList());
    }
}

