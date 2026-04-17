import argparse
import csv
import pathlib
import shutil
import subprocess


MODEL_DIR_NAMES = [
    "Qwen2.5-Coder-1.5B-Instruct-GGUF",
    "DeepSeek-Coder-1.3B-Instruct-GGUF",
]

ARGS = None


def write_text(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def compile_java(javac_path: pathlib.Path, junit_console: pathlib.Path, work_dir: pathlib.Path) -> subprocess.CompletedProcess:
    return subprocess.run(
        [
            str(javac_path),
            "-encoding",
            "UTF-8",
            "-cp",
            str(junit_console),
            "-d",
            "classes",
            "Solution.java",
            ARGS.test_file_name,
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=ARGS.compile_timeout,
    )


def run_junit(
    java_path: pathlib.Path,
    junit_console: pathlib.Path,
    jacoco_agent: pathlib.Path,
    work_dir: pathlib.Path,
) -> subprocess.CompletedProcess:
    jacoco_exec = work_dir / "jacoco.exec"
    agent_arg = f"-javaagent:{jacoco_agent}=destfile={jacoco_exec}"
    return subprocess.run(
        [
            str(java_path),
            agent_arg,
            "-jar",
            str(junit_console),
            "execute",
            "--class-path",
            "classes",
            "--select-class",
            ARGS.test_class_name,
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=ARGS.run_timeout,
    )


def generate_report(
    java_path: pathlib.Path,
    jacoco_cli: pathlib.Path,
    work_dir: pathlib.Path,
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
            ".",
            "--csv",
            "jacoco.csv",
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=ARGS.run_timeout,
    )


def read_branch_coverage(csv_path: pathlib.Path) -> tuple[int, int]:
    if not csv_path.exists():
        return 0, 0

    with csv_path.open("r", encoding="utf-8", newline="") as handle:
        rows = list(csv.DictReader(handle))

    if not rows:
        return 0, 0

    covered = 0
    missed = 0
    for row in rows:
        if row["CLASS"].startswith("Solution"):
            covered += int(row["BRANCH_COVERED"])
            missed += int(row["BRANCH_MISSED"])
    return covered, missed


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tasks-root", required=True)
    parser.add_argument("--results", required=True)
    parser.add_argument("--java", required=True)
    parser.add_argument("--javac", required=True)
    parser.add_argument("--junit-console", required=True)
    parser.add_argument("--jacoco-agent", required=True)
    parser.add_argument("--jacoco-cli", required=True)
    parser.add_argument("--compile-timeout", type=int, default=20)
    parser.add_argument("--run-timeout", type=int, default=20)
    parser.add_argument("--test-stage", default="base")
    parser.add_argument("--test-file-name", default="DatasetBaseTest.java")
    parser.add_argument("--test-class-name", default="DatasetBaseTest")
    parser.add_argument("--artifacts-subdir", default="base-coverage")
    args = parser.parse_args()
    global ARGS
    ARGS = args

    tasks_root = pathlib.Path(args.tasks_root)
    results_path = pathlib.Path(args.results)
    java_path = pathlib.Path(args.java)
    javac_path = pathlib.Path(args.javac)
    junit_console = pathlib.Path(args.junit_console)
    jacoco_agent = pathlib.Path(args.jacoco_agent)
    jacoco_cli = pathlib.Path(args.jacoco_cli)
    runs_root = results_path.parent / "artifacts" / args.artifacts_subdir
    runs_root.mkdir(parents=True, exist_ok=True)

    result_rows: list[list[str]] = []

    for task_dir in sorted([item for item in tasks_root.iterdir() if item.is_dir()]):
        for model_name in MODEL_DIR_NAMES:
            solution_path = task_dir / "generated-code" / model_name / "Solution.java"
            test_path = task_dir / "generated-tests" / args.test_stage / model_name / args.test_file_name

            if not solution_path.exists() or not test_path.exists():
                result_rows.append([task_dir.name, model_name, "false", "false", "0", "0", "missing inputs"])
                continue

            run_dir = runs_root / task_dir.name / model_name
            if run_dir.exists():
                shutil.rmtree(run_dir)
            run_dir.mkdir(parents=True)
            (run_dir / "classes").mkdir()

            write_text(run_dir / "Solution.java", solution_path.read_text(encoding="utf-8"))
            write_text(run_dir / args.test_file_name, test_path.read_text(encoding="utf-8"))

            try:
                compile_proc = compile_java(javac_path, junit_console, run_dir)
            except subprocess.TimeoutExpired:
                result_rows.append([task_dir.name, model_name, "false", "false", "0", "0", "compile timeout"])
                continue

            write_text(run_dir / "compile.stdout.txt", compile_proc.stdout)
            write_text(run_dir / "compile.stderr.txt", compile_proc.stderr)
            if compile_proc.returncode != 0:
                result_rows.append([task_dir.name, model_name, "false", "false", "0", "0", "compile failure"])
                continue

            try:
                run_proc = run_junit(java_path, junit_console, jacoco_agent, run_dir)
            except subprocess.TimeoutExpired:
                result_rows.append([task_dir.name, model_name, "true", "false", "0", "0", "test timeout"])
                continue

            write_text(run_dir / "junit.stdout.txt", run_proc.stdout)
            write_text(run_dir / "junit.stderr.txt", run_proc.stderr)
            if run_proc.returncode != 0:
                result_rows.append([task_dir.name, model_name, "true", "false", "0", "0", "junit failure"])
                continue

            try:
                report_proc = generate_report(java_path, jacoco_cli, run_dir)
            except subprocess.TimeoutExpired:
                result_rows.append([task_dir.name, model_name, "true", "true", "0", "0", "report timeout"])
                continue

            write_text(run_dir / "jacoco_report.stdout.txt", report_proc.stdout)
            write_text(run_dir / "jacoco_report.stderr.txt", report_proc.stderr)
            if report_proc.returncode != 0:
                result_rows.append([task_dir.name, model_name, "true", "true", "0", "0", "report failure"])
                continue

            branch_covered, branch_missed = read_branch_coverage(run_dir / "jacoco.csv")
            result_rows.append(
                [
                    task_dir.name,
                    model_name,
                    "true",
                    "true",
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
                "task_folder",
                "model_name",
                "compile_success",
                "junit_success",
                "branch_covered",
                "branch_missed",
                "notes",
            ]
        )
        writer.writerows(result_rows)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
