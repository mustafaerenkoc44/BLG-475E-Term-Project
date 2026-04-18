# Coverage Notes - Java/6

## Base Tests
- `Qwen2.5-Coder-1.5B` base coverage: `7` covered / `1` missed (87.50%)
- `DeepSeek-Coder-1.3B` base coverage: `7` covered / `1` missed (87.50%)
### Missed Branches Before Improvement
- `Qwen2.5-Coder-1.5B` still missed `1` branch(es) before improvement.
- `DeepSeek-Coder-1.3B` still missed `1` branch(es) before improvement.
### Smells Observed
- missing empty-input boundary
- depth transitions under-specified

## Improved Tests
- `Qwen2.5-Coder-1.5B` improved coverage: `7` covered / `1` missed (87.50%; delta +0.00%)
- `DeepSeek-Coder-1.3B` improved coverage: `7` covered / `1` missed (87.50%; delta +0.00%)
### Newly Covered Behavior
- Improved tests mainly strengthened black-box evidence; branch coverage did not increase further on this task.
### Remaining Gaps
- `Qwen2.5-Coder-1.5B` retains `1` missed branch(es); the remaining gap is likely in a rare helper path or an out-of-contract branch.
- `DeepSeek-Coder-1.3B` retains `1` missed branch(es); the remaining gap is likely in a rare helper path or an out-of-contract branch.
