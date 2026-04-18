/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Input to this function is a string containing multiple groups of nested parentheses. Your goal is to
    separate those group into separate strings and return the list of those.
    Separate groups are balanced (each open brace is properly closed) and not nested within each other
    Ignore any spaces in the input string.
    >>> separateParenGroups("( ) (( )) (( )( ))")
    ["()", "(())", "(()())"]
     */
    public List<String> separateParenGroups(String paren_string) {
List<String> result = new ArrayList<>();
        StringBuilder currentGroup = new StringBuilder();
        int open = 0;
        for (char c : paren_string.toCharArray()) {
            if (c == '(') {
                open++;
                currentGroup.append(c);
            } else if (c == ')') {
                open--;
                currentGroup.append(c);
                if (open == 0) {
                    result.add(currentGroup.toString());
                    currentGroup = new StringBuilder();
                }
            }
        }
        return result;
    }
}


