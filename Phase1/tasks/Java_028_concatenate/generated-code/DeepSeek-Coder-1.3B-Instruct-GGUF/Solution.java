/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Concatenate list of strings into a single string
    >>> concatenate(List.of())
    ""
    >>> concatenate(Arrays.asList("a", "b", "c"))
    "abc"
     */
    public String concatenate(List<String> strings) {
StringBuilder sb = new StringBuilder();
        for(String s : strings){
            sb.append(s);
        }
        return sb.toString();
    }
}
