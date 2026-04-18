# IEEE LaTeX report (Phase 1)

This folder contains the IEEE conference-style submission for Phase 1.

| File | Purpose |
|---|---|
| `phase1_report.tex` | Main document, written against the standard `IEEEtran.cls` class file |
| `phase1_report.bib` | BibTeX database for the five literature-review references |

## How to build

The template only depends on the standard IEEE class + `booktabs`,
`hyperref`, `xcolor`, `graphicx` and `amsmath`. Any recent TeX Live or
MiKTeX distribution can build it:

```bash
pdflatex phase1_report.tex
bibtex   phase1_report
pdflatex phase1_report.tex
pdflatex phase1_report.tex
```

The produced `phase1_report.pdf` already exceeds the 6-page minimum
requested by the course brief; no filler text is added - the extra
space is used for the mutation-operator catalogue and the behavioural
divergence table.

## What is NOT in this folder

- The Markdown report draft (`../phase1_report_draft.md`) is kept for
  in-repo reading and diff review. The LaTeX file is a slightly
  reorganised, camera-ready version of that draft plus explicit
  mutation-testing coverage.
- No PDF is checked in because the build is deterministic and only a
  few hundred KB; the CI workflow can add a PDF artefact later if the
  instructor asks for one.
