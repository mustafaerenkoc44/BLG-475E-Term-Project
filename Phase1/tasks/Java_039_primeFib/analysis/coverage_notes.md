# Coverage Notes - Java/39

## Base Tests
- `Qwen2.5-Coder-1.5B` base coverage: `9` covered / `1` missed (90.00%)
- `DeepSeek-Coder-1.3B` base coverage: `10` covered / `0` missed (100.00%)
### Missed Branches Before Improvement
- `Qwen2.5-Coder-1.5B` still missed `1` branch(es) before improvement.
### Smells Observed
- few performance-sensitive inputs
- indexing boundary lightly sampled

## Improved Tests
- `Qwen2.5-Coder-1.5B` improved coverage: `9` covered / `1` missed (90.00%; delta +0.00%)
- `DeepSeek-Coder-1.3B` improved coverage: `10` covered / `0` missed (100.00%; delta +0.00%)
### Newly Covered Behavior
- Improved tests mainly strengthened black-box evidence; branch coverage did not increase further on this task.
### Remaining Gaps
- `Qwen2.5-Coder-1.5B` retains `1` missed branch(es); the remaining gap is likely in a rare helper path or an out-of-contract branch.
