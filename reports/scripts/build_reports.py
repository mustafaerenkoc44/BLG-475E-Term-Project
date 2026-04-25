from __future__ import annotations

import html
import re
import shutil
import sys
from dataclasses import dataclass
from pathlib import Path

import matplotlib.pyplot as plt
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_JUSTIFY, TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, StyleSheet1, getSampleStyleSheet
from reportlab.lib.units import cm
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import (
    Image,
    KeepTogether,
    LongTable,
    PageBreak,
    Paragraph,
    SimpleDocTemplate,
    Spacer,
    Table,
    TableStyle,
)


REPO_ROOT = Path(__file__).resolve().parents[2]
REPORTS_ROOT = REPO_ROOT / "reports"
PAGE_WIDTH, PAGE_HEIGHT = A4
BODY_WIDTH = PAGE_WIDTH - (2.0 * cm) - (2.0 * cm)

REPO_URL = "https://github.com/mustafaerenkoc44/BLG-475E-Term-Project"
COURSE_NAME = "BLG 475E - Software Quality and Testing"
TERM_NAME = "2025-2026 Spring Term"
TEAM_MEMBERS = [
    ("Mustafa Eren KOC", "150190805", "Lead: engineering and automation track"),
    ("Onat Baris ERCAN", "150210075", "Lead: quality and analysis track"),
]


@dataclass(frozen=True)
class ReportSpec:
    phase_label: str
    title: str
    subtitle: str
    due_label: str
    output_dir: Path
    output_basename: str
    markdown_source: Path
    latex_source: Path
    bib_source: Path
    key_points: list[str]
    summary_rows: list[tuple[str, str]]
    chart_title: str
    chart_builder: str
    combine_with_phase1: bool = False


def register_fonts() -> tuple[str, str, str, str]:
    candidates = {
        "ReportSans": Path(r"C:\Windows\Fonts\arial.ttf"),
        "ReportSansBold": Path(r"C:\Windows\Fonts\arialbd.ttf"),
        "ReportSerif": Path(r"C:\Windows\Fonts\times.ttf"),
        "ReportSerifBold": Path(r"C:\Windows\Fonts\timesbd.ttf"),
    }
    registered = {}
    for font_name, font_path in candidates.items():
        if font_path.exists():
            pdfmetrics.registerFont(TTFont(font_name, str(font_path)))
            registered[font_name] = font_name

    sans = registered.get("ReportSans", "Helvetica")
    sans_bold = registered.get("ReportSansBold", "Helvetica-Bold")
    serif = registered.get("ReportSerif", "Times-Roman")
    serif_bold = registered.get("ReportSerifBold", "Times-Bold")
    return sans, sans_bold, serif, serif_bold


