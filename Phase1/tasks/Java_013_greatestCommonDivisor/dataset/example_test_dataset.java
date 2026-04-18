/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.greatestCommonDivisor(3, 5) == 1,
                s.greatestCommonDivisor(25, 15) == 5
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
