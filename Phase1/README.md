# Phase 1

This folder is prepared for the warm-up phase of the project:

- selecting two public code-capable LLMs,
- selecting and categorizing 30 HumanEval-X Java prompts,
- storing raw generations without editing the generated code,
- adapting and extending base tests,
- measuring branch coverage,
- assessing tests with black-box techniques,
- logging every LLM interaction,
- preparing Phase 1 report material.

## Preselected public LLMs

The scaffold assumes the following two publicly available models:

1. `Qwen/Qwen2.5-Coder-7B-Instruct`
2. `deepseek-ai/DeepSeek-Coder-V2-Lite-Instruct`

Rationale:

- both are public model releases,
- both are explicitly code-oriented,
- both have instruction-tuned variants suitable for semi-agentic or agentic prompting,
- they offer a clean comparison between a dense smaller coder model and a larger MoE-style coder model.

You can replace them, but then update the logs, report tables, and folder names consistently.

## What is already set up

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
- `scripts/New-InteractionLog.ps1`: creates structured log files for every LLM interaction.
- `docs/analysis/*`: tables and checklists for coverage, test smells, and result summaries.
- `docs/report/phase1_report_outline.md`: report skeleton aligned with the assignment.
- `docs/literature/*`: manual-only literature review placeholders.

## What you still need to do manually

- run the two actual LLMs,
- paste raw prompts and raw responses into the logs,
- save generated Java code under each task folder,
- convert dataset tests into your JUnit test layout,
- run coverage locally or in CI,
- perform equivalence partitioning and boundary analysis,
- write the literature review manually without LLM assistance,
- write the final report in the official IEEE template.

## Suggested workflow

1. Download the dataset with `scripts/Download-HumanEvalJavaDataset.ps1`.
2. Set up the local toolchain with `scripts/Setup-Phase1Toolchain.ps1`.
3. Download the two local GGUF models with `scripts/Download-Phase1Models.ps1`.
4. Expand the selected 30 tasks with `scripts/Build-Phase1Workspace.ps1`.
5. Generate raw solutions with `scripts/Generate-SelectedSolutions.ps1`.
6. Run the base dataset tests first.
7. Improve tests after smell review and branch coverage analysis.
8. Perform black-box assessment and record missing classes and boundaries.
9. If necessary, refactor the code through a new LLM round and log that interaction too.
10. Fill the analysis CSV/Markdown templates and convert them into report tables/figures.

## Tooling assumptions

- Java: JDK 21 or newer
- Build tool: Maven 3.9 or newer
- Test framework: JUnit 6
- Coverage tool: JaCoCo

## Literature review warning

Per the assignment, the literature review content should be written manually. The placeholders in `docs/literature/` are there to help you organize sources, not to auto-generate the section.
