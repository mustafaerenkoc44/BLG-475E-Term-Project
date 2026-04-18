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
            return emptyScanResult(targetLength);
        }

        List<Integer> matchingLines = new ArrayList<>();
        Map<Integer, Integer> occurrencesByLine = new LinkedHashMap<>();
        Map<Integer, List<String>> matchedWordsByLine = new LinkedHashMap<>();
        int totalOccurrences = 0;

        String[] lines = text.split("\\R", -1);
        for (int index = 0; index < lines.length; index++) {
            int lineNumber = index + 1;
            List<String> matchedWords = new ArrayList<>();

            for (String token : tokenizeWords(lines[index])) {
                if (strlen(token) == targetLength) {
                    matchedWords.add(token);
                }
            }

            if (!matchedWords.isEmpty()) {
                matchingLines.add(lineNumber);
                occurrencesByLine.put(lineNumber, matchedWords.size());
                matchedWordsByLine.put(lineNumber, List.copyOf(matchedWords));
                totalOccurrences += matchedWords.size();
            }
        }

        return new ScanResult(
                targetLength,
                totalOccurrences,
                List.copyOf(matchingLines),
                freezeIntegerMap(occurrencesByLine),
                freezeWordMap(matchedWordsByLine));
    }

    public WordSearchResult scanWord(String text, String word) {
        if (text == null || text.isBlank()) {
            return emptyWordSearchResult(word);
        }

        String canonicalWord = canonicalizeQueryWord(word);
        if (canonicalWord.isEmpty()) {
            return emptyWordSearchResult(word);
        }

        List<Integer> matchingLines = new ArrayList<>();
        Map<Integer, Integer> occurrencesByLine = new LinkedHashMap<>();
        int totalOccurrences = 0;

        String[] lines = text.split("\\R", -1);
        for (int index = 0; index < lines.length; index++) {
            int lineNumber = index + 1;
            String normalizedLine = normalizeLineForWholeWordCounting(lines[index]);
            int occurrences = howManyTimes(
                    " " + normalizedLine + " ",
                    " " + canonicalWord + " ");

            if (occurrences > 0) {
                matchingLines.add(lineNumber);
                occurrencesByLine.put(lineNumber, occurrences);
                totalOccurrences += occurrences;
            }
        }

        return new WordSearchResult(
                canonicalWord,
                totalOccurrences,
                List.copyOf(matchingLines),
                freezeIntegerMap(occurrencesByLine));
    }

    /**
     * Phase 1 task #18 adapted for robust use in a larger integration-tested class.
     * The method counts overlapping occurrences and returns 0 for null, blank,
     * or oversized search terms to avoid runaway loops on empty substrings.
     */
    public int howManyTimes(String string, String substring) {
        if (string == null || substring == null) {
            return 0;
        }
        if (string.isEmpty() || substring.isEmpty() || substring.length() > string.length()) {
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

    /**
     * Phase 1 task #23 preserved as a direct helper for line-level token filtering.
     */
    public int strlen(String string) {
        return Objects.requireNonNull(string, "string").length();
    }

    /**
     * Phase 1 task #27 preserved for case-normalization paths that need to toggle
     * fully-uppercase tokens into a stable lowercase form without altering symbols.
     */
    public String flipCase(String string) {
        Objects.requireNonNull(string, "string");

        StringBuilder result = new StringBuilder(string.length());
        for (char character : string.toCharArray()) {
            if (Character.isUpperCase(character)) {
                result.append(Character.toLowerCase(character));
            } else if (Character.isLowerCase(character)) {
                result.append(Character.toUpperCase(character));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    private ScanResult emptyScanResult(int targetLength) {
        return new ScanResult(
                targetLength,
                0,
                List.of(),
                Map.of(),
                Map.of());
    }

    private WordSearchResult emptyWordSearchResult(String word) {
        return new WordSearchResult(
                word == null ? "" : word.strip(),
                0,
                List.of(),
                Map.of());
    }

    private Map<Integer, Integer> freezeIntegerMap(Map<Integer, Integer> source) {
        return Collections.unmodifiableMap(new LinkedHashMap<>(source));
    }

    private Map<Integer, List<String>> freezeWordMap(Map<Integer, List<String>> source) {
        Map<Integer, List<String>> frozen = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<String>> entry : source.entrySet()) {
            frozen.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        return Collections.unmodifiableMap(frozen);
    }

    private String normalizeLineForWholeWordCounting(String line) {
        List<String> normalizedTokens = new ArrayList<>();
        for (String token : tokenizeWords(line)) {
            normalizedTokens.add(canonicalizeWord(token));
        }
        return String.join(" ", normalizedTokens);
    }

    private String canonicalizeQueryWord(String word) {
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
        String trimmed = token.strip();
        if (trimmed.isEmpty()) {
            return "";
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        for (char character : trimmed.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasUpper = true;
            } else if (Character.isLowerCase(character)) {
                hasLower = true;
            }
        }

        if (hasUpper && !hasLower) {
            return flipCase(trimmed);
        }
        return trimmed.toLowerCase(Locale.ROOT);
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