def build_styles() -> StyleSheet1:
    sans, sans_bold, serif, serif_bold = register_fonts()
    styles = getSampleStyleSheet()
    styles.add(
        ParagraphStyle(
            name="CoverTitle",
            parent=styles["Title"],
            fontName=sans_bold,
            fontSize=26,
            leading=31,
            textColor=colors.HexColor("#0b3d91"),
            alignment=TA_CENTER,
            spaceAfter=14,
        )
    )
    styles.add(
        ParagraphStyle(
            name="CoverSubtitle",
            parent=styles["BodyText"],
            fontName=sans,
            fontSize=12,
            leading=16,
            textColor=colors.HexColor("#274c77"),
            alignment=TA_CENTER,
            spaceAfter=18,
        )
    )
    styles.add(
        ParagraphStyle(
            name="SectionHeading",
            parent=styles["Heading1"],
            fontName=sans_bold,
            fontSize=16,
            leading=20,
            textColor=colors.HexColor("#12355b"),
            spaceBefore=12,
            spaceAfter=8,
        )
    )
    styles.add(
        ParagraphStyle(
            name="SubHeading",
            parent=styles["Heading2"],
            fontName=sans_bold,
            fontSize=12,
            leading=15,
            textColor=colors.HexColor("#1d3557"),
            spaceBefore=10,
            spaceAfter=6,
        )
    )
    styles.add(
        ParagraphStyle(
            name="MinorHeading",
            parent=styles["Heading3"],
            fontName=sans_bold,
            fontSize=10.5,
            leading=13,
            textColor=colors.HexColor("#2f4858"),
            spaceBefore=8,
            spaceAfter=4,
        )
    )
    styles.add(
        ParagraphStyle(
            name="ReportBody",
            parent=styles["BodyText"],
            fontName=serif,
            fontSize=10.2,
            leading=14,
            alignment=TA_JUSTIFY,
            textColor=colors.HexColor("#111111"),
            spaceAfter=6,
        )
    )
    styles.add(
        ParagraphStyle(
            name="ReportBodyBold",
            parent=styles["BodyText"],
            fontName=serif_bold,
            fontSize=10.2,
            leading=14,
            alignment=TA_JUSTIFY,
            textColor=colors.HexColor("#111111"),
            spaceAfter=6,
        )
    )
    styles.add(
        ParagraphStyle(
            name="BulletBody",
            parent=styles["BodyText"],
            fontName=serif,
            fontSize=10.2,
            leading=13.5,
            alignment=TA_LEFT,
            textColor=colors.HexColor("#111111"),
            leftIndent=14,
            firstLineIndent=0,
            spaceAfter=4,
        )
    )
    styles.add(
        ParagraphStyle(
            name="QuoteBody",
            parent=styles["Italic"],
            fontName=serif,
            fontSize=9.5,
            leading=13,
            textColor=colors.HexColor("#3d405b"),
            leftIndent=16,
            rightIndent=16,
            spaceAfter=8,
        )
    )
    styles.add(
        ParagraphStyle(
            name="TableCell",
            parent=styles["BodyText"],
            fontName=serif,
            fontSize=8.6,
            leading=11,
            alignment=TA_LEFT,
            textColor=colors.HexColor("#111111"),
        )
    )
    styles.add(
        ParagraphStyle(
            name="TableHead",
            parent=styles["BodyText"],
            fontName=sans_bold,
            fontSize=8.6,
            leading=10.5,
            alignment=TA_CENTER,
            textColor=colors.white,
        )
    )
    styles.add(
        ParagraphStyle(
            name="MetaLabel",
            parent=styles["BodyText"],
            fontName=sans_bold,
            fontSize=9.2,
            leading=11,
            textColor=colors.HexColor("#12355b"),
        )
    )
    styles.add(
        ParagraphStyle(
            name="MetaValue",
            parent=styles["BodyText"],
            fontName=serif,
            fontSize=9.2,
            leading=11.5,
            textColor=colors.HexColor("#111111"),
        )
    )
    return styles


def format_inline(text: str) -> str:
    text = html.escape(text)
    text = re.sub(r"`([^`]+)`", r"<font face='Courier'>\1</font>", text)
    text = re.sub(r"\*\*([^*]+)\*\*", r"<b>\1</b>", text)
    text = re.sub(r"\*([^*]+)\*", r"<i>\1</i>", text)
    text = text.replace("\u2248", "approx.")
    text = text.replace("–", "-")
    text = text.replace("—", "-")
    return text


def trim_markdown_source(text: str) -> str:
    marker = "## Abstract"
    index = text.find(marker)
    if index == -1:
        return text
    return text[index:]


