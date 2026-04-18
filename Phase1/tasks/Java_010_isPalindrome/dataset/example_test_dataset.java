/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                Objects.equals(s.makePalindrome(""), ""),
                Objects.equals(s.makePalindrome("cat"), "catac"),
                Objects.equals(s.makePalindrome("cata"), "catac")
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
