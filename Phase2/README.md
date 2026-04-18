# Phase 2 - Integration Testing Extension

Phase 2 is complete. This folder contains the final `BookScan`
implementation, the integration/regression test suites that validate it, the
prompt-comparison experiment artefacts, and the supporting analysis/report
documents required by the assignment.

## Objective

`BookScan` determines:

- how many words of a given length appear in a text,
- on which lines those matches appear,
- how many times a requested whole word appears across lines, and
- how the helper behavior from HumanEval-X tasks `Java/18`, `Java/23`, and
  `Java/27` behaves inside a single integrated class.

The final implementation preserves those three helper semantics through:

- `howManyTimes(String, String)` for substring counting,
- `strlen(String)` for length calculation,
- `flipCase(String)` for case normalization support.

## Deliverables

- `src/main/java/BookScan.java`
  - selected final implementation used in the report and CI
- `src/test/java/BookScanIntegrationTest.java`
  - eleven cross-method integration scenarios for line scanning,
    tokenization, case-insensitive matching, punctuation handling,
    alphanumeric words, and invalid inputs
- `src/test/java/BookScanRegressionTest.java`
  - four focused regression tests that keep the Phase 1 helper semantics
    (`howManyTimes`, `strlen`, `flipCase`) callable from inside the
    composite class
- `generated-code/`
  - four candidate `BookScan` implementations generated from
    original-combined and edited-combined prompts for both models
- `results/`
  - prompt-comparison CSVs, summary markdown, and raw JUnit/JaCoCo artefacts
- `logs/`
  - prompt/response logs for code generation and integration-test generation
- `docs/analysis/`
  - execution report, prompt-strategy comparison, black-box assessment, and
    coverage summary
- `docs/report/phase2_report_draft.md`
  - Markdown Phase 2 report draft
- `docs/report/ieee/phase2_report.tex` + `phase2_report.bib`
  - camera-ready Phase 2 report in the IEEE conference template, sharing
    its bibliography style with the Phase 1 IEEE report

## Final Metrics

### Selected Final Implementation

- JUnit success: `15 / 15`
- branch coverage: `70 / 72 = 97.22%`
- line coverage: `109 / 110 = 99.09%`

### Prompt-Comparison Results

| Strategy | Model | JUnit Pass | Branch Coverage |
|---|---|---:|---:|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `7 / 15` | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `9 / 15` | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `15 / 15` | `66 / 68 = 97.06%` |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `15 / 15` | `61 / 64 = 95.31%` |
| selected-final | Manual-Selected | `15 / 15` | `70 / 72 = 97.22%` |

## Main Findings

- The original combined prompt was too vague about line numbering, token
  boundaries, punctuation, and case normalization; both models produced
  integration bugs as a result.
- The edited combined prompt fixed those ambiguities and immediately lifted
  both models to full JUnit success.
- The final selected implementation keeps the edited-prompt behavior but adds a
  few manual refinements that make the public API clearer and slightly improve
  branch coverage.
- The only remaining uncovered branches are defensive private-helper paths that
  are not reachable through the public specification.

## Recommended Reading Order

1. `docs/analysis/phase2_execution_report.md`
2. `docs/analysis/prompt_strategy_comparison.md`
3. `docs/analysis/black_box_assessment.md`
4. `results/prompt_comparison_summary.md`
5. `docs/report/phase2_report_draft.md` (Markdown) or
   `docs/report/ieee/phase2_report.tex` (IEEE conference template)

## Reproducibility

- local verification:
  - `mvn -f Phase2/pom.xml clean verify`
  - `powershell -ExecutionPolicy Bypass -File .\Phase2\scripts\Run-Phase2PromptComparison.ps1`
  - `powershell -ExecutionPolicy Bypass -File .\Phase2\scripts\Summarize-Phase2Results.ps1`
- optional automated mutation score (PITest):
  - `mvn -f Phase2/pom.xml -P mutation test`
  - reports: `Phase2/target/pit-reports/index.html` (HTML),
    `Phase2/target/pit-reports/mutations.xml` and `mutations.csv`
  - the `mutation` profile is disabled by default so that
    `mvn clean verify` stays fast; PITest is run explicitly when a
    machine-measured mutation score is needed, in addition to the Phase 1
    hand-crafted mutation guardrails inherited by `BookScan` via
    `howManyTimes`/`strlen`/`flipCase`.
- CI:
  - `.github/workflows/phase2-ci.yml`
