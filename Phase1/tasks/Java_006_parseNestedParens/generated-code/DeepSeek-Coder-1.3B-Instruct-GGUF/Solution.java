/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Input to this function is a string represented multiple groups for nested parentheses separated by spaces.
    For each of the group, output the deepest level of nesting of parentheses.
    E.g. (()()) has maximum two levels of nesting while ((())) has three.

    >>> parseNestedParens("(()()) ((())) () ((())()())")
    [2, 3, 1, 3]
     */
    public List<Integer> parseNestedParens(String paren_string) {
List<Integer> levels = new ArrayList<>();
        String[] groups = paren_string.split(" ");
        for (String group : groups) {
            int maxLevel = 0;
            int currentLevel = 0;
            for (char ch : group.toCharArray()) {
                if (ch == '(') {
                    currentLevel++;
                    maxLevel = Math.max(maxLevel, currentLevel);
                } else if (ch == ')') {
                    currentLevel--;
                }
            }
            levels.add(maxLevel);
        }
        return levels;
    }
}


