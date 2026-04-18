/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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