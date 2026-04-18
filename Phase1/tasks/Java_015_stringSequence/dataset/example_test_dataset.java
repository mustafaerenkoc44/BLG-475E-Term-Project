/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
