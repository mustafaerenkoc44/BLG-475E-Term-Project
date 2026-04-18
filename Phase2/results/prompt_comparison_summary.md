# Phase 2 Prompt Comparison Summary

| Strategy | Model | Compiled | JUnit | Tests Found | Tests Failed | Pass Rate | Branch Covered | Branch Missed | Branch Coverage | Notes |
|---|---|---|---|---:|---:|---:|---:|---:|---:|---|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | true | false | 18 | 12 | 33.33% | 0 | 0 | n/a | junit failure |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | true | false | 18 | 10 | 44.44% | 0 | 0 | n/a | junit failure |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | true | false | 18 | 1 | 94.44% | 0 | 0 | n/a | junit failure |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | true | false | 18 | 1 | 94.44% | 0 | 0 | n/a | junit failure |
| selected-final | Manual-Selected | true | true | 18 | 0 | 100.00% | 72 | 0 | 100.00% | ok |
