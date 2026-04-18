/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.howManyTimes("", "x") == 0,
                s.howManyTimes("xyxyxyx", "x") == 4,
                s.howManyTimes("cacacacac", "cac") == 4,
                s.howManyTimes("john doe", "john") == 1
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
