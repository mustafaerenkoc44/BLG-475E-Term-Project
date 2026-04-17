# Phase 1 Report Outline

Use the official IEEE journal template for the actual submission. This outline maps your content to that report.

## Title

LLM-Based Java Code and Test Generation on HumanEval-X: Phase 1 Analysis

## Abstract

- problem scope
- chosen LLMs
- dataset size and selection strategy
- major findings on correctness and coverage

## I. Introduction

- role of LLMs in code generation
- need for validation through testing
- objective of the phase

## II. Experimental Setup

- HumanEval-X Java dataset
- selected 30 prompts and why they were chosen
- difficulty categorization rubric
- chosen LLMs and why
- semi-agentic or agentic workflow decision
- Java, JUnit 6, JaCoCo, and any other tools

## III. Code Generation Procedure

- exact generation policy
- note that prompt descriptions were not modified
- how raw outputs were stored
- logging strategy for every interaction

## IV. Base Test Execution

- how dataset base tests were adapted when necessary
- count of passing and failing tasks per model
- observed incompatibilities

## V. Test Improvement and Coverage Analysis

- smell review process
- branch coverage workflow
- before and after comparison
- table per model:
  - tasks
  - test count
  - branch coverage
  - failing cases

## VI. Manual Black-Box Assessment

- equivalence classes
- invalid classes
- boundary conditions
- adequacy of base tests
- new mutated inputs derived from missing classes

## VII. Refactoring Loop

- criteria for returning to code generation
- examples of defects found
- impact of corrected generations

## VIII. Literature Review

This section should be written manually from the papers you read. Do not outsource this section to an LLM.

Recommended subsections:

- paper selection method
- comparison dimensions
- findings relevant to LLM-based test generation
- gap between literature and your observations

## IX. Discussion

- which model generated better initial code
- which model responded better to feedback
- relation between coverage and correctness
- limits of base tests

## X. Threats to Validity

- prompt selection bias
- evaluator subjectivity in difficulty labels
- model randomness and prompt sensitivity
- local environment limitations

## XI. Conclusion

- concise summary
- lessons for Phase 2

## Acknowledgment

- GitHub repository URL
- group member roles and workload distribution