def parse_markdown(markdown_text: str, styles: StyleSheet1, chart_path: Path | None = None) -> list:
    flowables = []
    lines = trim_markdown_source(markdown_text).splitlines()
    index = 0
    paragraph_buffer: list[str] = []

    def flush_paragraph() -> None:
        nonlocal paragraph_buffer
        if not paragraph_buffer:
            return
        joined = " ".join(item.strip() for item in paragraph_buffer).strip()
        if joined:
            flowables.append(Paragraph(format_inline(joined), styles["ReportBody"]))
        paragraph_buffer = []

    while index < len(lines):
        raw_line = lines[index].rstrip()
        stripped = raw_line.strip()

        if not stripped:
            flush_paragraph()
            index += 1
            continue

        if stripped.startswith("|"):
            flush_paragraph()
            table_lines = []
            while index < len(lines) and lines[index].strip().startswith("|"):
                table_lines.append(lines[index].strip())
                index += 1
            flowables.append(build_markdown_table(table_lines, styles))
            flowables.append(Spacer(1, 0.18 * cm))
            continue

        heading_match = re.match(r"^(#{1,6})\s+(.*)$", stripped)
        if heading_match:
            flush_paragraph()
            level = len(heading_match.group(1))
            content = heading_match.group(2)
            if level == 2:
                style_name = "SectionHeading"
            elif level == 3:
                style_name = "SubHeading"
            else:
                style_name = "MinorHeading"
            flowables.append(Paragraph(format_inline(content), styles[style_name]))
            if "Abstract" in content and chart_path and chart_path.exists():
                flowables.append(Image(str(chart_path), width=16.2 * cm, height=6.4 * cm))
                flowables.append(Spacer(1, 0.15 * cm))
            index += 1
            continue

        if stripped.startswith(">"):
            flush_paragraph()
            quote_lines = []
            while index < len(lines) and lines[index].strip().startswith(">"):
                quote_lines.append(lines[index].strip()[1:].strip())
                index += 1
            flowables.append(Paragraph(format_inline(" ".join(quote_lines)), styles["QuoteBody"]))
            continue

        bullet_match = re.match(r"^(-|\*)\s+(.*)$", stripped)
        if bullet_match:
            flush_paragraph()
            bullet_lines = []
            while index < len(lines):
                line = lines[index].rstrip()
                current = line.strip()
                if not current:
                    break
                bullet = re.match(r"^(-|\*)\s+(.*)$", current)
                if bullet:
                    bullet_lines.append(("bullet", bullet.group(2)))
                    index += 1
                    continue
                numbered = re.match(r"^\d+\.\s+(.*)$", current)
                if numbered:
                    break
                if bullet_lines:
                    kind, text = bullet_lines[-1]
                    bullet_lines[-1] = (kind, text + " " + current)
                    index += 1
                    continue
                break
            for _, item in bullet_lines:
                flowables.append(Paragraph(format_inline(item), styles["BulletBody"], bulletText="\u2022"))
            continue

        numbered_match = re.match(r"^(\d+)\.\s+(.*)$", stripped)
        if numbered_match:
            flush_paragraph()
            ordered_lines = []
            while index < len(lines):
                line = lines[index].rstrip()
                current = line.strip()
                if not current:
                    break
                numbered = re.match(r"^(\d+)\.\s+(.*)$", current)
                if numbered:
                    ordered_lines.append((numbered.group(1), numbered.group(2)))
                    index += 1
                    continue
                if ordered_lines:
                    number, text = ordered_lines[-1]
                    ordered_lines[-1] = (number, text + " " + current)
                    index += 1
                    continue
                break
            for number, item in ordered_lines:
                flowables.append(Paragraph(format_inline(item), styles["BulletBody"], bulletText=f"{number}."))
            continue

        paragraph_buffer.append(stripped)
        index += 1

    flush_paragraph()
    return flowables


def build_markdown_table(table_lines: list[str], styles: StyleSheet1) -> LongTable:
    rows = []
    for line in table_lines:
        if re.fullmatch(r"\|?\s*:?-+:?\s*(\|\s*:?-+:?\s*)+\|?", line):
            continue
        cells = [cell.strip() for cell in line.strip("|").split("|")]
        rows.append(cells)

    if not rows:
        return LongTable([[""]])

    column_count = max(len(row) for row in rows)
    normalized = [row + [""] * (column_count - len(row)) for row in rows]
    char_widths = []
    for column in range(column_count):
        longest = max(len(re.sub(r"`|\*|\\", "", row[column])) for row in normalized)
        char_widths.append(max(longest, 8))
    total_chars = float(sum(char_widths))
    col_widths = [(BODY_WIDTH * width / total_chars) for width in char_widths]

    data = []
    for row_index, row in enumerate(normalized):
        style_name = "TableHead" if row_index == 0 else "TableCell"
        data.append([Paragraph(format_inline(cell), styles[style_name]) for cell in row])

    table = LongTable(data, colWidths=col_widths, repeatRows=1)
    table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#12355b")),
                ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
                ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.whitesmoke, colors.HexColor("#eef3f8")]),
                ("GRID", (0, 0), (-1, -1), 0.35, colors.HexColor("#a3b8cc")),
                ("BOX", (0, 0), (-1, -1), 0.6, colors.HexColor("#7a90a8")),
                ("VALIGN", (0, 0), (-1, -1), "TOP"),
                ("LEFTPADDING", (0, 0), (-1, -1), 6),
                ("RIGHTPADDING", (0, 0), (-1, -1), 6),
                ("TOPPADDING", (0, 0), (-1, -1), 5),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
            ]
        )
    )
    return table


