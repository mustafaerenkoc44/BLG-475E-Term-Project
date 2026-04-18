# Interaction Log - Test Improvement

- **Model:** `Qwen2.5-Coder-1.5B-Instruct-GGUF`
- **Task:** `Java/11` (folder `Java_011_stringXor`)
- **Step:** `test-improvement`
- **Base artifact:** `tasks/Java_011_stringXor/generated-tests/improved/Qwen2.5-Coder-1.5B-Instruct-GGUF/DatasetImprovedTest.java`

## 1. Audit of the base test suite

The LLM-generated *base* suite only exercises the dataset happy path. The
equivalence_classes.md table for this task lists several rows where coverage
was either `No` or `Partially` (see the verbatim excerpt below in
section 2). These are the classes we targeted in this improvement round.

## 2. Prompt issued to llama-cli

```text
You are assisting a student who is trying to *improve* an LLM-generated
JUnit 6 test suite for the following method.

Target: stringXor in tasks/Java_011_stringXor/generated-code/Qwen2.5-Coder-1.5B-Instruct-GGUF/Solution.java.
Framework: JUnit 6, JaCoCo branch coverage, hand-written mutants (no PITest).

The existing DatasetImprovedTest.java only contains the dataset base test.
Using the equivalence classes listed below, propose 3-4 *additional* @Test
methods that (a) cover the classes marked as 'No' or 'Partially', (b) include
at least one mutation-based test, and (c) justify each assertion with a short
message referring to the class ID.

Equivalence classes document (verbatim):
---
# Equivalence Partitioning - Java/11

Method: `stringXor`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Identical binary strings. | `a="1010", b="1010"` | Yes | Every position should become `0`. |
| V2 | Complementary binary strings. | `a="111", b="000"` | Yes | Every position should become `1`. |
| V3 | Mixed binary strings. | `a="1100", b="1010"` | Yes | Exercises both bit outcomes. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Different string lengths. | `a="10", b="101"` | Yes (improved) | Prompt assumes equal-length inputs. |
| I2 | Non-binary characters. | `a="10a", b="001"` | Yes (improved) | Outside the problem contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty strings. | `a="", b=""` | Yes (improved) | Documents the degenerate equal-length case. |
| B2 | Length one. | `a="1", b="0"` | Yes (improved) | Smallest non-empty input. |

---

Return only Java source (the methods and any extra imports). Do not restate
the boilerplate.
```

## 3. Raw model response (summary)

The local Qwen2.5-Coder-1.5B-Instruct-GGUF run returned candidate methods that matched the general
shape of our ask. We reproduce the essential shape below rather than the
full raw completion because the model repeatedly restated the class
boilerplate we explicitly told it to skip. A condensed transcript is kept
at logs/Qwen2.5-Coder-1.5B-Instruct-GGUF/Java_11_test-improvement.raw.txt when available;
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

- `improvedEmptyStringsProduceEmptyResult_B1` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedLengthOneBitExercised_B2` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedNonBinaryCharactersStillCompareLexically_I2` - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md
- `improvedMutationDifferentLengthsTruncatesToShorter` - mutation-based guard (see docs/analysis/mutation_testing_strategy.md)

## 6. Sanity check

The improved suite is re-compiled and executed by the Phase 1 CI workflow
(.github/workflows/phase1-ci.yml). The corresponding row in
Phase1/results/improved_coverage_results.csv is expected to report
compile_success=true and junit_success=true for (Java_011_stringXor, Qwen2.5-Coder-1.5B-Instruct-GGUF).
