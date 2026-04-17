import argparse
import pathlib


MODEL_DIR_NAMES = [
    "Qwen2.5-Coder-1.5B-Instruct-GGUF",
    "DeepSeek-Coder-1.3B-Instruct-GGUF",
]


EXTRA_TESTS = {
    "Java_001_separateParenGroups": """
    @Test
    void improvedHandlesCompactAndEmptyInput() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.separateParenGroups(""));
        Assertions.assertEquals(Arrays.asList("(()())"), s.separateParenGroups("(()())"));
    }
""",
    "Java_006_parseNestedParens": """
    @Test
    void improvedHandlesSingleDeepGroup() {
        Solution s = new Solution();
        Assertions.assertEquals(Arrays.asList(4), s.parseNestedParens("(((())))"));
    }
""",
    "Java_009_rollingMax": """
    @Test
    void improvedCoversEmptyNullAndNegativeInputs() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.rollingMax(List.of()));
        Assertions.assertEquals(List.of(), s.rollingMax(null));
        Assertions.assertEquals(Arrays.asList(-3, -2, -2), s.rollingMax(Arrays.asList(-3, -2, -5)));
    }
""",
    "Java_010_isPalindrome": """
    @Test
    void improvedKeepsPalindromeAndHandlesSingleCharacter() {
        Solution s = new Solution();
        Assertions.assertEquals("racecar", s.makePalindrome("racecar"));
        Assertions.assertEquals("a", s.makePalindrome("a"));
    }
""",
    "Java_017_parseMusic": """
    @Test
    void improvedHandlesEmptyAndSingleTokenInputs() {
        Solution s = new Solution();
        Assertions.assertEquals(List.of(), s.parseMusic(""));
        Assertions.assertEquals(Arrays.asList(4, 2, 1), s.parseMusic("o o| .|"));
    }
""",
    "Java_018_howManyTimes": """
    @Test
    void improvedChecksOverlapAndEmptyStringCases() {
        Solution s = new Solution();
        Assertions.assertEquals(4, s.howManyTimes("aaaa", "a"));
        Assertions.assertEquals(0, s.howManyTimes("", "abc"));
    }
""",
    "Java_023_strlen": """
    @Test
    void improvedChecksWhitespaceAndPunctuation() {
        Solution s = new Solution();
        Assertions.assertEquals(1, s.strlen(" "));
        Assertions.assertEquals(5, s.strlen("a! b?"));
    }
""",
    "Java_027_flipCase": """
    @Test
    void improvedPreservesDigitsAndPunctuation() {
        Solution s = new Solution();
        Assertions.assertEquals("123!aB", s.flipCase("123!Ab"));
    }
""",
    "Java_039_primeFib": """
    @Test
    void improvedChecksNextPrimeFibonacciValue() {
        Solution s = new Solution();
        Assertions.assertEquals(233, s.primeFib(6));
    }
""",
}


def write_text(path: pathlib.Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--tasks-root", required=True)
    args = parser.parse_args()

    tasks_root = pathlib.Path(args.tasks_root)

    for task_dir in sorted(item for item in tasks_root.iterdir() if item.is_dir()):
        extra = EXTRA_TESTS.get(task_dir.name, "")
        for model_name in MODEL_DIR_NAMES:
            base_test_path = task_dir / "generated-tests" / "base" / model_name / "DatasetBaseTest.java"
            improved_test_path = task_dir / "generated-tests" / "improved" / model_name / "DatasetImprovedTest.java"
            if not base_test_path.exists():
                continue

            source = base_test_path.read_text(encoding="utf-8")
            source = source.replace("class DatasetBaseTest {", "class DatasetImprovedTest {")
            if extra:
                source = source.rstrip()
                if source.endswith("}"):
                    source = source[:-1].rstrip() + "\n\n" + extra.strip("\n") + "\n}\n"
            write_text(improved_test_path, source)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())

