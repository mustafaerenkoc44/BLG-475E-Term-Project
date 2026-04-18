/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.sumProduct(new ArrayList<>(List.of())).equals(Arrays.asList(0, 1)),
                s.sumProduct(new ArrayList<>(Arrays.asList(1, 2, 3,4))).equals(Arrays.asList(10, 24))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
