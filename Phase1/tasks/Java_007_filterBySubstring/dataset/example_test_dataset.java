/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<Boolean> correct = Arrays.asList(
                s.filterBySubstring(new ArrayList<>(List.of()), "s").equals(List.of()),
                s.filterBySubstring(new ArrayList<>(Arrays.asList("abc", "bacd", "cde", "array")), "a").equals(Arrays.asList("abc", "bacd", "array"))
        );
        if (correct.contains(false)) {
            throw new AssertionError();
        }
    }
}
