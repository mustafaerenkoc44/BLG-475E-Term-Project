import argparse
import csv
import pathlib
import re
import shutil
import subprocess


VARIANTS = [
    (
        "original-combined",
        "Qwen2.5-Coder-1.5B-Instruct-GGUF",
        pathlib.Path("generated-code/original-combined/Qwen2.5-Coder-1.5B-Instruct-GGUF/BookScan.java"),
    ),
    (
        "original-combined",
        "DeepSeek-Coder-1.3B-Instruct-GGUF",
        pathlib.Path("generated-code/original-combined/DeepSeek-Coder-1.3B-Instruct-GGUF/BookScan.java"),
    ),
    (
        "edited-combined",
        "Qwen2.5-Coder-1.5B-Instruct-GGUF",
        pathlib.Path("generated-code/edited-combined/Qwen2.5-Coder-1.5B-Instruct-GGUF/BookScan.java"),
    ),
    (
        "edited-combined",
        "DeepSeek-Coder-1.3B-Instruct-GGUF",
        pathlib.Path("generated-code/edited-combined/DeepSeek-Coder-1.3B-Instruct-GGUF/BookScan.java"),
    ),
    (
        "selected-final",
        "Manual-Selected",
        pathlib.Path("src/main/java/BookScan.java"),
    ),
]


