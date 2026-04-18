# BLG 475E Term Project Workspace

This repository is structured around the two project phases described in the course brief and now contains a completed Phase 1 workspace plus a prepared Phase 2 starting point.

The workspace now contains:

- `Phase1/`: completed warm-up phase with selected prompts, raw LLM logs, generated Java solutions, generated JUnit tests, coverage results, black-box assessment files, refactoring logs, and report material.
- `Phase2/`: extension phase placeholder for the `BookScan` integration-testing work.

## Current status

- Phase 1 prompt selection, generation, base testing, improved testing, coverage analysis, and task-level documentation are in place.
- The measured Phase 1 results currently show `30/30` base-test success for both public local models.
- Aggregate branch coverage is `96.09%` for `Qwen2.5-Coder-1.5B-Instruct-GGUF` and `98.46%` for `DeepSeek-Coder-1.3B-Instruct-GGUF`, increasing to `96.88%` and `99.23%` after the improved JUnit suites.

## Recommended next steps

1. Review `Phase1/docs/report/phase1_report_draft.md` and adapt it into the IEEE template.
2. Replace the author-header placeholders with your real names and IDs.
3. Use `Phase2/` to implement `BookScan` and the integration-testing experiments.
4. Keep the same descriptive commit style for every Phase 2 step.

## Commit style

Use commit messages that tell the story of the work:

- `Step 1: Added prompt selection rationale and categorized 30 tasks`
- `Step 2: Stored Qwen outputs for 10 easy prompts without edits`
- `Step 4: Improved boundary tests after JaCoCo branch analysis`

## CI

A GitHub Actions workflow is included at `.github/workflows/phase1-ci.yml` so the Phase 1 Maven project can be verified in GitHub.