def build_cover(spec: ReportSpec, styles: StyleSheet1) -> list:
    story = []
    story.append(Spacer(1, 1.2 * cm))
    story.append(Paragraph(spec.phase_label, styles["CoverSubtitle"]))
    story.append(Paragraph(spec.title, styles["CoverTitle"]))
    story.append(Paragraph(spec.subtitle, styles["CoverSubtitle"]))
    story.append(Spacer(1, 0.25 * cm))

    metadata_table = Table(
        [
            [Paragraph("Course", styles["MetaLabel"]), Paragraph(COURSE_NAME, styles["MetaValue"])],
            [Paragraph("Term", styles["MetaLabel"]), Paragraph(TERM_NAME, styles["MetaValue"])],
            [Paragraph("Submission", styles["MetaLabel"]), Paragraph(spec.due_label, styles["MetaValue"])],
            [
                Paragraph("Repository", styles["MetaLabel"]),
                Paragraph(f"<link href='{REPO_URL}' color='blue'>{REPO_URL}</link>", styles["MetaValue"]),
            ],
        ],
        colWidths=[3.2 * cm, 11.5 * cm],
    )
    metadata_table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, -1), colors.HexColor("#f5f9fc")),
                ("BOX", (0, 0), (-1, -1), 0.75, colors.HexColor("#c7d7e6")),
                ("INNERGRID", (0, 0), (-1, -1), 0.35, colors.HexColor("#d7e1ea")),
                ("VALIGN", (0, 0), (-1, -1), "TOP"),
                ("LEFTPADDING", (0, 0), (-1, -1), 8),
                ("RIGHTPADDING", (0, 0), (-1, -1), 8),
                ("TOPPADDING", (0, 0), (-1, -1), 6),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
            ]
        )
    )
    story.append(metadata_table)
    story.append(Spacer(1, 0.45 * cm))

    authors_data = [[Paragraph("Student", styles["MetaLabel"]), Paragraph("ID", styles["MetaLabel"]), Paragraph("Contribution Focus", styles["MetaLabel"])]]
    for name, student_id, workload in TEAM_MEMBERS:
        authors_data.append(
            [
                Paragraph(name, styles["MetaValue"]),
                Paragraph(student_id, styles["MetaValue"]),
                Paragraph(workload, styles["MetaValue"]),
            ]
        )
    authors_table = Table(authors_data, colWidths=[8.1 * cm, 3.0 * cm, 3.6 * cm])
    authors_table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#0b3d91")),
                ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
                ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, colors.HexColor("#f4f8fb")]),
                ("BOX", (0, 0), (-1, -1), 0.75, colors.HexColor("#c7d7e6")),
                ("INNERGRID", (0, 0), (-1, -1), 0.35, colors.HexColor("#d7e1ea")),
                ("VALIGN", (0, 0), (-1, -1), "TOP"),
                ("LEFTPADDING", (0, 0), (-1, -1), 8),
                ("RIGHTPADDING", (0, 0), (-1, -1), 8),
                ("TOPPADDING", (0, 0), (-1, -1), 6),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
            ]
        )
    )
    story.append(authors_table)
    story.append(Spacer(1, 0.45 * cm))

    summary_title = Paragraph("Executive Snapshot", styles["SubHeading"])
    summary_data = [[Paragraph("Metric", styles["MetaLabel"]), Paragraph("Value", styles["MetaLabel"])]]
    for key, value in spec.summary_rows:
        summary_data.append([Paragraph(key, styles["MetaValue"]), Paragraph(value, styles["MetaValue"])])
    summary_table = Table(summary_data, colWidths=[7.2 * cm, 7.5 * cm])
    summary_table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#1d3557")),
                ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
                ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.whitesmoke, colors.HexColor("#eef3f8")]),
                ("BOX", (0, 0), (-1, -1), 0.75, colors.HexColor("#c7d7e6")),
                ("INNERGRID", (0, 0), (-1, -1), 0.35, colors.HexColor("#d7e1ea")),
                ("VALIGN", (0, 0), (-1, -1), "TOP"),
                ("LEFTPADDING", (0, 0), (-1, -1), 8),
                ("RIGHTPADDING", (0, 0), (-1, -1), 8),
                ("TOPPADDING", (0, 0), (-1, -1), 6),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
            ]
        )
    )
    story.append(summary_title)
    story.append(summary_table)
    story.append(Spacer(1, 0.35 * cm))

    story.append(Paragraph("Key Highlights", styles["SubHeading"]))
    for point in spec.key_points:
        story.append(Paragraph(format_inline(point), styles["BulletBody"], bulletText="\u2022"))

    story.append(Spacer(1, 0.2 * cm))
    story.append(
        Paragraph(
            "Prepared as a submission-ready academic report package with synchronized PDF and LaTeX sources.",
            styles["QuoteBody"],
        )
    )
    story.append(PageBreak())
    return story


