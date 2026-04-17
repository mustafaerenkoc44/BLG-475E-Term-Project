import argparse
import csv
import pathlib
import re
import shutil
import subprocess
from dataclasses import dataclass


MODEL_DIR_NAMES = [
    "Qwen2.5-Coder-1.5B-Instruct-GGUF",
    "DeepSeek-Coder-1.3B-Instruct-GGUF",
]


@dataclass
class RunResult:
    task_folder: str
    model_name: str
    compile_success: bool
    run_success: bool
    output_dir: str
    notes: str


def write_text(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def sanitize_base_test(source: str) -> str:
    source = source.replace("Array.asList", "Arrays.asList")
    source = re.sub(r"Optional\.empty(?!\()", "Optional.empty()", source)

    if not source.lstrip().startswith("import "):
        source = "import java.util.*;\nimport java.lang.*;\n\n" + source

    return source


def compile_java(javac_path: pathlib.Path, work_dir: pathlib.Path) -> subprocess.CompletedProcess:
    return subprocess.run(
        [
            str(javac_path),
            "-encoding",
            "UTF-8",
            "-d",
            "classes",
            "Solution.java",
            "Main.java",
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=ARGS.compile_timeout,
    )


def run_java(java_path: pathlib.Path, work_dir: pathlib.Path) -> subprocess.CompletedProcess:
    return subprocess.run(
        [
            str(java_path),
            "-cp",
            "classes",
            "Main",
        ],
        cwd=work_dir,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=ARGS.run_timeout,
    )


ARGS = None


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tasks-root", required=True)
    parser.add_argument("--results", required=True)
    parser.add_argument("--java", required=True)
    parser.add_argument("--javac", required=True)
    parser.add_argument("--compile-timeout", type=int, default=30)
    parser.add_argument("--run-timeout", type=int, default=20)
    args = parser.parse_args()
    global ARGS
    ARGS = args

    tasks_root = pathlib.Path(args.tasks_root)
    results_path = pathlib.Path(args.results)
    java_path = pathlib.Path(args.java)
    javac_path = pathlib.Path(args.javac)
    runs_root = results_path.parent / "artifacts" / "base-tests"
    runs_root.mkdir(parents=True, exist_ok=True)

    results: list[RunResult] = []

    for task_dir in sorted([item for item in tasks_root.iterdir() if item.is_dir() and item.name != "__pycache__"]):
        base_test_path = task_dir / "dataset" / "base_test_dataset.java"
        if not base_test_path.exists():
            continue

        base_test = sanitize_base_test(base_test_path.read_text(encoding="utf-8"))

        for model_name in MODEL_DIR_NAMES:
            solution_path = task_dir / "generated-code" / model_name / "Solution.java"
            if not solution_path.exists():
                results.append(
                    RunResult(
                        task_folder=task_dir.name,
                        model_name=model_name,
                        compile_success=False,
                        run_success=False,
                        output_dir="",
                        notes="missing solution file",
                    )
                )
                continue

            run_dir = runs_root / task_dir.name / model_name
            if run_dir.exists():
                shutil.rmtree(run_dir)
            run_dir.mkdir(parents=True)
            (run_dir / "classes").mkdir()

            solution_source = solution_path.read_text(encoding="utf-8")
            write_text(run_dir / "Solution.java", solution_source)
            write_text(run_dir / "Main.java", base_test)

            try:
                compile_proc = compile_java(javac_path, run_dir)
            except subprocess.TimeoutExpired:
                write_text(run_dir / "compile.stderr.txt", "compile timeout")
                results.append(
                    RunResult(
                        task_folder=task_dir.name,
                        model_name=model_name,
                        compile_success=False,
                        run_success=False,
                        output_dir=str(run_dir),
                        notes="compile timeout",
                    )
                )
                continue
            write_text(run_dir / "compile.stdout.txt", compile_proc.stdout)
            write_text(run_dir / "compile.stderr.txt", compile_proc.stderr)

            if compile_proc.returncode != 0:
                results.append(
                    RunResult(
                        task_folder=task_dir.name,
                        model_name=model_name,
                        compile_success=False,
                        run_success=False,
                        output_dir=str(run_dir),
                        notes="compile failure",
                    )
                )
                continue

            try:
                run_proc = run_java(java_path, run_dir)
            except subprocess.TimeoutExpired:
                write_text(run_dir / "run.stderr.txt", "run timeout")
                results.append(
                    RunResult(
                        task_folder=task_dir.name,
                        model_name=model_name,
                        compile_success=True,
                        run_success=False,
                        output_dir=str(run_dir),
                        notes="run timeout",
                    )
                )
                continue
            write_text(run_dir / "run.stdout.txt", run_proc.stdout)
            write_text(run_dir / "run.stderr.txt", run_proc.stderr)

            results.append(
                RunResult(
                    task_folder=task_dir.name,
                    model_name=model_name,
                    compile_success=True,
                    run_success=(run_proc.returncode == 0),
                    output_dir=str(run_dir),
                    notes="pass" if run_proc.returncode == 0 else "runtime failure",
                )
            )

    results_path.parent.mkdir(parents=True, exist_ok=True)
    with results_path.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.writer(handle)
        writer.writerow(
            [
                "task_folder",
                "model_name",
                "compile_success",
                "run_success",
                "output_dir",
                "notes",
            ]
        )
        for item in results:
            writer.writerow(
                [
                    item.task_folder,
                    item.model_name,
                    str(item.compile_success).lower(),
                    str(item.run_success).lower(),
                    item.output_dir,
                    item.notes,
                ]
            )

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
