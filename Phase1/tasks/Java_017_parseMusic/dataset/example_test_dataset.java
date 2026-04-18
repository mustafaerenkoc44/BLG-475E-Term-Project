/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.parseMusic("o o| .| o| o| .| .| .| .| o o").equals(Arrays.asList(4, 2, 1, 2, 2, 1, 1, 1, 1, 4, 4))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


