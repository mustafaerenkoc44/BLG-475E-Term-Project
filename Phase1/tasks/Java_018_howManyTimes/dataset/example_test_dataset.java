/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.howManyTimes("", "a") == 0,
                s.howManyTimes("aaa", "a") == 3,
                s.howManyTimes("aaaa", "aa") == 3
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


