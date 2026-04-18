/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.factorize(8).equals(Arrays.asList(2, 2, 2)),
                s.factorize(25).equals(Arrays.asList(5,5)),
                s.factorize(70).equals(Arrays.asList(2,5,7))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
