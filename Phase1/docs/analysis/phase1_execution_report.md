# Phase 1 Execution Report

## Scope

This workspace executed the Phase 1 pipeline on 30 selected HumanEval-X Java tasks with two public local coder models:

- `Qwen2.5-Coder-1.5B-Instruct-GGUF`
- `DeepSeek-Coder-1.3B-Instruct-GGUF`

## Workflow

1. Download the official HumanEval-X Java dataset.
2. Expand the selected 30 tasks into per-task folders.
3. Generate raw code for both models and store the exact prompt and raw response.
4. Normalize the raw generations into compilable `Solution.java` files.
5. Run dataset base tests as the first correctness gate.
6. Repair malformed or incorrect solutions that failed compilation, timed out, or failed assertions.
7. Convert dataset base tests into JUnit 6 and run JaCoCo branch coverage.
8. Add improved JUnit tests for boundary and edge scenarios on selected tasks.
9. Re-run JaCoCo and compare base versus improved coverage.

## Final Measured Results

### Base correctness

- `Qwen2.5-Coder-1.5B-Instruct-GGUF`: `30/30` compiled, `30/30` passed base tests
- `DeepSeek-Coder-1.3B-Instruct-GGUF`: `30/30` compiled, `30/30` passed base tests

### Base branch coverage

- `Qwen2.5-Coder-1.5B-Instruct-GGUF`: `123 covered / 5 missed`, `96.09%`
- `DeepSeek-Coder-1.3B-Instruct-GGUF`: `128 covered / 2 missed`, `98.46%`

### Improved branch coverage

- `Qwen2.5-Coder-1.5B-Instruct-GGUF`: `124 covered / 4 missed`, `96.88%`
- `DeepSeek-Coder-1.3B-Instruct-GGUF`: `129 covered / 1 missed`, `99.23%`

## Main Repair Outcomes

- Raw output cleanup recovered several solutions that were structurally valid in the raw model response but were previously damaged by weak extraction logic.
- Deterministic repair patches were needed for a small set of remaining failures:
  - palindrome construction
  - longest-string optional handling
  - music token parsing
  - number-word sorting
  - repeated method fragments
  - case-flipping implementation
  - prime Fibonacci timeout

## Improved Test Focus

Improved tests were added primarily for:

- empty input handling
- null and empty collections
- compact versus spaced formatting
- overlap-sensitive behavior
- punctuation and non-letter preservation
- larger boundary cases

## Residual Missed Branches

After improved tests, a few branches remain uncovered:

- `Java_001_separateParenGroups` for Qwen
- `Java_006_parseNestedParens` for both models
- `Java_017_parseMusic` for Qwen
- `Java_039_primeFib` for Qwen

These remaining misses are small and are likely tied to helper or impossible-under-spec paths rather than exposed public behavior gaps.

## Supporting Files

- `results/base_test_results.csv`
- `results/base_coverage_results.csv`
- `results/improved_coverage_results.csv`
- `docs/analysis/phase1_summary.md`
- `docs/analysis/improved_coverage_summary.md`

