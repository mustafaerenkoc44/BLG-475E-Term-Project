# Interaction Log - Refactoring Loop

- **Model:** `DeepSeek-Coder-1.3B-Instruct-GGUF`
- **Task:** `Java/27` (folder `Java_027_flipCase`)
- **Step:** `refactoring`

## 1. Observed failure

flipCase() flipped ASCII digits alongside letters because the LLM used Character.toUpperCase/toLowerCase without first checking Character.isLetter.

## 2. Prompt issued to llama-cli

```text
The flipCase method below changes digits and punctuation to strange code points. Only flip characters that actually have a case, and preserve everything else verbatim.

Attached file (C:\Users\musta\Downloads\BLG_475E_Term_Project\Phase1\tasks\Java_027_flipCase\generated-code\DeepSeek-Coder-1.3B-Instruct-GGUF\Solution.java):
```

```java
/* @Authors
* Student Names: Mustafa Eren KOÇ, Onat Barış ERCAN
* Student IDs: 150190805, 150210075
*/

import java.util.*;
import java.lang.*;


class Solution {
    /**
    For a given string, flip lowercase characters to uppercase and uppercase to lowercase.
    >>> flipCase("Hello")
    "hELLO"
     */
    public String flipCase(String string) {
StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }
}



```

## 3. Accepted LLM response (distilled)

The portion of the response that we actually kept is the final `Solution.java`
committed at `generated-code/DeepSeek-Coder-1.3B-Instruct-GGUF/Solution.java`. Boilerplate that the model
repeated (imports, doc comments) was removed during normalisation. The
effective change applied to the tree was:

- Wrapped the per-char flip inside an `if/else if/else` that first checks `Character.isUpperCase(ch)`, then `Character.isLowerCase(ch)`, and finally appends the character unchanged for the else branch (digits, punctuation, whitespace).

## 4. Post-fix sanity check

After the fix landed, the dataset base test, the improved test and the
mutation-based test for `(Java_027_flipCase, DeepSeek-Coder-1.3B-Instruct-GGUF)` all compile and pass under
the Phase 1 pipeline (see `Phase1/results/base_coverage_results.csv` and
`Phase1/results/improved_coverage_results.csv`).

## 5. Usage note

This log satisfies the Phase 1 requirement to record the exact prompt, the
raw material the model had access to, and the rationale for every change
we applied in the refactoring loop.
