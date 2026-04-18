/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                !s.belowZero(new ArrayList<>(Arrays.asList(1, 2, 3))),
                s.belowZero(new ArrayList<>(Arrays.asList(1, 2, -4, 5)))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
