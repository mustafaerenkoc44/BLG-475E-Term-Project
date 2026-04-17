import argparse
import csv
import gzip
import json
import pathlib
import re
import subprocess
import sys
import tempfile
from dataclasses import dataclass


@dataclass
class ModelSpec:
    name: str
    relative_path: str


MODELS = [
    ModelSpec(
        name="Qwen2.5-Coder-1.5B-Instruct-GGUF",
        relative_path="Qwen2.5-Coder-1.5B-Instruct-GGUF/qwen2.5-coder-1.5b-instruct-q4_k_m.gguf",
    ),
    ModelSpec(
        name="DeepSeek-Coder-1.3B-Instruct-GGUF",
        relative_path="DeepSeek-Coder-1.3B-Instruct-GGUF/deepseek-coder-1.3b-instruct.Q4_K_M.gguf",
    ),
]


def read_dataset(dataset_path: pathlib.Path) -> dict[str, dict]:
    tasks: dict[str, dict] = {}
    with gzip.open(dataset_path, "rt", encoding="utf-8") as handle:
        for line in handle:
            if not line.strip():
                continue
            task = json.loads(line)
            tasks[task["task_id"]] = task
    return tasks


def read_selection(selection_path: pathlib.Path) -> list[dict]:
    with selection_path.open("r", encoding="utf-8", newline="") as handle:
        return list(csv.DictReader(handle))


def task_folder_name(task_id: str, method_name: str) -> str:
    match = re.match(r"^Java/(\d+)$", task_id)
    if not match:
        safe = re.sub(r"[^A-Za-z0-9_\-]", "_", task_id)
        return safe
    return f"Java_{int(match.group(1)):03d}_{method_name}"


def braces_closed(source: str) -> bool:
    balance = 0
    for char in source:
        if char == "{":
            balance += 1
        elif char == "}":
            balance -= 1
    return balance <= 0


def strip_prompt_echo(prompt: str, text: str) -> str:
    stripped = text.lstrip()
    if stripped.startswith(prompt):
        return stripped[len(prompt) :].lstrip()
    return text


def unwrap_fenced_code(text: str) -> str:
    fence_start = text.find("```")
    if fence_start == -1:
        return text

    rest = text[fence_start + 3 :]
    first_newline = rest.find("\n")
    if first_newline != -1:
        rest = rest[first_newline + 1 :]

    fence_end = rest.find("```")
    if fence_end == -1:
        return text

    return rest[:fence_end].strip()


def extract_balanced_class_source(text: str) -> str | None:
    class_pos = text.find("class Solution")
    if class_pos == -1:
        return None

    import_pos = text.find("import ")
    if import_pos != -1 and import_pos > class_pos:
        import_pos = -1
    start = import_pos if import_pos != -1 else class_pos

    brace_balance = 0
    started = False
    for index in range(start, len(text)):
        char = text[index]
        if char == "{":
            brace_balance += 1
            started = True
        elif char == "}":
            brace_balance -= 1
            if started and brace_balance == 0:
                return text[start : index + 1].strip()

    return None


def build_solution_source(prompt: str, raw_generation: str) -> str:
    cleaned = strip_prompt_echo(prompt, raw_generation.strip())
    cleaned = unwrap_fenced_code(cleaned).strip()

    full_source = extract_balanced_class_source(cleaned)
    if full_source:
        return full_source

    return complete_java_source(prompt, cleaned)


def complete_java_source(prompt: str, raw_generation: str) -> str:
    full = prompt + raw_generation
    balance = 0
    seen_generation = False

    for index, char in enumerate(full):
        if char == "{":
            balance += 1
        elif char == "}":
            balance -= 1

        if index >= len(prompt):
            seen_generation = True
            if seen_generation and balance <= 0:
                return full[: index + 1]

    return full


