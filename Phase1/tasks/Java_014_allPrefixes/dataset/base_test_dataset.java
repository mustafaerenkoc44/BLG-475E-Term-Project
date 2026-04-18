/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.allPrefixes("").equals(List.of()),
                s.allPrefixes("asdfgh").equals(Arrays.asList("a", "as", "asd", "asdf", "asdfg", "asdfgh")),
                s.allPrefixes("WWW").equals(Arrays.asList("W", "WW", "WWW"))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}

