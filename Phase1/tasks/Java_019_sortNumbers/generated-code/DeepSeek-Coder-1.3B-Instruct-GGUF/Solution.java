/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Input is a space-delimited string of numberals from 'zero' to 'nine'.
    Valid choices are 'zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight' and 'nine'.
    Return the string with numbers sorted from smallest to largest
    >>> sortNumbers("three one five")
    "one three five"
     */
    public String sortNumbers(String numbers) {
String[] numberWords = numbers.split(" ");
        String[] numberNames = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        int[] numberValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        HashMap<String, Integer> numberMap = new HashMap<String, Integer>();
        for (int i = 0; i < numberNames.length; i++) {
            numberMap.put(numberNames[i], numberValues[i]);
        }
        Arrays.sort(numberWords, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return numberMap.get(s1) - numberMap.get(s2);
            }
        });
        return String.join(" ", numberWords);
    }
}