def add_page_chrome(canvas, doc, phase_label: str) -> None:
    canvas.saveState()
    canvas.setFillColor(colors.HexColor("#12355b"))
    canvas.rect(1.6 * cm, PAGE_HEIGHT - 1.75 * cm, PAGE_WIDTH - 3.2 * cm, 0.42 * cm, stroke=0, fill=1)
    canvas.setFillColor(colors.white)
    canvas.setFont("Helvetica-Bold", 9)
    canvas.drawString(1.95 * cm, PAGE_HEIGHT - 1.60 * cm, f"{COURSE_NAME} | {phase_label}")

    canvas.setStrokeColor(colors.HexColor("#c7d7e6"))
    canvas.line(1.6 * cm, 1.45 * cm, PAGE_WIDTH - 1.6 * cm, 1.45 * cm)
    canvas.setFillColor(colors.HexColor("#44546a"))
    canvas.setFont("Helvetica", 8.5)
    canvas.drawString(1.7 * cm, 1.0 * cm, REPO_URL)
    canvas.drawRightString(PAGE_WIDTH - 1.7 * cm, 1.0 * cm, f"Page {doc.page}")
    canvas.restoreState()


def make_phase1_chart(output_path: Path) -> None:
    labels = ["Qwen Base", "Qwen Improved", "DeepSeek Base", "DeepSeek Improved"]
    coverage = [96.09, 98.44, 98.46, 100.00]
    mutation = [0.0, 100.0, 0.0, 100.0]

    fig, axes = plt.subplots(1, 2, figsize=(11.5, 4.2))
    fig.patch.set_facecolor("#fbfdff")

    axes[0].bar(labels, coverage, color=["#7aa6c2", "#0b3d91", "#8ecae6", "#1d3557"])
    axes[0].set_ylim(0, 105)
    axes[0].set_title("Branch Coverage", fontsize=11, fontweight="bold")
    axes[0].set_ylabel("Percent")
    axes[0].grid(axis="y", linestyle="--", alpha=0.3)
    axes[0].tick_params(axis="x", rotation=20)

    axes[1].bar(labels, mutation, color=["#cfd8dc", "#2a9d8f", "#cfd8dc", "#2a9d8f"])
    axes[1].set_ylim(0, 105)
    axes[1].set_title("Mutation Guardrail Score", fontsize=11, fontweight="bold")
    axes[1].set_ylabel("Percent")
    axes[1].grid(axis="y", linestyle="--", alpha=0.3)
    axes[1].tick_params(axis="x", rotation=20)

    fig.tight_layout()
    fig.savefig(output_path, dpi=220, bbox_inches="tight")
    plt.close(fig)


def make_phase2_chart(output_path: Path) -> None:
    fig, axes = plt.subplots(1, 2, figsize=(11.5, 4.2))
    fig.patch.set_facecolor("#fbfdff")

    scenario_labels = ["Orig Qwen", "Orig DeepSeek", "Edited Qwen", "Edited DeepSeek", "Final"]
    pass_rates = [33.33, 44.44, 94.44, 94.44, 100.0]
    axes[0].bar(scenario_labels, pass_rates, color=["#b0c4de", "#8ecae6", "#457b9d", "#1d3557", "#0b3d91"])
    axes[0].set_ylim(0, 105)
    axes[0].set_title("Prompt Comparison Pass Rates", fontsize=11, fontweight="bold")
    axes[0].set_ylabel("Percent")
    axes[0].grid(axis="y", linestyle="--", alpha=0.3)
    axes[0].tick_params(axis="x", rotation=18)

    metrics = ["Branch", "Line", "Mutation"]
    values = [100.0, 100.0, 84.95]
    axes[1].bar(metrics, values, color=["#0b3d91", "#457b9d", "#2a9d8f"])
    axes[1].set_ylim(0, 105)
    axes[1].set_title("Selected Final Quality Metrics", fontsize=11, fontweight="bold")
    axes[1].set_ylabel("Percent")
    axes[1].grid(axis="y", linestyle="--", alpha=0.3)

    fig.tight_layout()
    fig.savefig(output_path, dpi=220, bbox_inches="tight")
    plt.close(fig)


