/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
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
        // Convert the string to lowercase to ignore case
        string = string.toLowerCase();
        // Create a set to store distinct characters
        Set<Character> distinctChars = new HashSet<>();
        // Iterate through each character in the string
        for (char c : string.toCharArray()) {
            // Add the character to the set if it's not already present
            distinctChars.add(c);
        }
        // Return the size of the set, which represents the number of distinct characters
        return distinctChars.size();
    }
}

