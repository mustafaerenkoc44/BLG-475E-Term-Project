import argparse
import pathlib


HEADER = """/* @Authors
* Student Names: <student_name>
* Student IDs: <student_id>
*/

"""


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tasks-root", required=True)
    args = parser.parse_args()

    tasks_root = pathlib.Path(args.tasks_root)
    for java_file in tasks_root.rglob("*.java"):
        content = java_file.read_text(encoding="utf-8")
        if content.startswith("/* @Authors"):
            continue
        java_file.write_text(HEADER + content, encoding="utf-8", newline="\n")

    return 0


if __name__ == "__main__":
    raise SystemExit(main())

