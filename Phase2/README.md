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
  - twelve cross-method integration scenarios for line scanning,
    tokenization, case-insensitive matching, punctuation handling,
    alphanumeric words, mixed-case normalization, and invalid inputs
- `src/test/java/BookScanRegressionTest.java`
  - six focused regression and white-box hardening tests that keep the
    Phase 1 helper semantics (`howManyTimes`, `strlen`, `flipCase`)
    callable from inside the composite class and pin down private
    normalization defenses
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

- JUnit success: `18 / 18`
- branch coverage: `72 / 72 = 100.00%`
- line coverage: `110 / 110 = 100.00%`
- mutation score (PITest): `79 / 93 = 84.95%`

### Prompt-Comparison Results

| Strategy | Model | JUnit Pass | Branch Coverage |
|---|---|---:|---:|
| original-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `6 / 18` | n/a |
| original-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `8 / 18` | n/a |
| edited-combined | Qwen2.5-Coder-1.5B-Instruct-GGUF | `17 / 18` | n/a |
| edited-combined | DeepSeek-Coder-1.3B-Instruct-GGUF | `17 / 18` | n/a |
| selected-final | Manual-Selected | `18 / 18` | `72 / 72 = 100.00%` |

## Main Findings

- The original combined prompt was too vague about line numbering, token
  boundaries, punctuation, and case normalization; both models produced
  integration bugs as a result.
- The edited combined prompt fixed the public integration bugs, but both models
  still failed one stricter regression on blank-token canonicalization inside a
  private helper.
- The final selected implementation closes that last regression, reaches full
  JaCoCo branch/line coverage, and separates the camera-ready solution from the
  raw edited-prompt candidates.
- The remaining PITest survivors are concentrated in equivalent or defensive
  helper branches and are documented in
  `docs/analysis/mutation_assessment.md`.

## Recommended Reading Order

1. `docs/analysis/phase2_execution_report.md`
2. `docs/analysis/mutation_assessment.md`
3. `docs/analysis/prompt_strategy_comparison.md`
4. `docs/analysis/black_box_assessment.md`
5. `results/prompt_comparison_summary.md`
6. `docs/report/phase2_report_draft.md` (Markdown) or
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
  - last measured result in this repository:
    `79 / 93 = 84.95%` mutation score with `110 / 110` mutated lines covered
  - the `mutation` profile is disabled by default so that
    `mvn clean verify` stays fast; PITest is run explicitly when a
    machine-measured mutation score is needed, in addition to the Phase 1
    hand-crafted mutation guardrails inherited by `BookScan` via
    `howManyTimes`/`strlen`/`flipCase`.
- CI:
  - `.github/workflows/phase2-ci.yml`
