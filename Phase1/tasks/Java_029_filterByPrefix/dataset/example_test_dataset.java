/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.filterByPrefix(new ArrayList<>(List.of()), "a").equals(List.of()),
                s.filterByPrefix(new ArrayList<>(Arrays.asList("abc", "bcd", "cde", "array")), "a").equals(Arrays.asList("abc", "array"))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


