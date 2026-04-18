/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.greatestCommonDivisor(3, 5) == 1,
                s.greatestCommonDivisor(25, 15) == 5
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}

