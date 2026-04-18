/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.allPrefixes("abc").equals(Arrays.asList("a", "ab", "abc"))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


