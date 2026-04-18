# Phase 2 - Integration Testing Extension

This folder contains the starting point for the second phase of the term project. The focus of Phase 2 is to design, implement, and integration-test a Java class named `BookScan`.

## Objective

`BookScan` should analyze a text and determine:

- how many times words of a given length appear,
- on which lines those words appear,
- how shared helper behavior affects integration across multiple responsibilities.

The class must incorporate the behaviors related to the following HumanEval-X tasks already studied in Phase 1:

- `Java/18` - substring counting
- `Java/23` - string length
- `Java/27` - upper-lower case conversion

## Expected Deliverables

- `BookScan` production implementation
- integration test suite
- prompt-combination experiments:
  - unmodified and combined prompts
  - edited and combined prompts
- comparative analysis of the two prompt strategies
- Phase 1 and Phase 2 combined report material

## Recommended Structure

- `src/main/java/`
  - `BookScan.java`
  - helper classes only if they are justified by the design
- `src/test/java/`
  - integration tests for cross-method behavior
  - any focused regression tests for bugs found during integration
- `docs/`
  - prompt-improvement notes
  - Phase 2 analysis and report fragments

## Proposed BookScan Responsibilities

The implementation can be organized around three core responsibilities:

1. normalize input text for consistent scanning and case handling
2. compute word length information and substring-based occurrences
3. aggregate per-line match information into a testable result format

## Integration Test Focus

Phase 2 should not stop at isolated unit behavior. The most important scenarios are:

- line-by-line scanning over multi-line text
- case-insensitive matching paths that reuse the `flipCase`-style behavior
- word-length filtering paths that reuse the `strlen`-style behavior
- occurrence counting paths that reuse the `howManyTimes`-style behavior
- combined prompts versus manually improved prompts for `BookScan`

## Recommended Workflow

1. define a clear `BookScan` API before prompting the models
2. generate the first implementation with both LLM strategies
3. run integration tests before polishing the code
4. analyze failures and document whether the issue is in the code, the prompt, or the test oracle
5. compare prompt strategies with correctness and coverage evidence
6. merge the final findings into the combined Phase 1 + Phase 2 report

## Current Status

- Phase 1 dependencies are already available in the repository
- Phase 2 planning is now documented
- implementation and integration tests are the next concrete step
