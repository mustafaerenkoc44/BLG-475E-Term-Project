# Logs

Every interaction with each local code-LLM is stored here, organised by
model name and task. Two tracked models are present:

- `Qwen2.5-Coder-1.5B-Instruct-GGUF/`
- `DeepSeek-Coder-1.3B-Instruct-GGUF/`

For every task we keep up to three log files per model:

| Suffix | Step | Always present? |
|---|---|---|
| `Java_<N>_code-generation.md` | initial raw generation | yes (30 tasks x 2 models = 60 files) |
| `Java_<N>_test-improvement.md` | improved-test design loop | yes (60 files) |
| `Java_<N>_refactoring.md` | repair loop (only when base tests failed) | only for the seven repaired tasks |

## Required content per log

Following the course brief, every log includes:

1. **Full prompt** sent to the LLM (`## Full Prompt` or `## Prompt issued
   to llama-cli`).
2. **Agent response** (`## Raw Model Response` for code-generation /
   refactoring; `## Raw model response (summary)` for the
   test-improvement step, see disclosure below).
3. **Brief usage note** describing how the output was used or why it was
   modified (`## Usage Note`, `## Triage`, `## Final additions`, or
   `## Sanity check`).

## Capture policy disclosure (test-improvement logs)

The local `llama.cpp` runs for the test-improvement step were explicitly
instructed to *not* restate the JUnit boilerplate in the response. The
local runs nevertheless restated that boilerplate repeatedly. Reproducing
the verbatim raw response in the Markdown log would therefore add
hundreds of lines of duplicated boilerplate and obscure the actual
decisions that drove the improved suite.

For that reason every `Java_<N>_test-improvement.md` log contains:

- the **full prompt** (verbatim, including the equivalence-classes
  document that was attached to it),
- a **summary of the raw response** that lists what the model proposed,
- a **triage table** classifying every suggestion as *kept*, *rewritten*,
  or *rejected*, with the reason,
- the **final additions** that landed in
  `tasks/Java_<N>_*/generated-tests/improved/<model>/DatasetImprovedTest.java`,
- a **sanity-check** pointer back to the CI row that re-runs the suite.

The complete audit trail therefore consists of the log file plus the
versioned `DatasetImprovedTest.java` produced by it; both are stored in
this repository. The same disclosure applies to a small subset of
refactoring logs that condensed long boilerplate echoes.

The verbatim code-generation logs (`Java_<N>_code-generation.md`) and the
Phase 2 logs (`../../Phase2/logs/`) record the full prompt and the full
unmodified response without any condensation.

## Creating new log stubs

`./scripts/New-InteractionLog.ps1 -ModelName "<model>" -TaskId "Java/18" -Step "code-generation"`
