/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
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
