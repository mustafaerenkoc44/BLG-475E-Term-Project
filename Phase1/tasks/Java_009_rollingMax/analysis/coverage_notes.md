# Coverage Notes - Java/9

## Base Tests
- `Qwen2.5-Coder-1.5B` base coverage: `7` covered / `1` missed (87.50%)
- `DeepSeek-Coder-1.3B` base coverage: `7` covered / `1` missed (87.50%)
### Missed Branches Before Improvement
- `Qwen2.5-Coder-1.5B` still missed `1` branch(es) before improvement.
- `DeepSeek-Coder-1.3B` still missed `1` branch(es) before improvement.
### Smells Observed
- missing empty-input boundary
- few equality-versus-greater comparisons

## Improved Tests
- `Qwen2.5-Coder-1.5B` improved coverage: `8` covered / `0` missed (100.00%; delta +12.50%)
- `DeepSeek-Coder-1.3B` improved coverage: `8` covered / `0` missed (100.00%; delta +12.50%)
### Newly Covered Behavior
- `Qwen2.5-Coder-1.5B` gained coverage after targeting empty input and duplicate maxima.
- `DeepSeek-Coder-1.3B` gained coverage after targeting empty input and duplicate maxima.
### Remaining Gaps
- No remaining branch gap was observed after the improved JUnit suite.
