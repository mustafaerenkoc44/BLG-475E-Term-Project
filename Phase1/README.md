# Phase 1

This folder contains the full Phase 1 workspace for the warm-up phase of the project:

- selecting two public code-capable LLMs,
- selecting and categorizing 30 HumanEval-X Java prompts,
- storing raw generations without editing the generated code,
- adapting and extending base tests,
- measuring branch coverage,
- assessing tests with black-box techniques,
- logging every LLM interaction,
- preparing Phase 1 report material.

## Preselected public LLMs

The executed Phase 1 pipeline uses the following two publicly available coder models through local GGUF deployments:

1. `Qwen/Qwen2.5-Coder-1.5B-Instruct`
2. `deepseek-ai/deepseek-coder-1.3b-instruct`

Rationale:

- both are public model releases,
- both are explicitly code-oriented,
- both have instruction-tuned variants suitable for semi-agentic or agentic prompting,
- both run locally with modest hardware through `llama.cpp`,
- they provide a direct comparison between two lightweight open coder models under the same generation and testing pipeline.

The stored outputs in this repository correspond to the local GGUF execution variants:

- `Qwen2.5-Coder-1.5B-Instruct-GGUF`
- `DeepSeek-Coder-1.3B-Instruct-GGUF`

## What is already set up and executed

- `pom.xml`: Maven project with JUnit 6 and JaCoCo.
- `data/selected_prompts.csv`: a balanced 30-task starter set.
- `data/selection_criteria.md`: difficulty and selection rationale.
- `scripts/Download-HumanEvalJavaDataset.ps1`: fetches the official Java dataset file.
- `scripts/Setup-Phase1Toolchain.ps1`: downloads workspace-local Java, JUnit, JaCoCo, and `llama.cpp`.
- `scripts/Download-Phase1Models.ps1`: downloads two small public local models in GGUF format.
- `scripts/Build-Phase1Workspace.ps1`: expands the selected tasks into task folders with templates.
- `scripts/Generate-SelectedSolutions.ps1`: generates raw local-model solutions for the selected tasks.
- `scripts/Normalize-GeneratedSolutions.ps1`: rebuilds `Solution.java` files from raw model output with stronger cleanup heuristics.
- `scripts/Run-BaseTests.ps1`: compiles each generated solution with the dataset base test and records pass/fail status.
- `scripts/Prepare-BaseJUnitTests.ps1`: converts dataset base tests into JUnit 6 test classes.
- `scripts/Prepare-ImprovedJUnitTests.ps1`: creates improved JUnit tests by extending the base JUnit tests with extra edge and boundary cases.
- `scripts/Run-BaseCoverage.ps1`: runs the generated JUnit base tests with JaCoCo and records branch coverage.
- `scripts/Run-ImprovedCoverage.ps1`: runs the improved JUnit tests with JaCoCo and records branch coverage.
- `scripts/Summarize-Phase1Results.ps1`: merges base-test and coverage outputs into report-ready summaries.
- `scripts/Summarize-ImprovedCoverage.ps1`: compares base and improved coverage in report-ready Markdown.
- `scripts/Add-AuthorHeaders.ps1`: prepends the required submission header template to generated Java code and test files.
- `scripts/Finalize-Phase1Analysis.ps1`: populates task-level black-box, coverage, and refactoring documents from measured results.
- `scripts/New-InteractionLog.ps1`: creates structured log files for every LLM interaction.
- `docs/analysis/*`: tables and checklists for coverage, test smells, and result summaries.
- `docs/report/phase1_report_draft.md`: report-ready Phase 1 draft content with measured results.
- `docs/report/phase1_report_outline.md`: report skeleton aligned with the assignment.
- `docs/literature/*`: paper matrix plus a sourced literature-review draft.

## Final measured status

- Base correctness:
  - `Qwen2.5-Coder-1.5B-Instruct-GGUF`: `30/30` compile, `30/30` base-test pass
  - `DeepSeek-Coder-1.3B-Instruct-GGUF`: `30/30` compile, `30/30` base-test pass
- Aggregate base branch coverage:
  - `Qwen2.5-Coder-1.5B-Instruct-GGUF`: `123 covered / 5 missed` (`96.09%`)
  - `DeepSeek-Coder-1.3B-Instruct-GGUF`: `128 covered / 2 missed` (`98.46%`)
- Aggregate improved branch coverage:
  - `Qwen2.5-Coder-1.5B-Instruct-GGUF`: `126 covered / 2 missed` (`98.44%`)
  - `DeepSeek-Coder-1.3B-Instruct-GGUF`: `130 covered / 0 missed` (`100.00%`)
- Mutation-based guardrails:
  - Every (task, model) pair has at least one `improvedMutation...` JUnit
    method pinned to a specific operator family (ROR / AOR / NPE / BND /
    RV / LCR / UOI / EXC); the full inventory is in
    `docs/analysis/mutation_testing_strategy.md`.
  - All 60 improved suites (30 tasks x 2 models) now report
    `compile_success=true` and `junit_success=true`.

## Suggested workflow

1. Review `docs/analysis/phase1_execution_report.md` for the execution narrative.
2. Review `docs/analysis/task_level_review.md` and the per-task folders for black-box and coverage evidence.
3. Review `docs/literature/phase1_literature_review.md` and adapt it to your report voice if needed.
4. Move the content into the official IEEE template.
5. Replace all author placeholders before submission.

## Tooling assumptions

- Java: JDK 21 or newer
- Pipeline driver: Python 3.11 + PowerShell (scripts in `scripts/` and
  `scripts/python/`)
- Test framework: JUnit 6 (`junit-platform-console-standalone-6.0.3.jar`)
- Coverage tool: JaCoCo 0.8.12 (agent + CLI under `.tools/`)
- Local inference runtime: `llama.cpp`

## Literature review note

The repository now includes a sourced literature-review draft and filled paper matrix so the report section can be finalized quickly. If you want strict compliance with the course note about manual writing, use those files as structured notes and rewrite the prose in your own final report voice.
