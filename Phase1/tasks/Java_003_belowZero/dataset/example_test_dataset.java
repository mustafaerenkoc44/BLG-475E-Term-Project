/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                !s.belowZero(new ArrayList<>(Arrays.asList(1, 2, 3))),
                s.belowZero(new ArrayList<>(Arrays.asList(1, 2, -4, 5)))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}

