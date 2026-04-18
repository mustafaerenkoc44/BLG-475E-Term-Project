# Interaction Log - Test Improvement

- **Model:** `DeepSeek-Coder-1.3B-Instruct-GGUF`
- **Task:** `Java/6` (folder `Java_006_parseNestedParens`)
- **Step:** `test-improvement`
- **Base artifact:** `tasks/Java_006_parseNestedParens/generated-tests/improved/DeepSeek-Coder-1.3B-Instruct-GGUF/DatasetImprovedTest.java`

## 1. Audit of the base test suite

The LLM-generated *base* suite only exercises the dataset happy path. The
equivalence_classes.md table for this task lists several rows where coverage
was either `No` or `Partially` (see the verbatim excerpt below in
section 2). These are the classes we targeted in this improvement round.

## 2. Prompt issued to llama-cli

```text
You are assisting a student who is trying to *improve* an LLM-generated
JUnit 6 test suite for the following method.

Target: parseNestedParens in tasks/Java_006_parseNestedParens/generated-code/DeepSeek-Coder-1.3B-Instruct-GGUF/Solution.java.
Framework: JUnit 6, JaCoCo branch coverage, hand-written mutants (no PITest).

The existing DatasetImprovedTest.java only contains the dataset base test.
Using the equivalence classes listed below, propose 3-4 *additional* @Test
methods that (a) cover the classes marked as 'No' or 'Partially', (b) include
at least one mutation-based test, and (c) justify each assertion with a short
message referring to the class ID.

Equivalence classes document (verbatim):
---
# Equivalence Partitioning - Java/6

Method: `parseNestedParens`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Single group with depth one. | `paren_string="()"` | Yes | Expected output contains `1`. |
| V2 | Multiple groups with different depths. | `paren_string="(()()) ((())) () ((())()())"` | Yes | Prompt example. |
| V3 | A deeply nested single group. | `paren_string="(((())))"` | Yes (improved) | Stresses maximum-depth tracking. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unbalanced parentheses. | `paren_string="(()"` | Yes (improved) | Outside the stated balanced-input contract. |
| I2 | Unexpected tokens between groups. | `paren_string="() abc (())"` | Yes (improved) | Not defined by the prompt. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty input string. | `paren_string=""` | Yes (improved) | Added in improved tests. |
| B2 | Alternation between depth one and depth two. | `paren_string="() (())"` | Yes (improved) | Useful for branch transitions in the depth counter. |

---

Return only Java source (the methods and any extra imports). Do not restate
the boilerplate.
```

## 3. Raw model response (summary)

The local DeepSeek-Coder-1.3B-Instruct-GGUF run returned candidate methods that matched the general
shape of our ask. We reproduce the essential shape below rather than the
full raw completion because the model repeatedly restated the class
boilerplate we explicitly told it to skip. A condensed transcript is kept
at logs/DeepSeek-Coder-1.3B-Instruct-GGUF/Java_6_test-improvement.raw.txt when available;
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

- `improvedHandlesSingleDeepGroup_V3` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedDepthOneVsTwoAlternation_B2` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedEmptyInputProducesSingleZero_B1` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedMutationNonParenthesisTokensAreIgnored_I2` - mutation-based guard (see docs/analysis/mutation_testing_strategy.md)

## 6. Sanity check

The improved suite is re-compiled and executed by the Phase 1 CI workflow
(.github/workflows/phase1-ci.yml). The corresponding row in
Phase1/results/improved_coverage_results.csv is expected to report
compile_success=true and junit_success=true for (Java_006_parseNestedParens, DeepSeek-Coder-1.3B-Instruct-GGUF).
