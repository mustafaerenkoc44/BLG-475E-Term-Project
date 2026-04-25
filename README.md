# BLG 475E - Term Project

[![Phase 1 CI](https://github.com/mustafaerenkoc44/BLG-475E-Term-Project/actions/workflows/phase1-ci.yml/badge.svg)](https://github.com/mustafaerenkoc44/BLG-475E-Term-Project/actions/workflows/phase1-ci.yml)
[![Phase 2 CI](https://github.com/mustafaerenkoc44/BLG-475E-Term-Project/actions/workflows/phase2-ci.yml/badge.svg)](https://github.com/mustafaerenkoc44/BLG-475E-Term-Project/actions/workflows/phase2-ci.yml)

Course repository for the BLG 475E Software Quality and Testing term project.
The repository is organized around two connected deliverables:

- `Phase1`: LLM-based Java code generation, unit-test generation, black-box
  assessment, mutation guardrails, and branch-coverage analysis on 30 selected
  HumanEval-X Java prompts.
- `Phase2`: integration-testing extension centered on a composite `BookScan`
  class that reuses the semantics of HumanEval-X tasks `Java/18`, `Java/23`,
  and `Java/27`.

## Team

The two-person group split the workload along two complementary tracks
and cross-reviewed each other's deliverables before merging them. The
same split applied to both Phase 1 and Phase 2.

- **Mustafa Eren KOC** - `150190805` - *engineering and automation
  track*: repository scaffolding, GitHub Actions CI workflows, PowerShell
  + Python driver layer, local `llama.cpp` pipeline, dataset-to-JUnit
  adapters, JaCoCo automation, Maven and PITest configuration, `BookScan`
  reference implementation, prompt-comparison harness, Git commit
  discipline.
- **Onat Baris ERCAN** - `150210075` - *quality and analysis track*:
  30-prompt selection rationale and difficulty categorisation, per-task
  equivalence-partitioning and boundary-value documents, test-smell audit,
  hand-crafted mutation-operator catalogue and `improvedMutation...`
  JUnit method authoring, `BookScan` black-box assessment, original-versus
  -edited prompt-strategy design, failure-mode classification, five-paper
  literature review, and IEEE journal manuscript drafting.

## Project Snapshot

- Phase 1 is complete and versioned with descriptive step-by-step commits.
- Phase 2 is complete and includes both prompt-comparison experiments and a
  selected final implementation validated through Maven, JUnit 6, and JaCoCo.
- Two public local coder models were evaluated across both phases:
  - `Qwen2.5-Coder-1.5B-Instruct-GGUF`
  - `DeepSeek-Coder-1.3B-Instruct-GGUF`

### Phase 1 Highlights

- `30/30` selected tasks compiled and passed dataset base tests for both models
- aggregate branch coverage:
  - Qwen base: `96.09%` (`123 / 128`)
  - Qwen improved: `98.44%` (`126 / 128`)
  - DeepSeek base: `98.46%` (`128 / 130`)
  - DeepSeek improved: `100.00%` (`130 / 130`)
- every improved suite contains at least one mutation-guardrail method
  (`improvedMutation...`)

### Phase 2 Highlights

- final `BookScan` implementation passed `18/18` integration and regression
  tests
- final `BookScan` branch coverage: `100.00%` (`72 / 72`)
- final `BookScan` line coverage: `100.00%` (`110 / 110`)
- final `BookScan` mutation score: `84.95%` (`79 / 93` mutants killed)
- prompt-combination experiment results:
  - original combined prompt, Qwen: `6/18` tests passed
  - original combined prompt, DeepSeek: `8/18` tests passed
  - edited combined prompt, Qwen: `17/18` tests passed
  - edited combined prompt, DeepSeek: `17/18` tests passed
- the selected final implementation is the only variant that clears the full
  strengthened suite, including the blank-token canonicalization regression
- JaCoCo leaves no residual branch or line misses; the remaining mutation
  survivors are documented as equivalent or defensive helper behavior

## Repository Layout

- `Phase1/`
  - selected prompts, raw LLM logs, generated Java solutions, generated JUnit
    tests, coverage outputs, black-box assessment files, refactoring logs, and
    report material
- `Phase2/`
  - final `BookScan` implementation, integration and regression test suites,
    prompt-comparison candidate implementations, result CSVs, interaction logs,
    analysis docs, and Phase 2 report draft
- `.github/workflows/phase1-ci.yml`
  - reproduces the Phase 1 Python + PowerShell coverage pipeline on GitHub
    Actions
- `.github/workflows/phase2-ci.yml`
  - runs `mvn clean verify`, executes the prompt-comparison harness,
    summarizes the CSV results, runs an optional PITest profile summary, and
    uploads Phase 2 artefacts

## Start Here

Phase 1 result summary:

- `Phase1/docs/analysis/phase1_execution_report.md`
- `Phase1/docs/analysis/task_level_review.md`
- `Phase1/docs/analysis/mutation_testing_strategy.md`
- `Phase1/docs/report/phase1_report_draft.md` (Markdown draft)
- `Phase1/docs/report/ieee/phase1_report.tex` +
  `phase1_report.bib` (IEEE conference template)

Phase 2 result summary:

- `Phase2/docs/analysis/phase2_execution_report.md`
- `Phase2/docs/analysis/mutation_assessment.md`
- `Phase2/docs/analysis/prompt_strategy_comparison.md`
- `Phase2/docs/analysis/black_box_assessment.md`
- `Phase2/docs/report/phase2_report_draft.md` (Markdown draft)
- `Phase2/docs/report/ieee/phase2_report.tex` +
  `phase2_report.bib` (IEEE conference template)

## Reproducibility Notes

- Java: JDK 21+
- Phase 1 orchestration: PowerShell 7 + Python 3.11
- Phase 2 build tool: Maven 3.9+
- Test framework: JUnit 6
- Coverage tool: JaCoCo 0.8.12
- Local inference runtime used for the prompt-comparison artefacts:
  `llama.cpp`

## Submission Notes

- Java files include the required author header block.
- Both phases contain raw interaction logs that record prompts, model outputs,
  and a short usage note.
- The report drafts contain the repository URL and member role allocation in the
  acknowledgement section.
- The commit history is intentionally descriptive so the repository tells the
  development story step by step.
