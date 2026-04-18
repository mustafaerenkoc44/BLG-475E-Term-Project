/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.fizzBuzz(50) == 0,
                s.fizzBuzz(78) == 2,
                s.fizzBuzz(79) == 3
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
