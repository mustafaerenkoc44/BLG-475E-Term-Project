import argparse
import csv
import pathlib
from collections import defaultdict


def read_rows(path: pathlib.Path) -> list[dict]:
    with path.open("r", encoding="utf-8", newline="") as handle:
        return list(csv.DictReader(handle))


def aggregate(rows: list[dict]) -> dict[str, tuple[int, int, int]]:
    result = defaultdict(lambda: [0, 0, 0])
    for row in rows:
        if row["notes"] != "ok":
            continue
        model = row["model_name"]
        result[model][0] += 1
        result[model][1] += int(row["branch_covered"])
        result[model][2] += int(row["branch_missed"])
    return result


def pct(covered: int, missed: int) -> float:
    total = covered + missed
    return round((100.0 * covered / total), 2) if total else 0.0


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--base-coverage", required=True)
    parser.add_argument("--improved-coverage", required=True)
    parser.add_argument("--output-markdown", required=True)
    args = parser.parse_args()

    base = aggregate(read_rows(pathlib.Path(args.base_coverage)))
    improved = aggregate(read_rows(pathlib.Path(args.improved_coverage)))

    lines = [
        "# Improved Coverage Summary",
        "",
        "| Model | Base Covered | Base Missed | Base % | Improved Covered | Improved Missed | Improved % | Delta |",
        "|---|---:|---:|---:|---:|---:|---:|---:|",
    ]

    model_names = sorted(set(base) | set(improved))
    for model in model_names:
        _, b_cov, b_miss = base.get(model, (0, 0, 0))
        _, i_cov, i_miss = improved.get(model, (0, 0, 0))
        b_pct = pct(b_cov, b_miss)
        i_pct = pct(i_cov, i_miss)
        delta = round(i_pct - b_pct, 2)
        lines.append(
            f"| {model} | {b_cov} | {b_miss} | {b_pct}% | {i_cov} | {i_miss} | {i_pct}% | {delta}% |"
        )

    pathlib.Path(args.output_markdown).write_text("\n".join(lines) + "\n", encoding="utf-8", newline="\n")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

