/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                Objects.equals(s.stringXor("010", "110"), "100")
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
