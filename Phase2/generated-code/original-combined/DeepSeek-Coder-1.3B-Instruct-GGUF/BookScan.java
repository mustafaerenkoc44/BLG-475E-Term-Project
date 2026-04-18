/* @Authors
* Student Names: Mustafa Eren KOC, Onat Baris ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class BookScan {
    public record ScanResult(
            int targetLength,
            int totalOccurrences,
            List<Integer> matchingLines,
            Map<Integer, Integer> occurrencesByLine,
            Map<Integer, List<String>> matchedWordsByLine) {
    }

    public record WordSearchResult(
            String word,
            int totalOccurrences,
            List<Integer> matchingLines,
            Map<Integer, Integer> occurrencesByLine) {
    }

    public ScanResult scanByWordLength(String text, int targetLength) {
        if (text == null || targetLength <= 0) {
            return new ScanResult(targetLength, 0, List.of(), Map.of(), Map.of());
        }

        List<Integer> lines = new ArrayList<>();
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        Map<Integer, List<String>> matches = new LinkedHashMap<>();
        int total = 0;

        String[] rows = text.split("\\R");
        for (int index = 0; index < rows.length; index++) {
            String cleaned = rows[index].replaceAll("[^A-Za-z0-9 ]", " ");
            List<String> lineMatches = new ArrayList<>();
            for (String token : cleaned.split("\\s+")) {
                if (!token.isBlank() && strlen(token) == targetLength) {
                    lineMatches.add(token);
                }
            }
            if (!lineMatches.isEmpty()) {
                int lineNumber = index + 1;
                lines.add(lineNumber);
                counts.put(lineNumber, lineMatches.size());
                matches.put(lineNumber, lineMatches);
                total += lineMatches.size();
            }
        }

        return new ScanResult(targetLength, total, lines, counts, matches);
    }

    public WordSearchResult scanWord(String text, String word) {
        if (text == null || word == null || word.isBlank()) {
            return new WordSearchResult(word == null ? "" : word, 0, List.of(), Map.of());
        }

        List<Integer> lines = new ArrayList<>();
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        int total = 0;
        String needle = word.toLowerCase(Locale.ROOT);

        String[] rows = text.split("\\R");
        for (int index = 0; index < rows.length; index++) {
            String lowered = rows[index].toLowerCase(Locale.ROOT);
            int occurrences = howManyTimes(lowered, needle);
            if (occurrences > 0) {
                int lineNumber = index + 1;
                lines.add(lineNumber);
                counts.put(lineNumber, occurrences);
                total += occurrences;
            }
        }

        return new WordSearchResult(needle, total, lines, counts);
    }

    public int howManyTimes(String string, String substring) {
        if (string == null || substring == null || substring.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int index = 0; index <= string.length() - substring.length(); index++) {
            if (string.substring(index, index + substring.length()).equals(substring)) {
                count++;
            }
        }
        return count;
    }

    public int strlen(String string) {
        return Objects.requireNonNull(string).length();
    }

    public String flipCase(String string) {
        Objects.requireNonNull(string);
        StringBuilder builder = new StringBuilder(string.length());
        for (char character : string.toCharArray()) {
            if (Character.isUpperCase(character)) {
                builder.append(Character.toLowerCase(character));
            } else if (Character.isLowerCase(character)) {
                builder.append(Character.toUpperCase(character));
            } else {
                builder.append(character);
            }
        }
        return builder.toString();
    }
}
