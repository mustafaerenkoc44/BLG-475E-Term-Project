/* @Authors
* Student Names: Mustafa Eren KOC, Onat Baris ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookScan {
    private static final Pattern WORD_PATTERN =
            Pattern.compile("[A-Za-z0-9]+(?:['-][A-Za-z0-9]+)*");

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
        if (text == null || text.isBlank() || targetLength <= 0) {
            return new ScanResult(targetLength, 0, List.of(), Map.of(), Map.of());
        }

        String[] rows = text.split("\\R", -1);
        List<Integer> lines = new ArrayList<>();
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        Map<Integer, List<String>> matches = new LinkedHashMap<>();
        int total = 0;

        for (int index = 0; index < rows.length; index++) {
            List<String> lineMatches = new ArrayList<>();
            for (String token : tokenizeWords(rows[index])) {
                if (strlen(token) == targetLength) {
                    lineMatches.add(token);
                }
            }
            if (!lineMatches.isEmpty()) {
                int lineNumber = index + 1;
                lines.add(lineNumber);
                counts.put(lineNumber, lineMatches.size());
                matches.put(lineNumber, List.copyOf(lineMatches));
                total += lineMatches.size();
            }
        }

        return new ScanResult(
                targetLength,
                total,
                List.copyOf(lines),
                Collections.unmodifiableMap(new LinkedHashMap<>(counts)),
                Collections.unmodifiableMap(new LinkedHashMap<>(matches)));
    }

    public WordSearchResult scanWord(String text, String word) {
        if (text == null || text.isBlank()) {
            return new WordSearchResult(word == null ? "" : word.strip(), 0, List.of(), Map.of());
        }

        String needle = canonicalizeQuery(word);
        if (needle.isEmpty()) {
            return new WordSearchResult(word == null ? "" : word.strip(), 0, List.of(), Map.of());
        }

        String[] rows = text.split("\\R", -1);
        List<Integer> lines = new ArrayList<>();
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        int total = 0;

        for (int index = 0; index < rows.length; index++) {
            int lineNumber = index + 1;
            String normalizedLine = normalizeLine(rows[index]);
            int occurrences = howManyTimes(" " + normalizedLine + " ", " " + needle + " ");
            if (occurrences > 0) {
                lines.add(lineNumber);
                counts.put(lineNumber, occurrences);
                total += occurrences;
            }
        }

        return new WordSearchResult(
                needle,
                total,
                List.copyOf(lines),
                Collections.unmodifiableMap(new LinkedHashMap<>(counts)));
    }

    public int howManyTimes(String string, String substring) {
        if (string == null || substring == null || substring.isEmpty() || string.isEmpty()
                || substring.length() > string.length()) {
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

    private String normalizeLine(String line) {
        List<String> normalized = new ArrayList<>();
        for (String token : tokenizeWords(line)) {
            normalized.add(canonicalizeWord(token));
        }
        return String.join(" ", normalized);
    }

    private String canonicalizeQuery(String word) {
        if (word == null) {
            return "";
        }
        List<String> tokens = tokenizeWords(word.strip());
        if (tokens.size() != 1) {
            return "";
        }
        return canonicalizeWord(tokens.get(0));
    }

    private String canonicalizeWord(String token) {
        if (token.equals(token.toUpperCase(Locale.ROOT)) && !token.equals(token.toLowerCase(Locale.ROOT))) {
            return flipCase(token);
        }
        return token.toLowerCase(Locale.ROOT);
    }

    private List<String> tokenizeWords(String line) {
        List<String> tokens = new ArrayList<>();
        if (line == null || line.isBlank()) {
            return tokens;
        }
        Matcher matcher = WORD_PATTERN.matcher(line);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }
}