def phase_core_markdown(path: Path) -> str:
    text = path.read_text(encoding="utf-8")
    trimmed = trim_markdown_source(text)
    acknowledgement_marker = "## Acknowledgement"
    ack_index = trimmed.find(acknowledgement_marker)
    if ack_index != -1:
        trimmed = trimmed[:ack_index].rstrip()
    return trimmed.strip()


def demote_markdown_headings(text: str, prefix: str) -> str:
    updated_lines = []
    for line in text.splitlines():
        if line.startswith("## "):
            updated_lines.append(f"### {prefix} - {line[3:].strip()}")
        elif line.startswith("### "):
            updated_lines.append(f"#### {line[4:].strip()}")
        else:
            updated_lines.append(line)
    return "\n".join(updated_lines)


def phase2_combined_markdown(spec: ReportSpec) -> str:
    phase1_core = demote_markdown_headings(
        phase_core_markdown(REPO_ROOT / "Phase1" / "docs" / "report" / "phase1_report_draft.md"),
        "Phase 1",
    )
    phase2_core = demote_markdown_headings(phase_core_markdown(spec.markdown_source), "Phase 2")

    combined = f"""
## Abstract

This final submission package combines the warm-up and extension phases of the
BLG 475E term project in a single polished report for the Phase 2 deadline.
Phase 1 evaluates two local public code-oriented LLMs on 30 HumanEval-X Java
prompts under a semi-agentic workflow with dataset tests, improved JUnit 6
suites, JaCoCo branch coverage, equivalence partitioning, and mutation-based
guardrails. Phase 2 lifts the helper semantics of tasks `Java/18`, `Java/23`,
and `Java/27` into a composite `BookScan` class and compares original versus
edited prompt combinations under a shared integration suite. Across the full
project, the consistent result is that prompt clarity, explicit testing
contracts, and coverage-aware validation matter at least as much as raw model
selection. The final `BookScan` implementation reaches `18/18` test success,
`72/72 = 100.00%` branch coverage, `110/110 = 100.00%` line coverage, and
`79/93 = 84.95%` mutation score.

## I. Final Report Scope

This document is the final combined submission for the Phase 2 deadline. It
preserves the full Phase 1 story, then extends it with the integration-testing
results required by the second part of the assignment. The report therefore
serves two purposes:

- it keeps the Phase 1 experimental decisions, metrics, and mutation guardrails
  visible as the foundation of the project;
- it shows how those same guardrails and testing instincts scale to a more
  realistic integrated class in Phase 2.

## II. Phase 1 Warm-Up Results

{phase1_core}

## III. Phase 2 Integration Extension

{phase2_core}

## IV. Cross-Phase Conclusion

Taken together, the two phases show a stable pattern. The raw models were
useful starting points, but the strongest results only emerged once the project
added:

- explicit prompt constraints,
- black-box equivalence classes,
- white-box coverage feedback,
- mutation-oriented regression guards,
- and documented repair loops.

Phase 1 established this pattern on isolated benchmark tasks; Phase 2 confirmed
that the same pattern becomes even more important once the problem moves from a
single function to a multi-method class with shared normalization and result
contracts.

## Acknowledgement

- GitHub repository URL:
  `https://github.com/mustafaerenkoc44/BLG-475E-Term-Project`
- The two-person group split the workload along two complementary tracks
  and cross-reviewed each other's deliverables; the same split applied to
  both phases.
- Group member roles and workload distribution:
  - **Mustafa Eren KOC (150190805) - engineering and automation track**:
    repository scaffolding, GitHub Actions CI workflows, PowerShell + Python
    driver layer, local llama.cpp pipeline, dataset-to-JUnit adapters,
    JaCoCo automation, Maven and PITest configuration, BookScan reference
    implementation, prompt-comparison harness, Git commit discipline.
  - **Onat Baris ERCAN (150210075) - quality and analysis track**:
    30-prompt selection rationale and difficulty categorisation, per-task
    equivalence-partitioning and boundary-value documents, test-smell
    audit, hand-crafted mutation-operator catalogue and improvedMutation...
    JUnit method authoring, BookScan black-box assessment, original-versus
    -edited prompt-strategy design, failure-mode classification, five-paper
    literature review, and IEEE journal manuscript drafting.
""".strip()
    return combined + "\n"


