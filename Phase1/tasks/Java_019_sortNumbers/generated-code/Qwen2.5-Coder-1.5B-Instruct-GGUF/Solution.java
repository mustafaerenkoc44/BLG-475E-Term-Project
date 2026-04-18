/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
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
        if (numbers.isBlank()) {
            return "";
        }

        List<String> values = new ArrayList<>(Arrays.asList(numbers.split("\\s+")));
        List<String> order = Arrays.asList(
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine"
        );

        values.sort(new Comparator<String>() {
            @Override
            public int compare(String left, String right) {
                return Integer.compare(order.indexOf(left), order.indexOf(right));
            }
        });
        return String.join(" ", values);
    }
}


