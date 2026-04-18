# BLG 475E - Term Project

[![Phase 1 CI](https://github.com/mustafaerenkoc44/BLG-475E-Term-Project/actions/workflows/phase1-ci.yml/badge.svg)](https://github.com/mustafaerenkoc44/BLG-475E-Term-Project/actions/workflows/phase1-ci.yml)

Course repository for the BLG 475E Software Quality and Testing term project. The repository is organized around two project phases:

- `Phase1`: LLM-based Java code generation, unit-test generation, black-box assessment, and branch-coverage analysis on 30 selected HumanEval-X Java prompts.
- `Phase2`: integration-testing extension centered on the `BookScan` class and prompt-combination experiments.

## Team

- Mustafa Eren KOC - `150190805`
- Onat Baris ERCAN - `150210075`

## Project Snapshot

- Phase 1 is complete and versioned with descriptive step-by-step commits.
- Two public local coder models were evaluated:
  - `Qwen2.5-Coder-1.5B-Instruct-GGUF`
  - `DeepSeek-Coder-1.3B-Instruct-GGUF`
- Final Phase 1 correctness:
  - `30/30` compiled and `30/30` base-test pass for both models
- Aggregate branch coverage:
  - Qwen base: `96.09%` (123 / 128)
  - Qwen improved: `98.44%` (126 / 128)
  - DeepSeek base: `98.46%` (128 / 130)
  - DeepSeek improved: `100.00%` (130 / 130)
- Every improved suite additionally contains at least one mutation-based
  test method (`improvedMutation...`) pinned to a specific operator
  family; see `Phase1/docs/analysis/mutation_testing_strategy.md`.

## Repository Layout

- `Phase1/`
  - selected prompts, raw LLM logs, generated Java solutions, generated JUnit tests, coverage outputs, black-box assessment files, refactoring logs, and report material
- `Phase2/`
  - `BookScan` implementation plan, integration-testing strategy, and Phase 2 starter structure
- `.github/workflows/phase1-ci.yml`
  - GitHub Actions workflow that reproduces the Phase 1 Python + PowerShell
    pipeline on a Linux runner, verifies every `Solution.java` is
    BOM-free, runs the base and improved coverage scripts end to end,
    and fails the build if any `(task, model)` pair regresses

## Phase 1 Deliverables

Phase 1 already includes:

- prompt selection and difficulty categorization for 30 HumanEval-X Java tasks
- raw prompt/response logging for both LLMs
- generated Java solutions and generated JUnit test suites
- dataset base-test execution results
- improved tests driven by branch coverage and black-box reasoning
- per-task equivalence partitioning, boundary analysis, and refactoring notes
- Phase 1 literature review draft and report draft

Start here if you want the Phase 1 result summary:

- `Phase1/docs/analysis/phase1_execution_report.md`
- `Phase1/docs/analysis/task_level_review.md`
- `Phase1/docs/report/phase1_report_draft.md`

## Phase 2 Direction

Phase 2 focuses on integration testing for a new `BookScan` class that combines the logic associated with:

- `Java/18` substring counting
- `Java/23` string length
- `Java/27` upper-lower case conversion

The `Phase2` folder now contains a clearer implementation and testing roadmap so the second phase can continue without restructuring the repository.

## Reproducibility Notes

- Java: JDK 21+
- Phase 1 build tool: Python 3.11 + PowerShell (see
  `Phase1/scripts/Run-BaseCoverage.ps1` and
  `Phase1/scripts/Run-ImprovedCoverage.ps1`)
- Phase 2 build tool: Maven 3.9+ (planned)
- Test framework: JUnit 6
- Coverage tool: JaCoCo
- Local inference runtime used in Phase 1: `llama.cpp`

## Submission Notes

- Java files include the required author header block.
- The Phase 1 report draft contains the repository URL and workload distribution in the acknowledgment section.
- The commit history is intentionally descriptive so the repository tells the development story step by step.
