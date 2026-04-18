/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.countDistinctCharacters("xyzXYZ") == 3,
                s.countDistinctCharacters("Jerry") == 4
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
