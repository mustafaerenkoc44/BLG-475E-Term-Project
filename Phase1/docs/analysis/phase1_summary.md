# Phase 1 Measured Summary

## Model Comparison

| Model | Compiled | Base Tests Passed | Covered Branches | Missed Branches | Aggregate Branch Coverage |
|---|---:|---:|---:|---:|---:|
| Qwen2.5-Coder-1.5B-Instruct-GGUF | 30/30 | 30/30 | 123 | 5 | 96.09% |
| DeepSeek-Coder-1.3B-Instruct-GGUF | 30/30 | 30/30 | 128 | 2 | 98.46% |

## Failure Breakdown

No remaining compile failures or base-test failures were present in the final measured state. Earlier generation defects were resolved during normalization and the documented repair loop.

## Notes

- Branch counts come from the converted dataset base tests executed under JaCoCo.
- Coverage is aggregated only for rows where the JUnit coverage run completed successfully.
- Tasks with `0/0` branches in the task-level review are branchless implementations under JaCoCo rather than failed coverage runs.
