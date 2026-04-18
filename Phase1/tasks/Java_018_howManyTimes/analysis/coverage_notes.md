# Coverage Notes - Java/18

## Base Tests
- `Qwen2.5-Coder-1.5B` base coverage: `2` covered / `0` missed (100.00%)
- `DeepSeek-Coder-1.3B` base coverage: `4` covered / `0` missed (100.00%)
### Missed Branches Before Improvement
- Both models reached full branch coverage under the base JUnit suite for this task.
### Smells Observed
- empty-substring policy missing
- overlap behavior only sampled on short examples

## Improved Tests
- `Qwen2.5-Coder-1.5B` improved coverage: `2` covered / `0` missed (100.00%; delta +0.00%)
- `DeepSeek-Coder-1.3B` improved coverage: `4` covered / `0` missed (100.00%; delta +0.00%)
### Newly Covered Behavior
- Improved tests mainly strengthened black-box evidence; branch coverage did not increase further on this task.
### Remaining Gaps
- No remaining branch gap was observed after the improved JUnit suite.
