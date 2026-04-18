# Phase 2 Prompt Comparison Summary

| Strategy | Model | Compiled | JUnit | Tests Found | Tests Failed | Pass Rate | Branch Covered | Branch Missed | Branch Coverage | Notes |
|---|---|---|---|---:|---:|---:|---:|---:|---:|---|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | true | false | 15 | 8 | 46.67% | 0 | 0 | n/a | junit failure |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | true | false | 15 | 6 | 60.00% | 0 | 0 | n/a | junit failure |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | true | true | 15 | 0 | 100.00% | 66 | 2 | 97.06% | ok |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | true | true | 15 | 0 | 100.00% | 61 | 3 | 95.31% | ok |
| selected-final | Manual-Selected | true | true | 15 | 0 | 100.00% | 70 | 2 | 97.22% | ok |
