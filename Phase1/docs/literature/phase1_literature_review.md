# Literature Review: LLM-Based Test Generation

## Selection Method

The literature set was chosen to cover five complementary perspectives on LLM-based test generation within the last three years: an early end-to-end tool paper, an industrial deployment study, a broad empirical evaluation of open-source LLMs, a hybrid coverage-driven test generation technique, and a recent systematic literature review. This mix gives enough breadth to position the Phase 1 workflow against both academic and industrial practice.

## Paper Summaries

### 1. ChatUniTest (Xie et al., 2023)

ChatUniTest is an early reference point for LLM-based unit test generation. Its main contribution is not just asking ChatGPT for tests, but wrapping the model inside a generation-validation-repair loop. The paper argues that raw generations are often incomplete or syntactically broken, so validation and repair are necessary rather than optional. This is highly relevant to our project because our own Phase 1 workflow also needed a normalization step and a small repair loop before every selected task became stable under the dataset tests.

### 2. Automated Unit Test Improvement using Large Language Models at Meta (Alshahwan et al., 2024)

The Meta paper shifts the problem from generating tests from scratch to improving existing human-written tests. Its most important lesson is methodological: the model output is accepted only after it passes build, execution, flakiness, and coverage-gain filters. That design turns LLM usage into an assured engineering workflow instead of a pure text-generation task. Our Phase 1 design follows the same engineering instinct. We did not accept generations because they looked plausible; we accepted them only if they compiled, passed the dataset base tests, and then produced measurable branch coverage under JaCoCo.

### 3. On the Evaluation of Large Language Models in Unit Test Generation (Yang et al., 2024)

Yang et al. provide a direct empirical comparison of multiple open-source LLMs on Java unit test generation. Two ideas from that paper are especially relevant for our work. First, prompt structure matters a lot; simply switching prompt ingredients can change compilation and coverage outcomes. Second, open-source models can be competitive enough to justify local experimentation, but they still show reliability issues that require additional engineering. This aligns with our own results: both selected local coder models reached full base-test success only after normalization and, for a small subset of tasks, targeted repair.

### 4. LLM Test Generation via Iterative Hybrid Program Analysis / Panta (Gu et al., 2025; accepted to ICSE 2026)

Panta pushes the literature beyond prompt tuning by combining LLM generation with static control-flow analysis and dynamic coverage feedback. The paper is important because it targets the specific weakness that also appears in our assignment: coverage gaps on branch-heavy code. Rather than treating the LLM as a single-shot generator, Panta iteratively steers test generation toward uncovered paths. Our own improved-test step is a simpler version of the same philosophy. We used branch coverage information plus black-box reasoning to add targeted tests for missed or weakly exercised behavior.

### 5. Test case generation using large language models: a systematic literature review (Tasarsu et al., 2026)

The 2026 systematic review gives a broader view of the field by synthesizing 38 peer-reviewed studies. Its findings confirm several patterns that are visible in our project as well. Coverage remains the dominant metric in the literature. Post-processing and integration logic are common because raw model output alone is not reliable enough. At the same time, the review points out that maintainability, robustness, and workflow integration are still weaker than simple coverage reporting. This observation is useful for interpreting our own results: high branch coverage is valuable, but it is not enough by itself to claim that a test suite is complete or production-ready.

## Cross-Paper Synthesis

Three common themes appear across the selected papers.

First, LLM-based test generation works best when the model is embedded in a larger software engineering loop. ChatUniTest uses repair, Meta uses strict filtering, and Panta uses iterative static-plus-dynamic feedback. The literature consistently shows that naive one-shot generation is weaker than guided workflows.

Second, coverage is still the dominant evaluation metric. This is reflected in both the academic and industrial papers, and it is also central to our Phase 1 pipeline. However, the literature also warns that coverage alone can overstate quality when assertions are weak or when important invalid and boundary classes are not represented. That is why our Phase 1 process also includes black-box equivalence partitioning and boundary analysis rather than reporting JaCoCo numbers alone.

Third, open-source local models are usable, but prompt design and post-processing remain decisive. Yang et al. explicitly show prompt sensitivity, while our own measured results show that the local Qwen and DeepSeek runs became reliable only after normalization, structured testing, and a small number of targeted repairs.

## Relevance to This Project

The literature supports the overall structure used in Phase 1:

- raw generation followed by validation is standard and necessary;
- repair loops are justified when failures are exposed by tests;
- coverage-guided improvement is better than relying only on base tests;
- black-box reasoning is needed because coverage cannot reveal every missing oracle or missing input class;
- open-source local models are legitimate experimental subjects when their limitations are acknowledged and measured carefully.

Taken together, the selected papers justify the semi-agentic workflow used in this repository. The LLMs generate the initial code and tests, but the decisive quality gates are still compilation, unit execution, branch coverage, and manual black-box assessment.
