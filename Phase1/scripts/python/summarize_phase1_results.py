import argparse
import csv
import pathlib
from collections import Counter, defaultdict


def read_csv(path: pathlib.Path) -> list[dict]:
    with path.open("r", encoding="utf-8", newline="") as handle:
        return list(csv.DictReader(handle))


def write_csv(path: pathlib.Path, rows: list[dict], fieldnames: list[str]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


def write_markdown(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--base-results", required=True)
    parser.add_argument("--coverage-results", required=True)
    parser.add_argument("--summary-csv", required=True)
    parser.add_argument("--summary-markdown", required=True)
    args = parser.parse_args()

    base_rows = read_csv(pathlib.Path(args.base_results))
    coverage_rows = read_csv(pathlib.Path(args.coverage_results))
    coverage_map = {(row["task_folder"], row["model_name"]): row for row in coverage_rows}

    merged_rows: list[dict] = []
    per_model = defaultdict(lambda: {"total": 0, "compiled": 0, "passed": 0, "branch_covered": 0, "branch_missed": 0})
    failures = Counter()

    for base in base_rows:
        key = (base["task_folder"], base["model_name"])
        coverage = coverage_map.get(key, {})

        branch_covered = int(coverage.get("branch_covered", "0") or "0")
        branch_missed = int(coverage.get("branch_missed", "0") or "0")
        coverage_note = coverage.get("notes", "missing")

        merged = {
            "task_folder": base["task_folder"],
            "model_name": base["model_name"],
            "compile_success": base["compile_success"],
            "run_success": base["run_success"],
            "base_notes": base["notes"],
            "branch_covered": str(branch_covered),
            "branch_missed": str(branch_missed),
            "coverage_notes": coverage_note,
        }
        merged_rows.append(merged)

        stats = per_model[base["model_name"]]
        stats["total"] += 1
        if base["compile_success"] == "true":
            stats["compiled"] += 1
        if base["run_success"] == "true":
            stats["passed"] += 1
        stats["branch_covered"] += branch_covered
        stats["branch_missed"] += branch_missed

        if base["notes"] != "pass":
            failures[(base["model_name"], base["notes"])] += 1
        if coverage_note != "ok":
            failures[(base["model_name"], coverage_note)] += 1

    write_csv(
        pathlib.Path(args.summary_csv),
        merged_rows,
        [
            "task_folder",
            "model_name",
            "compile_success",
            "run_success",
            "base_notes",
            "branch_covered",
            "branch_missed",
            "coverage_notes",
        ],
    )

    lines = ["# Phase 1 Measured Summary", ""]
    lines.append("## Model Comparison")
    lines.append("")
    lines.append("| Model | Compiled | Base Tests Passed | Covered Branches | Missed Branches | Aggregate Branch Coverage |")
    lines.append("|---|---:|---:|---:|---:|---:|")

    for model_name, stats in per_model.items():
        total_branches = stats["branch_covered"] + stats["branch_missed"]
        coverage_pct = round(100.0 * stats["branch_covered"] / total_branches, 2) if total_branches else 0.0
        lines.append(
            f"| {model_name} | {stats['compiled']}/{stats['total']} | "
            f"{stats['passed']}/{stats['total']} | {stats['branch_covered']} | {stats['branch_missed']} | {coverage_pct}% |"
        )

    lines.append("")
    lines.append("## Failure Breakdown")
    lines.append("")
    lines.append("| Model | Outcome | Count |")
    lines.append("|---|---|---:|")

    for (model_name, outcome), count in sorted(failures.items()):
        lines.append(f"| {model_name} | {outcome} | {count} |")

    lines.append("")
    lines.append("## Notes")
    lines.append("")
    lines.append("- Branch counts come from the converted dataset base tests executed under JaCoCo.")
    lines.append("- Compile failures usually indicate malformed or incomplete model output rather than dataset failure.")
    lines.append("- Coverage is aggregated only for rows where the JUnit coverage run completed successfully.")
    lines.append("")

    write_markdown(pathlib.Path(args.summary_markdown), "\n".join(lines))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

