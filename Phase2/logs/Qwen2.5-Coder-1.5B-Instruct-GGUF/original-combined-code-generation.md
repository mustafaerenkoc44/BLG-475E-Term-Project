# Qwen - Original Combined Prompt - Code Generation

## Prompt

Create a Java class named `BookScan`. The class should determine how many times
words of a given length appear in a text and on which lines they appear. The
class must also contain methods related to HumanEval-X tasks `#18 substring`,
`#23 string length`, and `#27 upper-lower case`. Return a single compilable
Java file. Keep the implementation simple and self-contained.

## Response

```java
/* @Authors
* Student Names: Mustafa Eren KOC, Onat Baris ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
            String[] tokens = rows[index].split(" ");
            List<String> lineMatches = new ArrayList<>();
            for (String token : tokens) {
                if (!token.isEmpty() && strlen(token) == targetLength) {
                    lineMatches.add(token);
                    lines.add(index);
                    total++;
                }
            }
            if (!lineMatches.isEmpty()) {
                counts.put(index, lineMatches.size());
                matches.put(index, lineMatches);
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

        String[] rows = text.split("\\R");
        for (int index = 0; index < rows.length; index++) {
            int occurrences = howManyTimes(rows[index], word);
            if (occurrences > 0) {
                lines.add(index);
                counts.put(index, occurrences);
                total += occurrences;
            }
        }

        return new WordSearchResult(word, total, lines, counts);
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
        StringBuilder builder = new StringBuilder();
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
```

## Usage Note

The response was stored without semantic modification as the
`original-combined/Qwen.../BookScan.java` candidate. It was kept specifically
to measure how an under-specified combined prompt behaves under integration
tests.
