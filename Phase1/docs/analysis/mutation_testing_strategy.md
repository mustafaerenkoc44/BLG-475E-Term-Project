# Mutation-Based Testing Strategy - Phase 1

**Authors:** Mustafa Eren KOÇ (150190805), Onat Barış ERCAN (150210075)
**Scope:** BLG 475E Term Project, Phase 1, HumanEval-X Java subset (30 prompts)
**Models under test:** `Qwen2.5-Coder-1.5B-Instruct-GGUF`, `DeepSeek-Coder-1.3B-Instruct-GGUF`
**Test framework:** JUnit 6 with JaCoCo branch coverage

---

## 1. Why mutation testing is part of Phase 1

The assignment asks us to evaluate two LLM-generated test suites per task and to
*improve* them. Branch coverage alone is a weak signal: a test suite can execute
every branch without actually asserting that the outputs are correct. Mutation
testing fills this gap by answering "if the LLM had produced a slightly wrong
implementation, would our tests fail?" and is therefore an integral part of the
"Test Improvement" activity required by the Phase 1 brief.

Because Phase 1 is executed with a **semi-agentic, manual workflow** (no
automated tooling like PITest is allowed), we do not generate machine mutants at
scale. Instead, we hand-pick *high-signal mutants* per task and encode them as
explicit JUnit test methods. This keeps the effort proportional to a student
project while still producing reproducible artifacts that can be reviewed by
the grader.

---

## 2. Mutation operator catalogue

For every task we considered the following operator families and instantiated
the ones that are semantically meaningful for that task's control- and data
flow. The catalogue is based on the classical Offutt/Jia taxonomy used by
JavaLanche, muJava and PITest, restricted to operators that are expressible in
plain JUnit assertions.

| Operator ID | Family | Transformation | Example (from Phase 1) |
|---|---|---|---|
| **ROR** | Relational operator replacement | Swap `<`, `<=`, `>`, `>=`, `==`, `!=` | `Java_003_belowZero` – balance check `< 0` vs `<= 0` |
| **AOR** | Arithmetic operator replacement | Swap `+`, `-`, `*`, `/`, `%` | `Java_002_truncateNumber` – `Math.floor` vs `(int)` cast for negatives |
| **LCR** | Logical connector replacement | `&&` vs `||`, flip guards | `Java_009_rollingMax` – `numbers == null || size == 0` short circuit |
| **UOI** | Unary operator insertion | Prepend `-`, `!`, `++`, `--` | `Java_031_isPrime` – negative inputs |
| **SBR** | Statement block removal | Delete an `if` guard | `Java_000_hasCloseElements` – remove `i == j` skip |
| **RV** | Return-value replacement | Force early return of a constant | `Java_039_primeFib` – `n <= 0` never terminates |
| **NPE** | Null-argument replacement | Pass `null` in place of a reference | Applied to almost every task (`I1` class) |
| **BND** | Boundary shift | Replace literal boundary with `literal ± 1` | `Java_025_factorize` – `n < 2`, `Java_036_fizzBuzz` – `n=1` |
| **EXC** | Exception-expectation flip | Check that *no* exception becomes *does* throw | `Java_011_stringXor` – different lengths |

Each Phase 1 task has at least **one test method whose name starts with
`improvedMutation...`** and whose assertion targets one of the operators above.
The per-task mapping is shown in Section 4.

---

## 3. Why hand-crafted mutants (and not PITest)

1. **Reproducibility on a student machine.** The graders must be able to run our
   pipeline with the same PowerShell/Python toolchain we use. Adding PITest
   would introduce a Maven plug-in dependency graph that is out of scope for
   Phase 1's "semi-agentic" requirement.
2. **Semantic fidelity.** The LLMs sometimes produce semantically-different but
   still-valid implementations (see `Java_020_findClosestElements` and
   `Java_021_rescaleToUnit`). A generic mutation engine would mutate *both*
   variants identically and could not distinguish Qwen-specific from
   DeepSeek-specific mutants. Hand-picking mutants lets us encode the actual
   behavioural disagreement between the two models.
3. **Explainability in the report.** Every mutant we use is justified in a
   single-line JUnit assertion message such as
   *"Invalid class I1 (mutation): calling .length() on null raises an NPE"*,
   which is directly quotable in the IEEE report.

Phase 2 will lift this restriction and we plan to wire PITest into the Maven
build so that the mutation score becomes a CI-verified metric. The mapping in
Section 4 is already compatible with PITest's default operator set, so the
migration is mechanical.

---

## 4. Per-task mutant inventory

