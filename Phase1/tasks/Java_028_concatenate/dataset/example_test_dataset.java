/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                Objects.equals(s.concatenate(new ArrayList<>(List.of())), ""),
                Objects.equals(s.concatenate(new ArrayList<>(Arrays.asList("a", "b", "c"))),"abc")
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


