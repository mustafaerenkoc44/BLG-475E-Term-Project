/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Filter an input list of strings only for ones that contain given substring
    >>> filterBySubstring(List.of(), "a")
    []
    >>> filterBySubstring(Arrays.asList("abc", "bacd", "cde", "array"), "a")
    ["abc", "bacd", "array"]
     */
    public List<String> filterBySubstring(List<String> strings, String substring) {
List<String> result = new ArrayList<>();
        for(String str : strings){
            if(str.contains(substring)){
                result.add(str);
            }
        }
        return result;
    }
}
