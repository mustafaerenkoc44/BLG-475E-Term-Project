/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
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


