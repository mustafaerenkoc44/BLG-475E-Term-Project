/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
