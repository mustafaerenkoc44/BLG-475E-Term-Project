import argparse
import pathlib
import re


def read_text(path: pathlib.Path) -> str:
    return path.read_text(encoding="utf-8")


def write_text(path: pathlib.Path, content: str) -> None:
    path.write_text(content, encoding="utf-8", newline="\n")


def strip_prompt_echo(prompt: str, text: str) -> str:
    stripped = text.lstrip()
    if stripped.startswith(prompt):
        return stripped[len(prompt):].lstrip()
    return text


def ensure_supporting_imports(source: str) -> str:
    imports = []
    body_lines = []
    in_import_block = True

    for line in source.splitlines():
        if in_import_block and line.startswith("import "):
            imports.append(line)
        else:
            in_import_block = False
            body_lines.append(line)

    import_set = set(imports)

    if "Collectors." in source and "import java.util.stream.Collectors;" not in import_set:
        imports.append("import java.util.stream.Collectors;")

    if "Stream<" in source and "import java.util.stream.*;" not in import_set:
        imports.append("import java.util.stream.*;")

    if "Arrays.asList" in source and "import java.util.*;" not in import_set:
        imports.insert(0, "import java.util.*;")

    if "import java.lang.*;" not in import_set:
        imports.append("import java.lang.*;")

    return "\n".join(imports + [""] + body_lines).strip() + "\n"


def extract_source(text: str) -> str | None:
    class_pos = text.find("class Solution")
    if class_pos == -1:
        return None

    import_lines = []
    for line in text[:class_pos].splitlines():
        line = line.strip()
        if line.startswith("import "):
            import_lines.append(line)

    start = class_pos
    balance = 0
    seen_open = False
    end = None
    for idx in range(class_pos, len(text)):
        ch = text[idx]
        if ch == "{":
            balance += 1
            seen_open = True
        elif ch == "}":
            balance -= 1
            if seen_open and balance == 0:
                end = idx + 1
                break

    if end is None:
        snippet = text[class_pos:].rstrip()
        opens = snippet.count("{")
        closes = snippet.count("}")
        snippet = snippet + ("\n}" * max(0, opens - closes))
    else:
        snippet = text[class_pos:end].strip()

    if import_lines:
        return "\n".join(import_lines) + "\n\n" + snippet + "\n"
    return snippet + "\n"


def normalize_solution(prompt: str, raw_generation: str, declaration: str) -> str:
    raw_generation = strip_prompt_echo(prompt, raw_generation.strip())
    source = extract_source(raw_generation)
    if source is None:
        source = extract_source(prompt + raw_generation)
    if source is None:
        source = declaration.rstrip() + "\n        throw new UnsupportedOperationException(\"Normalization failed\");\n    }\n}\n"

    source = source.replace("Optional.empty", "Optional.empty()")
    source = source.replace("Array.asList", "Arrays.asList")
    source = ensure_supporting_imports(source)
    return source


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tasks-root", required=True)
    args = parser.parse_args()

    tasks_root = pathlib.Path(args.tasks_root)

    for task_dir in sorted(item for item in tasks_root.iterdir() if item.is_dir()):
        prompt_path = task_dir / "dataset" / "prompt.txt"
        declaration_path = task_dir / "dataset" / "declaration.java"
        if not prompt_path.exists() or not declaration_path.exists():
            continue

        prompt = read_text(prompt_path)
        declaration = read_text(declaration_path)

        for raw_path in task_dir.glob("generated-code/*/raw_generation.txt"):
            solution_path = raw_path.parent / "Solution.java"
            normalized = normalize_solution(prompt, read_text(raw_path), declaration)
            write_text(solution_path, normalized)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())

