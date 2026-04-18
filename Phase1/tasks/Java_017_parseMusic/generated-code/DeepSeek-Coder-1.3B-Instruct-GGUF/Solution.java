/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Input to this function is a string representing musical notes in a special ASCII format.
    Your task is to parse this string and return list of integers corresponding to how many beats does each
    not last.

    Here is a legend:
    "o" - whole note, lasts four beats
    "o|" - half note, lasts two beats
    ".|" - quater note, lasts one beat

    >>> parseMusic("o o| .| o| o| .| .| .| .| o o")
    [4, 2, 1, 2, 2, 1, 1, 1, 1, 4, 4]
     */
    public List<Integer> parseMusic(String string) {
List<Integer> beats = new ArrayList<>();
        String[] notes = string.split(" ");
        for (String note : notes) {
            switch (note) {
                case "o":
                    beats.add(4);
                    break;
                case "o|":
                    beats.add(2);
                    break;
                case ".|":
                    beats.add(1);
                    break;
            }
        }
        return beats;
    }
}

