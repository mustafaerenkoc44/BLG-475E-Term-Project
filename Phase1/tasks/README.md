# Task Workspace

Run `../scripts/Build-Phase1Workspace.ps1` after downloading the dataset.

The script creates one folder per selected HumanEval-X task with:

- dataset prompt and declaration
- dataset example and base tests
- generated-code folders for both models
- generated-tests folders for both models
- analysis templates for equivalence classes, coverage, and refactoring

By default, the script does **not** copy the canonical solutions into the workspace. If you explicitly need them for a separate audit, use the `-IncludeCanonicalSolution` switch and review your academic policy before doing so.