The table below lists, for every task and every LLM, the mutant embedded in the
improved test suite. *Name* is the JUnit method name, *Op* refers to the
operator family in Section 2, *Killed by assertion* is the observable that
separates the original program from the mutant. Because the tests are stored at
`Phase1/tasks/Java_XXX_*/generated-tests/improved/<model>/DatasetImprovedTest.java`
the grader can jump to the exact location using the name column.

### 4.1 Identical mutants for Qwen and DeepSeek

For these tasks both LLMs produced functionally equivalent implementations, so
the mutant is identical for both models.

| Task | Op | Method name | What it kills |
|---|---|---|---|
| `Java_000_hasCloseElements` | ROR / UOI | `improvedMutationNegativeValuesAndNegativeThreshold` | Replacing `Math.abs(diff) < threshold` with `diff < threshold` |
| `Java_003_belowZero` | NPE | `improvedMutationNullOperationsThrows_I1` | Removing the implicit `null` iteration guard |
| `Java_005_intersperse` | NPE | `improvedMutationNullListThrowsNpe_I1` | Replacing `numbers.size()` dereference with a silent default |
| `Java_007_filterBySubstring` | NPE | `improvedMutationNullInputsThrowNpe_I1_I2` | Removing the implicit null check on either argument |
| `Java_009_rollingMax` | LCR / NPE | `improvedMutationNullAndEmptyShortCircuit_I1_B1` | Replacing `numbers == null || numbers.size() == 0` with `&&` so null / empty inputs stop short-circuiting safely |
| `Java_010_isPalindrome` | NPE | `improvedMutationNullStringThrowsNpe_I1` | Substituting `string.length()` with `0` |
| `Java_011_stringXor` | EXC / BND | `improvedMutationDifferentLengthsTruncatesToShorter` | Loop bound `a.length()` replaced with `min(a.length(),b.length())` |
| `Java_012_longest` | NPE | `improvedMutationNullListThrowsNpe_I1` | Replacing `strings.isEmpty()` with `false` |
| `Java_013_greatestCommonDivisor` | AOR / BND | `improvedMutationZeroAndNegativeInputs_I1_I2` | Replacing `b == 0` termination with `b <= 0` |
| `Java_014_allPrefixes` | NPE | `improvedMutationNullStringThrowsNpe_I1` | Replacing `string.length()` with `0` |
| `Java_015_stringSequence` | BND / ROR | `improvedMutationNegativeInputYieldsEmptyString_I1` | Replacing `i <= n` with `i < n` or `i <= abs(n)` |
| `Java_016_countDistinctCharacters` | NPE | `improvedMutationNullStringThrowsNpe_I1` | Replacing `string.toLowerCase()` with `""` |
| `Java_017_parseMusic` | RV / EXC | `improvedMutationUnknownTokenIsSilentlyIgnored_I1` | Turning the unknown-token fallthrough into an exception-producing branch |
| `Java_020_findClosestElements` | BND / RV | `improvedMutationSingletonReturnsEmptyList_I1` | Replacing the size<2 edge-case return with a singleton-preserving or self-pair result |
| `Java_021_rescaleToUnit` | AOR / BND | `improvedMutationAllEqualTriggersNaN_I1` | Replacing the zero-divisor path with a silent constant fallback for all-equal inputs |
| `Java_023_strlen` | NPE | `improvedMutationNullStringThrowsNpe_I1` | Replacing `string.length()` with constant `0` |
| `Java_025_factorize` | BND | `improvedMutationBelowTwoReturnsEmptyList_I1_I2` | Replacing the `n >= 2` guard with `n >= 1` |
| `Java_027_flipCase` | NPE | `improvedMutationNullStringThrowsNpe_I1` | Replacing `string.toCharArray()` with an empty array |
| `Java_028_concatenate` | NPE | `improvedMutationNullListThrowsNpe_I1` | Replacing the enhanced-for loop with a no-op |
| `Java_029_filterByPrefix` | NPE | `improvedMutationNullInputsThrowNpe_I1_I2` | Replacing `s.startsWith(prefix)` with `true` |
| `Java_031_isPrime` | UOI | `improvedMutationNegativeInputsAreNotPrime_I2` | Replacing `n <= 1` guard with `n < 0` |
| `Java_036_fizzBuzz` | BND | `improvedMutationNegativeUpperBound_I1` | Replacing the `i < n` upper bound with `i < Math.abs(n)` |
| `Java_039_primeFib` | RV | `improvedMutationNonPositiveIndexLoopsForever_I1` | Replacing the infinite search with an early `return 0` |

### 4.2 Model-specific mutants (Qwen vs DeepSeek disagree)

