import argparse
import pathlib
import re


MODEL_DIR_NAMES = [
    "Qwen2.5-Coder-1.5B-Instruct-GGUF",
    "DeepSeek-Coder-1.3B-Instruct-GGUF",
]


def write_text(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def sanitize_body(source: str) -> str:
    source = source.replace("Array.asList", "Arrays.asList")
    source = re.sub(r"Optional\.empty(?!\()", "Optional.empty()", source)

    class_marker = "public class Main"
    main_marker = "public static void main(String[] args) {"

    class_pos = source.find(class_marker)
    main_pos = source.find(main_marker)
    if class_pos == -1 or main_pos == -1:
        return source

    body_start = main_pos + len(main_marker)
    body = source[body_start:]
    body = body.rsplit("}", 2)[0]
    body = body.replace("throw new AssertionError();", 'Assertions.fail("Dataset assertion failed");')
    return body.strip()


def build_junit_test(body: str) -> str:
    return "\n".join(
        [
            "import java.util.*;",
            "import java.lang.*;",
            "",
            "import org.junit.jupiter.api.Assertions;",
            "import org.junit.jupiter.api.Test;",
            "",
            "class DatasetBaseTest {",
            "    @Test",
            "    void datasetBaseTest() {",
            "\n".join(f"        {line}" if line else "" for line in body.splitlines()),
            "    }",
            "}",
            "",
        ]
    )


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tasks-root", required=True)
    args = parser.parse_args()

    tasks_root = pathlib.Path(args.tasks_root)

    for task_dir in sorted([item for item in tasks_root.iterdir() if item.is_dir()]):
        base_test_path = task_dir / "dataset" / "base_test_dataset.java"
        if not base_test_path.exists():
            continue

        body = sanitize_body(base_test_path.read_text(encoding="utf-8"))
        junit_source = build_junit_test(body)

        for model_name in MODEL_DIR_NAMES:
            target = task_dir / "generated-tests" / "base" / model_name / "DatasetBaseTest.java"
            write_text(target, junit_source)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())

