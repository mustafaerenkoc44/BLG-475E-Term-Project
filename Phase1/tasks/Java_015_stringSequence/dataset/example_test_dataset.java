/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.stringSequence(0).equals("0"),
                s.stringSequence(5).equals("0 1 2 3 4 5")
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


