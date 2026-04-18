# Prompt Selection Criteria

The starter set in `selected_prompts.csv` was chosen to create a balanced Phase 1 workload.

## Selection goals

1. Cover multiple input/output styles:
   - scalar numeric
   - strings
   - lists
   - parsing-oriented tasks
   - number-theory tasks
2. Include tasks with different testing pressure:
   - straightforward oracle checks
   - strong boundary sensitivity
   - whitespace or casing rules
   - duplicate, overlap, or tie behavior
   - nested-state behavior
3. Ensure continuity with Phase 2:
   - `Java/18`
   - `Java/23`
   - `Java/27`
4. Keep the set balanced across three difficulty labels:
   - 10 easy
   - 10 moderate
   - 10 hard

## Difficulty rubric

- `easy`: direct transformation or single-pass logic with a small branch surface.
- `moderate`: non-trivial control flow, ordering, tie behavior, or representation rules.
- `hard`: nested state, algorithmic search, number theory, or branch-heavy edge cases.

## Selected model pair

The executed Phase 1 workflow uses the following public base models and their local GGUF deployments:

- Qwen coder model: <https://huggingface.co/Qwen/Qwen2.5-Coder-1.5B-Instruct>
- DeepSeek coder model: <https://huggingface.co/deepseek-ai/deepseek-coder-1.3b-instruct>

This pair is suitable because both are public, code-focused, and small enough to execute locally with the same `llama.cpp` pipeline while still exposing different generation habits under identical prompts.

## Expected review

The empirical run kept the original `10 easy / 10 moderate / 10 hard` balance. The `review_status` column in `selected_prompts.csv` is now marked as `completed` for all 30 selected tasks.
