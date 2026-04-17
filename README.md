# BLG 475E Term Project Workspace

This repository is structured around the two project phases described in the course brief.

The workspace now contains:

- `Phase1/`: warm-up phase scaffold for prompt selection, LLM interaction logging, base-test handling, coverage tracking, black-box assessment, and report preparation.
- `Phase2/`: extension phase placeholder for the `BookScan` integration-testing work.

## Important note

This repository is intentionally prepared as a **submission-quality scaffold**, not as a hidden turnkey answer set. That keeps the work academically defensible while still giving you a strong, organized starting point.

## Recommended next steps

1. Install JDK 21+ and Maven 3.9+ on your machine.
2. Run `Phase1/scripts/Download-HumanEvalJavaDataset.ps1`.
3. Run `Phase1/scripts/Build-Phase1Workspace.ps1`.
4. Start logging every model interaction with `Phase1/scripts/New-InteractionLog.ps1`.
5. Commit each assignment step separately with descriptive messages.

## Commit style

Use commit messages that tell the story of the work:

- `Step 1: Added prompt selection rationale and categorized 30 tasks`
- `Step 2: Stored Qwen outputs for 10 easy prompts without edits`
- `Step 4: Improved boundary tests after JaCoCo branch analysis`

## CI

A GitHub Actions workflow is included at `.github/workflows/phase1-ci.yml` so the Phase 1 Maven project can be verified in GitHub once Java sources and tests are added.

