# Phase 2 Interaction Logs

This directory stores the Phase 2 prompt/response logs required by the course
brief.

Conventions:

- each log includes the full prompt sent to the model,
- the model response is reproduced in full,
- a short usage note explains how the response was kept, modified, or rejected.

Log groups:

- `original-combined-code-generation.md`
- `edited-combined-code-generation.md`
- `integration-test-generation.md`

Each `integration-test-generation.md` file also contains a scenario-to-test
mapping table that ties every scenario suggested by the model to the exact
JUnit 6 test method that implements it in the final repository (under
`Phase2/src/test/java`). Scenarios that the model did not surface but that
were later required by the black-box assessment or the original-prompt
failure analysis are clearly labelled as "added during repair".

These logs correspond to the generated artefacts under `Phase2/generated-code`
and the final tests under `Phase2/src/test/java`. The downstream mutation
analysis of the selected final `BookScan` (PITest under the Maven profile
`mutation`) is documented in `Phase2/README.md` and `Phase2/pom.xml`, not in
a separate prompt log, because PITest is driven entirely by the build rather
than by the LLM.