def report_markdown_text(spec: ReportSpec) -> str:
    if spec.combine_with_phase1:
        return phase2_combined_markdown(spec)
    return spec.markdown_source.read_text(encoding="utf-8")


def copy_report_sources(spec: ReportSpec) -> dict[str, Path]:
    spec.output_dir.mkdir(parents=True, exist_ok=True)
    output_tex = spec.output_dir / f"{spec.output_basename}.tex"
    output_bib = spec.output_dir / f"{spec.output_basename}.bib"
    output_md = spec.output_dir / f"{spec.output_basename}.md"
    output_pdf = spec.output_dir / f"{spec.output_basename}.pdf"
    shutil.copy2(spec.latex_source, output_tex)
    shutil.copy2(spec.bib_source, output_bib)
    # The source .tex references the bibliography by the source basename
    # (e.g. \bibliography{phase1_report}). After we rename the .bib file to
    # match the polished output basename, rewrite the same reference inside
    # the copied .tex so a downstream `pdflatex + bibtex` build still resolves.
    source_bib_stem = spec.bib_source.stem
    output_bib_stem = output_bib.stem
    if source_bib_stem != output_bib_stem:
        original_tex = output_tex.read_text(encoding="utf-8")
        rewritten_tex = original_tex.replace(
            f"\\bibliography{{{source_bib_stem}}}",
            f"\\bibliography{{{output_bib_stem}}}",
        )
        output_tex.write_text(rewritten_tex, encoding="utf-8")
    output_md.write_text(report_markdown_text(spec), encoding="utf-8")
    return {
        "tex": output_tex,
        "bib": output_bib,
        "md": output_md,
        "pdf": output_pdf,
    }


def _find_tectonic() -> Path | None:
    """Locate the bundled portable tectonic LaTeX engine, if present."""
    candidate = REPO_ROOT / ".tools" / "tectonic" / "tectonic.exe"
    if candidate.is_file():
        return candidate
    candidate = REPO_ROOT / ".tools" / "tectonic" / "tectonic"
    if candidate.is_file():
        return candidate
    return None


def _build_pdf_with_tectonic(tectonic: Path, spec: ReportSpec, copied_paths: dict[str, Path]) -> None:
    """Compile the polished .tex via tectonic, producing the canonical IEEE journal PDF."""
    import subprocess

    result = subprocess.run(
        [
            str(tectonic),
            "--keep-logs",
            "--outdir",
            str(spec.output_dir),
            str(copied_paths["tex"]),
        ],
        capture_output=True,
        text=True,
    )
    if result.returncode != 0:
        sys.stderr.write(result.stdout)
        sys.stderr.write(result.stderr)
        raise RuntimeError(
            f"tectonic failed to compile {copied_paths['tex']}; see log above."
        )


def _build_pdf_with_reportlab(spec: ReportSpec, styles: StyleSheet1, copied_paths: dict[str, Path]) -> None:
    """Markdown-driven preview renderer kept as a fallback when tectonic is unavailable."""
    chart_path = spec.output_dir / f"{spec.output_basename}_chart.png"
    if spec.chart_builder == "phase1":
        make_phase1_chart(chart_path)
    else:
        make_phase2_chart(chart_path)

    markdown_text = report_markdown_text(spec)

    doc = SimpleDocTemplate(
        str(copied_paths["pdf"]),
        pagesize=A4,
        leftMargin=2.0 * cm,
        rightMargin=2.0 * cm,
        topMargin=2.3 * cm,
        bottomMargin=2.1 * cm,
        title=spec.title,
        author=", ".join(name for name, _, _ in TEAM_MEMBERS),
        subject=f"{spec.phase_label} report for {COURSE_NAME}",
    )

    story = []
    story.extend(build_cover(spec, styles))
    story.extend(parse_markdown(markdown_text, styles, chart_path))

    doc.build(
        story,
        onFirstPage=lambda canvas, doc: add_page_chrome(canvas, doc, spec.phase_label),
        onLaterPages=lambda canvas, doc: add_page_chrome(canvas, doc, spec.phase_label),
    )