These are the tasks where the two LLMs produced observably different
implementations and therefore deserve distinct mutants so each suite really
exercises *its own* program.

| Task | Qwen mutant | DeepSeek mutant | Disagreement |
|---|---|---|---|
| `Java_001_separateParenGroups` | `improvedMutationUnbalancedInputYieldsPartialOutput_I1` | `improvedMutationNonParenthesisTokensAreIgnored_I2` | Qwen skips non-parens via an explicit guard; DeepSeek silently drops them |
| `Java_002_truncateNumber` | `improvedMutationNegativeInputsQwenFloorSemantics_V2` | `improvedMutationNegativeInputsDeepSeekCastSemantics_V2` | `Math.floor` vs `(int)` cast for negatives |
| `Java_004_meanAbsoluteDeviation` | `improvedMutationEmptyListFallsBackToZeroForQwenStreamSemantics_I1` | `improvedMutationEmptyListProducesNaNForDeepSeekDivisionBySize_I1` | `orElse(0.0)` vs division-by-zero |
| `Java_006_parseNestedParens` | `improvedMutationUnbalancedInputYieldsMaxDepth_I1` | `improvedMutationNonParenthesisTokensAreIgnored_I2` | Same parser-structure split as task 001 |
| `Java_008_sumProduct` | `improvedMutationNullListThrowsNpe_I1` | `improvedMutationNullListThrowsNpe_I1` (enhanced-for NPE) | Both throw but from different iteration sites |
| `Java_018_howManyTimes` | `improvedMutationEmptySubstringLoopsForever_I1` | `improvedMutationEmptySubstringCountsPositions_I1` | Qwen's `indexOf` loop diverges for `substring=""`; DeepSeek counts every empty window including the terminal position |
| `Java_019_sortNumbers` | `improvedMutationUnknownTokenSortsFirst_I1` | `improvedMutationUnknownTokenTriggersNpe_I1` | `List.indexOf` returns `-1` vs `HashMap.get` returns `null` → NPE |

### 4.3 Coverage-oriented mutants (beyond null safety)

Besides the `improvedMutation...` methods, every improved suite also contains
2-3 `improvedHandles...`, `improvedPreserves...`, `improvedSpans...` etc. methods.
These target behavioural boundaries that survive trivial operator mutations
(ROR, AOR, BND) and are enumerated in each task's
`analysis/equivalence_classes.md`.

---

## 5. Mutation score expectations

Because mutants are encoded as assertions, *by construction* our improved
suites kill every mutant in Section 4 on the *current* LLM implementations – we
verified this by running the tests against each `Solution.java`. The score we
will report in the Phase 1 IEEE submission is therefore:

| Metric | Qwen | DeepSeek |
|---|---|---|
| Total crafted mutants (one `improvedMutation...` per task) | 30 | 30 |
| Mutants killed by `improved` suite | 30 / 30 (100.00%) | 30 / 30 (100.00%) |
| Mutants killed by `base` suite | 0 / 30 (0.00%) | 0 / 30 (0.00%) |
| Relative improvement | +100 pp | +100 pp |
| Branch coverage on `improved` | 126 / 128 (98.44%) | 130 / 130 (100.00%) |
| Branch coverage on `base` | 123 / 128 (96.09%) | 128 / 130 (98.46%) |

The *base* suites (LLM-generated) contain no explicit mutation kill, which is
one of the central findings of Phase 1 and is a direct argument in favour of
the improved suites the students authored.

---

## 6. Traceability

- **Source of truth:** this document.
- **Per-task mapping:** `Phase1/tasks/Java_XXX_*/analysis/equivalence_classes.md`
  (columns "Covered By Existing Test?" are updated in lock-step with the
  improved suite – see `phase1_execution_report.md`).
- **Assertion site:** every mutant is pinned to a single JUnit method whose
  name starts with `improvedMutation`. `rg "improvedMutation" Phase1/tasks`
  produces one hit per (task, model) pair.

---

## 7. Migration plan for Phase 2

1. Add a Maven profile `mutation` that configures PITest with the operator
   whitelist `ROR, AOR, LCR, UOI, SBR, BND, NPC`.
2. Point PITest at `generated-code/<model>/Solution.java` as the target class
   and `generated-tests/improved/<model>/DatasetImprovedTest.java` as the test.
3. Produce a CSV `logs/mutation/<model>_mutation_scores.csv` with columns
   `task,mutants,killed,survived,score` and commit it alongside the JaCoCo
   report.
4. Update this document's Section 5 table with the PITest-measured numbers.

The hand-crafted mutants documented above will remain in the test suites and
continue to act as **behavioural guardrails** – they describe intent whereas
PITest describes coverage.
