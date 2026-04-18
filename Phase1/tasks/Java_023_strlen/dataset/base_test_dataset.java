/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150201075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.strlen("") == 0,
                s.strlen("x") == 1,
                s.strlen("asdasnakj") == 9
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
