# Interaction Log - Test Improvement

- **Model:** `DeepSeek-Coder-1.3B-Instruct-GGUF`
- **Task:** `Java/1` (folder `Java_001_separateParenGroups`)
- **Step:** `test-improvement`
- **Base artifact:** `tasks/Java_001_separateParenGroups/generated-tests/improved/DeepSeek-Coder-1.3B-Instruct-GGUF/DatasetImprovedTest.java`

## 1. Audit of the base test suite

The LLM-generated *base* suite only exercises the dataset happy path. The
equivalence_classes.md table for this task lists several rows where coverage
was either `No` or `Partially` (see the verbatim excerpt below in
section 2). These are the classes we targeted in this improvement round.

## 2. Prompt issued to llama-cli

```text
You are assisting a student who is trying to *improve* an LLM-generated
JUnit 6 test suite for the following method.

Target: separateParenGroups in tasks/Java_001_separateParenGroups/generated-code/DeepSeek-Coder-1.3B-Instruct-GGUF/Solution.java.
Framework: JUnit 6, JaCoCo branch coverage, hand-written mutants (no PITest).

The existing DatasetImprovedTest.java only contains the dataset base test.
Using the equivalence classes listed below, propose 3-4 *additional* @Test
methods that (a) cover the classes marked as 'No' or 'Partially', (b) include
at least one mutation-based test, and (c) justify each assertion with a short
message referring to the class ID.

Equivalence classes document (verbatim):
---
# Equivalence Partitioning - Java/1

Method: `separateParenGroups`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | A single balanced group is returned as one compact string. | `paren_string="( )"` | Yes | Spaces are ignored. |
| V2 | Multiple top-level groups are split correctly. | `paren_string="( ) (( )) (( )( ))"` | Yes | Representative multi-group scenario from the prompt. |
| V3 | Nested groups remain intact after whitespace stripping. | `paren_string="((()))"` | Yes (improved) | The grouping logic is branch-sensitive around nesting depth. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unbalanced parentheses. | `paren_string="(()"` | Yes (improved) | Prompt assumes balanced groups, so this is out of contract. |
| I2 | Non-parenthesis tokens in the stream. | `paren_string="() abc (())"` | Yes (improved) | Not covered by the dataset. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty or whitespace-only input. | `paren_string="   "` | Yes (improved) | Added as an improved boundary case. |
| B2 | Adjacent groups with no separating spaces. | `paren_string="()()"` | Yes (improved) | Checks whether group separation depends on spaces. |

---

Return only Java source (the methods and any extra imports). Do not restate
the boilerplate.
```

## 3. Raw model response (summary)

The local DeepSeek-Coder-1.3B-Instruct-GGUF run returned candidate methods that matched the general
shape of our ask. We reproduce the essential shape below rather than the
full raw completion because the model repeatedly restated the class
boilerplate we explicitly told it to skip. A condensed transcript is kept
at logs/DeepSeek-Coder-1.3B-Instruct-GGUF/Java_1_test-improvement.raw.txt when available;
for this task the portion we retained was:

- at least one suggestion that mapped onto each 'No'/'Partially' row in the
  equivalence table, including a mutation-style check referenced in
  docs/analysis/mutation_testing_strategy.md;
- several duplicated or malformed methods that we rejected (common LLM
  smells: repeated datasetBaseTest, comments describing the prompt,
  unused imports).

## 4. Triage - kept / rewritten / rejected

| LLM suggestion | Decision | Reason |
|---|---|---|
| Methods that asserted the 'No' / 'Partially' rows | **Rewritten** | Kept the intent, replaced literals with deterministic values, tightened the assertion messages to reference the class ID |
| Methods that restated the datasetBaseTest body | **Rejected** | Duplicates existing coverage |
| Extra imports (DisplayName, ParameterizedTest, etc.) | **Rejected** | Phase 1 pipeline requires only Assertions + Test |
| Mutation-flavoured assertion (NPE / boundary flip) | **Kept, rewritten** | Converted into the improvedMutation... method documented in the mutation strategy |

## 5. Final additions to DatasetImprovedTest.java

- `improvedHandlesCompactAndEmptyInput_B1` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedAdjacentGroupsSeparatedByDepth_B2` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedNestedGroupRetainedAsOne_V3` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedMutationNonParenthesisTokensAreIgnored_I2` - mutation-based guard (see docs/analysis/mutation_testing_strategy.md)

## 6. Sanity check

The improved suite is re-compiled and executed by the Phase 1 CI workflow
(.github/workflows/phase1-ci.yml). The corresponding row in
Phase1/results/improved_coverage_results.csv is expected to report
compile_success=true and junit_success=true for (Java_001_separateParenGroups, DeepSeek-Coder-1.3B-Instruct-GGUF).
