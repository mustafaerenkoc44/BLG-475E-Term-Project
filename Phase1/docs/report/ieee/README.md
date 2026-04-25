# IEEE journal report (Phase 1)

This folder contains the Phase 1 LaTeX submission, written against the
**official IEEE journal template** referenced in the course brief
(`\documentclass[journal]{IEEEtran}`). The template is the one demonstrated
in `bare_jrnl.tex` and on the Overleaf "IEEE Journal Paper Template"
project linked from the assignment.

| File | Purpose |
|---|---|
| `phase1_report.tex` | Standalone Phase 1 manuscript, two-column IEEE journal layout, with abstract, keywords, code listings, tables, inline `thebibliography`, and appendices. |
| `phase1_report.bib` | Legacy BibTeX database from the previous draft. The current `phase1_report.tex` uses an inline `thebibliography` block, so this file is kept only for reference. |

## How to build

The template depends on standard CTAN packages that are bundled with
both TeX Live and MiKTeX (`amsmath`, `amssymb`, `graphicx`, `booktabs`,
`multirow`, `array`, `url`, `listings`, `xcolor`, `hyperref`). Because
the bibliography is inline (`\begin{thebibliography}`), no separate
`bibtex` step is needed.

```bash
pdflatex phase1_report.tex
pdflatex phase1_report.tex
```

Two passes are sufficient because the second pass resolves the
cross-references generated during the first pass. The repository also
ships a portable [tectonic](https://tectonic-typesetting.github.io/)
binary under `.tools/tectonic/`; from the repository root,
`python reports/scripts/build_reports.py` will detect it automatically
and recompile both Phase 1 and Phase 2 PDFs.

If you prefer Overleaf, upload `phase1_report.tex` as a new project (the
`.bib` file is not referenced by the current `.tex`).

## What is NOT in this folder

- The Markdown report draft (`../phase1_report_draft.md`) is kept for
  in-repo reading and diff review. The LaTeX file is the camera-ready
  version of that draft and is the canonical submission deliverable.
- No PDF is checked in here because the build is deterministic; the
  polished PDF copy lives under `reports/Phase1/BLG475E_Phase1_Report.pdf`.

## Phase 2 final submission report

The Phase 2 report at `Phase2/docs/report/ieee/phase2_report.tex` is the
final combined submission, written in the same IEEE journal style. It
includes a condensed Phase 1 summary plus the full Phase 2 BookScan
analysis and meets the eight-page minimum mandated by the assignment.
