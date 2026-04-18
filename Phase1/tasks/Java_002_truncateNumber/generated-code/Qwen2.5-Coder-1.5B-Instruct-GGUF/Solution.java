/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Given a positive floating point number, it can be decomposed into
    and integer part (largest integer smaller than given number) and decimals
    (leftover part always smaller than 1).

    Return the decimal part of the number.
    >>> truncateNumber(3.5)
    0.5
     */
    public double truncateNumber(double number) { 
        return number - Math.floor(number);
    }
}


