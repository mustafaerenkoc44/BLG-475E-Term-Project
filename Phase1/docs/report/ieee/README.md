# LaTeX report (Phase 1)

This folder contains the standalone Phase 1 LaTeX submission, prepared
against the Istanbul Technical University Computer Engineering term-project
template (`\documentclass[pdftex,12pt,a4paper]{article}`). The folder name
`ieee/` is kept for git-history continuity even though the manuscript is no
longer using the IEEEtran class.

| File | Purpose |
|---|---|
| `phase1_report.tex` | Main document (Phase 1 standalone), uses the standard `article` class with the ITU title-page block, an embedded `thebibliography`, and code listings styled with the `listings` package. |
| `phase1_report.bib` | Legacy BibTeX database from the previous IEEE-style draft. The current `phase1_report.tex` uses an inline `thebibliography` block, so this file is kept only for reference. |

## How to build

The template depends on standard CTAN packages (`graphicx`, `geometry`,
`hyperref`, `booktabs`, `listings`, `amsmath`, `amssymb`, `multirow`,
`fancyvrb`, `mathtools`, `pdflscape`, `float`, `breakcites`, `indentfirst`).
Any recent TeX Live or MiKTeX distribution can build it. Because the
bibliography is inline (`\begin{thebibliography}`), no separate `bibtex`
step is needed.

```bash
pdflatex phase1_report.tex
pdflatex phase1_report.tex
```

Two passes are sufficient because the second pass resolves the table of
contents and cross-references generated during the first pass.

If you prefer Overleaf, upload `phase1_report.tex` as a new project (the
`.bib` is not referenced by the current `.tex`).

## What is NOT in this folder

- The Markdown report draft (`../phase1_report_draft.md`) is kept for
  in-repo reading and diff review. The LaTeX file is the camera-ready
  version of that draft.
- No PDF is checked in here because the build is deterministic and the
  polished PDF copies (built from the same draft via the ReportLab
  pipeline) live under `reports/Phase1/`.

## Phase 2 final submission report

The Phase 2 report at `Phase2/docs/report/ieee/phase2_report.tex` is the
final combined submission, in the same article-class style. It includes a
condensed Phase 1 summary plus the full Phase 2 BookScan analysis.
