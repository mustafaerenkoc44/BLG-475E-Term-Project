# Phase 2 Execution Plan

> Archived planning note. The plan in this file was executed; for the completed
> artefacts and measured outcomes, see:
> `docs/analysis/phase2_execution_report.md`,
> `docs/analysis/prompt_strategy_comparison.md`, and
> `docs/report/phase2_report_draft.md`.

## Goal

Build and evaluate a `BookScan` class under realistic integration-testing scenarios.

## Scope

The Phase 2 implementation should combine:

- substring counting logic
- word-length calculation logic
- upper-lower case normalization logic

## Experiment Axes

### 1. Prompt Strategy Comparison

- direct combination of the original prompts without edits
- manually improved prompt combination with clarified API and behavior

### 2. Integration Test Adequacy

- baseline integration tests
- improved tests after branch coverage review
- manual black-box review for missing scenarios

## Minimum Scenario Set

- empty text
- single-line input
- multi-line input
- repeated words across different lines
- mixed-case words
- punctuation-heavy text
- words shorter than the requested target length
- words exactly at the target length boundary

## Suggested Result Metrics

- compile success
- integration-test pass rate
- branch coverage
- number of repaired prompt/code iterations
- comparison between original-combined and edited-combined prompts

## Deliverable Checklist

- `BookScan` implementation
- integration test suite
- prompt-comparison notes
- coverage results
- analysis tables and report figures
