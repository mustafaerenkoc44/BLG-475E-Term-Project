/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.intersperse(new ArrayList<>(List.of()), 4).equals(List.of()),
                s.intersperse(new ArrayList<>(Arrays.asList(1,2,3)), 4).equals(Arrays.asList(1,4,2,4,3))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}