def write_text(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def copy_sources(work_dir: pathlib.Path, candidate_source: pathlib.Path, test_sources: list[pathlib.Path]) -> list[str]:
    source_dir = work_dir / "sources"
    source_dir.mkdir(parents=True, exist_ok=True)

    copied = []
    bookscan_target = source_dir / "BookScan.java"
    write_text(bookscan_target, candidate_source.read_text(encoding="utf-8"))
    copied.append("BookScan.java")

    for test_source in test_sources:
        target = source_dir / test_source.name
        write_text(target, test_source.read_text(encoding="utf-8"))
        copied.append(test_source.name)
    return copied


def compile_java(
    javac_path: pathlib.Path,
    junit_console: pathlib.Path,
    work_dir: pathlib.Path,
    source_files: list[str],
    timeout_seconds: int,
) -> subprocess.CompletedProcess:
    return subprocess.run(
        [
            str(javac_path),
            "-encoding",
            "UTF-8",
            "-cp",
            str(junit_console),
            "-d",
            "classes",
            *source_files,
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=timeout_seconds,
    )


def run_junit(
    java_path: pathlib.Path,
    junit_console: pathlib.Path,
    jacoco_agent: pathlib.Path,
    work_dir: pathlib.Path,
    timeout_seconds: int,
) -> subprocess.CompletedProcess:
    agent_arg = f"-javaagent:{jacoco_agent}=destfile=jacoco.exec"
    return subprocess.run(
        [
            str(java_path),
            agent_arg,
            "-jar",
            str(junit_console),
            "execute",
            "--disable-banner",
            "--class-path",
            str(work_dir / "classes"),
            "--select-class",
            "BookScanIntegrationTest",
            "--select-class",
            "BookScanRegressionTest",
            "--details",
            "summary",
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=timeout_seconds,
    )


def generate_report(
    java_path: pathlib.Path,
    jacoco_cli: pathlib.Path,
    work_dir: pathlib.Path,
    timeout_seconds: int,
) -> subprocess.CompletedProcess:
    return subprocess.run(
        [
            str(java_path),
            "-jar",
            str(jacoco_cli),
            "report",
            "jacoco.exec",
            "--classfiles",
            "classes",
            "--sourcefiles",
            "sources",
            "--csv",
            "jacoco.csv",
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=timeout_seconds,
    )


def parse_junit_summary(output: str) -> tuple[int, int]:
    tests_found = 0
    tests_failed = 0

    found_match = re.search(r"\[\s*(\d+)\s+tests found", output)
    failed_match = re.search(r"\[\s*(\d+)\s+tests failed", output)

    if found_match:
        tests_found = int(found_match.group(1))
    if failed_match:
        tests_failed = int(failed_match.group(1))
    return tests_found, tests_failed


def read_branch_coverage(csv_path: pathlib.Path) -> tuple[int, int]:
    if not csv_path.exists():
        return 0, 0

    with csv_path.open("r", encoding="utf-8", newline="") as handle:
        rows = list(csv.DictReader(handle))

    covered = 0
    missed = 0
    for row in rows:
        if row["CLASS"] == "BookScan":
            covered += int(row["BRANCH_COVERED"])
            missed += int(row["BRANCH_MISSED"])
    return covered, missed


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--phase2-root", required=True)
    parser.add_argument("--results", required=True)
    parser.add_argument("--java", required=True)
    parser.add_argument("--javac", required=True)
    parser.add_argument("--junit-console", required=True)
    parser.add_argument("--jacoco-agent", required=True)
    parser.add_argument("--jacoco-cli", required=True)
    parser.add_argument("--compile-timeout", type=int, default=30)
    parser.add_argument("--run-timeout", type=int, default=30)
    args = parser.parse_args()

    phase2_root = pathlib.Path(args.phase2_root)
    results_path = pathlib.Path(args.results)
    java_path = pathlib.Path(args.java)
    javac_path = pathlib.Path(args.javac)
    junit_console = pathlib.Path(args.junit_console)
    jacoco_agent = pathlib.Path(args.jacoco_agent)
    jacoco_cli = pathlib.Path(args.jacoco_cli)

    test_sources = sorted((phase2_root / "src" / "test" / "java").glob("*.java"))
    runs_root = results_path.parent / "artifacts" / "prompt-comparison"
    runs_root.mkdir(parents=True, exist_ok=True)

    rows: list[list[str]] = []

    for strategy, model_name, source_path in VARIANTS:
        candidate_source = phase2_root / source_path
        run_dir = runs_root / strategy / model_name

        if run_dir.exists():
            shutil.rmtree(run_dir)
        run_dir.mkdir(parents=True)
        (run_dir / "classes").mkdir(parents=True)

        if not candidate_source.exists():
            rows.append([strategy, model_name, "false", "false", "0", "0", "0", "0", "missing candidate"])
            continue

        source_files = [str(pathlib.Path("sources") / file_name) for file_name in copy_sources(run_dir, candidate_source, test_sources)]

        try:
            compile_proc = compile_java(
                javac_path,
                junit_console,
                run_dir,
                source_files,
                args.compile_timeout,
            )
        except subprocess.TimeoutExpired:
            rows.append([strategy, model_name, "false", "false", "0", "0", "0", "0", "compile timeout"])
            continue

        write_text(run_dir / "compile.stdout.txt", compile_proc.stdout)
        write_text(run_dir / "compile.stderr.txt", compile_proc.stderr)
        if compile_proc.returncode != 0:
            rows.append([strategy, model_name, "false", "false", "0", "0", "0", "0", "compile failure"])
            continue

        try:
            junit_proc = run_junit(
                java_path,
                junit_console,
                jacoco_agent,
                run_dir,
                args.run_timeout,
            )
        except subprocess.TimeoutExpired:
            rows.append([strategy, model_name, "true", "false", "0", "0", "0", "0", "test timeout"])
            continue

        write_text(run_dir / "junit.stdout.txt", junit_proc.stdout)
        write_text(run_dir / "junit.stderr.txt", junit_proc.stderr)

        tests_found, tests_failed = parse_junit_summary(junit_proc.stdout)
        if junit_proc.returncode != 0:
            rows.append(
                [
                    strategy,
                    model_name,
                    "true",
                    "false",
                    str(tests_found),
                    str(tests_failed),
                    "0",
                    "0",
                    "junit failure",
                ]
            )
            continue

        try:
            report_proc = generate_report(
                java_path,
                jacoco_cli,
                run_dir,
                args.run_timeout,
            )
        except subprocess.TimeoutExpired:
            rows.append(
                [
                    strategy,
                    model_name,
                    "true",
                    "true",
                    str(tests_found),
                    str(tests_failed),
                    "0",
                    "0",
                    "report timeout",
                ]
            )
            continue

        write_text(run_dir / "jacoco_report.stdout.txt", report_proc.stdout)
        write_text(run_dir / "jacoco_report.stderr.txt", report_proc.stderr)
        if report_proc.returncode != 0:
            rows.append(
                [
                    strategy,
                    model_name,
                    "true",
                    "true",
                    str(tests_found),
                    str(tests_failed),
                    "0",
                    "0",
                    "report failure",
                ]
            )
            continue

        branch_covered, branch_missed = read_branch_coverage(run_dir / "jacoco.csv")
        rows.append(
            [
                strategy,
                model_name,
                "true",
                "true",
                str(tests_found),
                str(tests_failed),
                str(branch_covered),
                str(branch_missed),
                "ok",
            ]
        )

    results_path.parent.mkdir(parents=True, exist_ok=True)
    with results_path.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.writer(handle)
        writer.writerow(
            [
                "strategy",
                "model_name",
                "compile_success",
                "junit_success",
                "tests_found",
                "tests_failed",
                "branch_covered",
                "branch_missed",
                "notes",
            ]
        )
        writer.writerows(rows)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