def build_pdf(spec: ReportSpec, styles: StyleSheet1, copied_paths: dict[str, Path]) -> None:
    """Produce the polished PDF for one report.

    The canonical deliverable is the IEEEtran journal LaTeX manuscript at
    ``copied_paths['tex']``. When the portable tectonic engine is available
    under ``.tools/tectonic/`` the script invokes it to produce a real
    IEEE journal PDF that matches the assignment template. As a fallback
    (e.g. on a CI runner without LaTeX) we keep the legacy ReportLab
    markdown-preview path so the script still produces *some* PDF.
    """
    tectonic = _find_tectonic()
    if tectonic is not None:
        _build_pdf_with_tectonic(tectonic, spec, copied_paths)
    else:
        _build_pdf_with_reportlab(spec, styles, copied_paths)


def build_specs() -> list[ReportSpec]:
    return [
        ReportSpec(
            phase_label="Project Phase 1",
            title="LLM-Based Java Code and Test Generation on HumanEval-X",
            subtitle="Semi-Agentic Generation, Improved Tests, and Mutation-Based Guardrails",
            due_label="Phase 1 submission - due 27 April 2026",
            output_dir=REPORTS_ROOT / "Phase1",
            output_basename="BLG475E_Phase1_Report",
            markdown_source=REPO_ROOT / "Phase1" / "docs" / "report" / "phase1_report_draft.md",
            latex_source=REPO_ROOT / "Phase1" / "docs" / "report" / "ieee" / "phase1_report.tex",
            bib_source=REPO_ROOT / "Phase1" / "docs" / "report" / "ieee" / "phase1_report.bib",
            key_points=[
                "30 selected HumanEval-X Java prompts balanced across easy, moderate, and hard tasks.",
                "Both local coder models reached 30/30 base-test success after the logged repair loop.",
                "Improved suites raised aggregate branch coverage to 98.44% (Qwen) and 100.00% (DeepSeek).",
                "Mutation guardrail inventory reached 60/60 kills across the improved suites.",
            ],
            summary_rows=[
                ("Selected prompts", "30 tasks, 10 easy / 10 moderate / 10 hard"),
                ("Models", "Qwen2.5-Coder-1.5B-Instruct-GGUF and DeepSeek-Coder-1.3B-Instruct-GGUF"),
                ("Base correctness", "30/30 tasks passed for both models"),
                ("Improved mutation score", "60/60 = 100.00%"),
            ],
            chart_title="Phase 1 Coverage and Mutation Overview",
            chart_builder="phase1",
        ),
        ReportSpec(
            phase_label="Project Phase 2",
            title="Integration Testing for the Composite BookScan Class",
            subtitle="Final Combined Report with Phase 1 Recap and Phase 2 Extension Results",
            due_label="Phase 2 final submission - due 25 May 2026",
            output_dir=REPORTS_ROOT / "Phase2",
            output_basename="BLG475E_Phase2_Report",
            markdown_source=REPO_ROOT / "Phase2" / "docs" / "report" / "phase2_report_draft.md",
            latex_source=REPO_ROOT / "Phase2" / "docs" / "report" / "ieee" / "phase2_report.tex",
            bib_source=REPO_ROOT / "Phase2" / "docs" / "report" / "ieee" / "phase2_report.bib",
            key_points=[
                "Selected final BookScan implementation clears the full strengthened 18-test suite.",
                "JaCoCo closes at 72/72 branches and 110/110 lines for the final implementation.",
                "Prompt refinement improves raw model outputs from 6/18 and 8/18 to 17/18 and 17/18.",
                "PITest records 79/93 killed mutants, with residual survivors documented as equivalent or defensive.",
            ],
            summary_rows=[
                ("Selected final correctness", "18/18 tests passed"),
                ("Selected final coverage", "72/72 branches and 110/110 lines"),
                ("Mutation score", "79/93 = 84.95%"),
                ("Repository", REPO_URL),
            ],
            chart_title="Phase 2 Prompt Comparison and Quality Metrics",
            chart_builder="phase2",
            combine_with_phase1=True,
        ),
    ]


def main() -> None:
    REPORTS_ROOT.mkdir(parents=True, exist_ok=True)
    (REPORTS_ROOT / "scripts").mkdir(parents=True, exist_ok=True)
    styles = build_styles()

    for spec in build_specs():
        copied_paths = copy_report_sources(spec)
        build_pdf(spec, styles, copied_paths)
        print(f"Built {copied_paths['pdf']}")
        print(f"Copied {copied_paths['tex']}")
        print(f"Copied {copied_paths['bib']}")


if __name__ == "__main__":
    main()