def run_generation(
    llama_completion: pathlib.Path,
    model_path: pathlib.Path,
    prompt: str,
    max_tokens: int,
    temperature: float,
) -> str:
    command = [
        str(llama_completion),
        "-m",
        str(model_path),
        "-f",
        "",  # filled after temp file creation
        "-n",
        str(max_tokens),
        "--temp",
        str(temperature),
        "--seed",
        "475",
        "-c",
        "4096",
        "--simple-io",
        "-no-cnv",
        "--no-perf",
        "--no-warmup",
    ]
    with tempfile.NamedTemporaryFile("w", delete=False, encoding="utf-8", suffix=".txt") as temp_prompt:
        temp_prompt.write(prompt)
        prompt_path = pathlib.Path(temp_prompt.name)

    command[4] = str(prompt_path)

    try:
        result = subprocess.run(
            command,
            capture_output=True,
            text=True,
            encoding="utf-8",
            errors="replace",
            check=True,
        )
    except subprocess.CalledProcessError as exc:
        raise RuntimeError(
            f"Generation failed for model '{model_path.name}'.\nSTDOUT:\n{exc.stdout}\nSTDERR:\n{exc.stderr}"
        ) from exc
    finally:
        prompt_path.unlink(missing_ok=True)

    stdout = result.stdout
    if "\nload_backend:" in stdout:
        stdout = stdout.split("\nload_backend:", 1)[0]
    stdout = stdout.replace("[end of text]", "")
    return stdout.rstrip()


def write_text(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def write_log(log_path: pathlib.Path, model_name: str, task_id: str, prompt: str, response: str) -> None:
    content = "\n".join(
        [
            "# Interaction Log",
            "",
            f"- Model: {model_name}",
            f"- Task: {task_id}",
            "- Step: code-generation",
            "",
            "## Full Prompt",
            "",
            "```text",
            prompt,
            "```",
            "",
            "## Raw Model Response",
            "",
            "```text",
            response,
            "```",
            "",
            "## Usage Note",
            "",
            "Saved as raw code-generation output.",
            "",
        ]
    )
    write_text(log_path, content)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--dataset", required=True)
    parser.add_argument("--selection", required=True)
    parser.add_argument("--tasks-root", required=True)
    parser.add_argument("--llama-completion", required=True)
    parser.add_argument("--models-root", required=True)
    parser.add_argument("--max-tokens", type=int, default=384)
    parser.add_argument("--temperature", type=float, default=0.2)
    parser.add_argument("--task-id", action="append", default=[])
    parser.add_argument("--skip-existing", action="store_true")
    args = parser.parse_args()

    dataset = read_dataset(pathlib.Path(args.dataset))
    selection = read_selection(pathlib.Path(args.selection))
    tasks_root = pathlib.Path(args.tasks_root)
    llama_completion = pathlib.Path(args.llama_completion)
    models_root = pathlib.Path(args.models_root)
    phase1_root = tasks_root.parent
    logs_root = phase1_root / "logs"
    only_task_ids = set(args.task_id)

    for row in selection:
        if only_task_ids and row["task_id"] not in only_task_ids:
            continue
        task = dataset.get(row["task_id"])
        if task is None:
            print(f"Skipping missing dataset task: {row['task_id']}", file=sys.stderr)
            continue

        folder_name = task_folder_name(row["task_id"], row["method_name"])
        task_root = tasks_root / folder_name

        for model in MODELS:
            model_path = models_root / model.relative_path
            if not model_path.exists():
                print(f"Missing model file: {model_path}", file=sys.stderr)
                continue

            code_dir = task_root / "generated-code" / model.name
            code_path = code_dir / "Solution.java"
            raw_response_path = code_dir / "raw_generation.txt"
            log_path = logs_root / model.name / f"{row['task_id'].replace('/', '_')}_code-generation.md"

            if args.skip_existing and code_path.exists():
                print(f"Skipping existing solution: {code_path}")
                continue

            prompt = task["prompt"]
            print(f"[generate] {row['task_id']} <- {model.name}")
            raw_generation = run_generation(
                llama_completion=llama_completion,
                model_path=model_path,
                prompt=prompt,
                max_tokens=args.max_tokens,
                temperature=args.temperature,
            )
            full_source = build_solution_source(prompt, raw_generation)

            write_text(raw_response_path, raw_generation)
            write_text(code_path, full_source)
            write_log(log_path, model.name, row["task_id"], prompt, raw_generation)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
