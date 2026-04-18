# Interaction Log - Test Improvement

- **Model:** `<model-name>`
- **Task:** `Java/<id>`
- **Step:** `test-improvement`
- **Timestamp:** `<YYYY-MM-DD HH:MM:SS>`

## 1. Audit of the base test suite

Briefly list the concrete test smells and the equivalence classes that were
not covered by `generated-tests/base/<model>/DatasetBaseTest.java`. Reference
the corresponding rows from `tasks/Java_<id>_*/analysis/equivalence_classes.md`.

## 2. Prompt issued to the LLM

Exact natural-language request sent to the local `llama-cli` with the
temperature/top-p settings documented in `results_summary_template.csv`.

```text
<paste prompt>
```

## 3. Raw model response

Verbatim completion returned by the model. Preserve whitespace so the grader
can reproduce the run with the same seed.

```text
<paste response>
```

## 4. Triage - kept / rewritten / rejected

A short table explaining what we did with each code block the model returned.

| Suggestion | Decision | Reason |
|---|---|---|
| ... | kept as-is / rewritten / rejected | ... |

## 5. Final additions to `DatasetImprovedTest.java`

List the JUnit method names that were added, one line each, tagged with the
equivalence class and the mutation operator from
`docs/analysis/mutation_testing_strategy.md`.

- `improved<Method>_<Class>` - covers `<V?/B?/I?>` / kills mutant `<ROR/AOR/...>`
- `improvedMutation<Scenario>_<Class>` - primary mutation test

## 6. Sanity check

Commands used to re-compile and re-run the improved suite locally, plus the
final JUnit + JaCoCo output snippet. Copy from the CI log when available.
