/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    Return list of prime factors of given integer in the order from smallest to largest.
    Each of the factors should be listed number of times corresponding to how many times it appeares in factorization.
    Input number should be equal to the product of all factors
    >>> factorize(8)
    [2, 2, 2]
    >>> factorize(25)
    [5, 5]
    >>> factorize(70)
    [2, 5, 7]
     */
    public List<Integer> factorize(int n) {        
        List<Integer> factors = new ArrayList<>();
        int i = 2;
        while (i * i <= n) {
            while (n % i == 0) {
                factors.add(i);
                n = n / i;
            }
            i++;
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }
}
