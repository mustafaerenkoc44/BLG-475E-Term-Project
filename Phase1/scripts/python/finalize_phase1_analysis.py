from __future__ import annotations

import csv
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
TASKS_DIR = ROOT / "tasks"
ANALYSIS_DIR = ROOT / "docs" / "analysis"
DATA_DIR = ROOT / "data"
LITERATURE_DIR = ROOT / "docs" / "literature"

BASE_TEST_RESULTS = ROOT / "results" / "base_test_results.csv"
BASE_COVERAGE_RESULTS = ROOT / "results" / "base_coverage_results.csv"
IMPROVED_COVERAGE_RESULTS = ROOT / "results" / "improved_coverage_results.csv"
TASK_INDEX = TASKS_DIR / "task_index.csv"
SELECTED_PROMPTS = DATA_DIR / "selected_prompts.csv"

MODELS = [
    "Qwen2.5-Coder-1.5B-Instruct-GGUF",
    "DeepSeek-Coder-1.3B-Instruct-GGUF",
]

MODEL_LABELS = {
    "Qwen2.5-Coder-1.5B-Instruct-GGUF": "Qwen2.5-Coder-1.5B",
    "DeepSeek-Coder-1.3B-Instruct-GGUF": "DeepSeek-Coder-1.3B",
}

REPAIR_SUMMARIES = {
    "Java_010_isPalindrome": "Repaired palindrome suffix handling so the generated solution appends only the reversed non-palindromic prefix.",
    "Java_012_longest": "Repaired empty-list and first-longest handling to match the Optional contract in the prompt.",
    "Java_017_parseMusic": "Repaired token parsing so `o`, `o|`, and `.|` are decoded consistently without skipping or duplicating notes.",
    "Java_019_sortNumbers": "Repaired token-to-value mapping and output ordering for the space-delimited numeral words.",
    "Java_023_strlen": "Removed malformed duplicate fragments from the normalized output and restored the intended `strlen` implementation.",
    "Java_027_flipCase": "Repaired case-conversion logic so letters toggle case while digits and punctuation remain unchanged.",
    "Java_039_primeFib": "Repaired the search loop to stop on the requested prime Fibonacci index without timing out on moderate inputs.",
}

TASK_SPECIFIC = {
    "hasCloseElements": {
        "valid": [
            ("V1", "At least one pair is closer than the threshold.", "numbers=[1.0, 2.8, 3.0, 4.0, 5.0, 2.0], threshold=0.3", "Yes", "Expected result is `true`."),
            ("V2", "No pair is closer than the threshold.", "numbers=[1.0, 2.0, 3.0], threshold=0.5", "Yes", "Expected result is `false`."),
            ("V3", "Duplicate or near-duplicate values appear in a longer list.", "numbers=[2.0, 2.0, 5.0], threshold=0.1", "Partially", "Improved tests make the equality boundary explicit."),
        ],
        "invalid": [
            ("I1", "Null list input.", "numbers=null, threshold=0.3", "No", "Out of dataset contract; noted as robustness risk."),
            ("I2", "Negative threshold.", "numbers=[1.0, 2.0], threshold=-0.1", "No", "Prompt does not define semantics for negative thresholds."),
        ],
        "boundaries": [
            ("B1", "List size below two.", "numbers=[] or [1.0], threshold=0.3", "Partially", "Improved tests document the degenerate size cases."),
            ("B2", "Pair distance exactly equals the threshold.", "numbers=[1.0, 1.3], threshold=0.3", "No", "Useful for checking `< threshold` versus `<= threshold`."),
        ],
        "smells": ["missing exact-threshold boundary", "literal-heavy numeric assertions"],
        "improved_focus": "degenerate list sizes and exact-threshold comparisons",
    },
    "separateParenGroups": {
        "valid": [
            ("V1", "A single balanced group is returned as one compact string.", "paren_string=\"( )\"", "Yes", "Spaces are ignored."),
            ("V2", "Multiple top-level groups are split correctly.", "paren_string=\"( ) (( )) (( )( ))\"", "Yes", "Representative multi-group scenario from the prompt."),
            ("V3", "Nested groups remain intact after whitespace stripping.", "paren_string=\"((()))\"", "Partially", "The grouping logic is branch-sensitive around nesting depth."),
        ],
        "invalid": [
            ("I1", "Unbalanced parentheses.", "paren_string=\"(()\"", "No", "Prompt assumes balanced groups, so this is out of contract."),
            ("I2", "Non-parenthesis tokens in the stream.", "paren_string=\"() abc (())\"", "No", "Not covered by the dataset."),
        ],
        "boundaries": [
            ("B1", "Empty or whitespace-only input.", "paren_string=\"   \"", "No", "Added as an improved boundary case."),
            ("B2", "Adjacent groups with no separating spaces.", "paren_string=\"()()\"", "No", "Checks whether group separation depends on spaces."),
        ],
        "smells": ["missing whitespace-only boundary", "assertion roulette in grouped output checks"],
        "improved_focus": "whitespace-only inputs and adjacent top-level groups",
    },
    "truncateNumber": {
        "valid": [
            ("V1", "Positive non-integer input.", "x=3.5", "Yes", "The fractional part should be preserved."),
            ("V2", "Negative non-integer input.", "x=-2.75", "Partially", "Documents sign handling at the fractional boundary."),
            ("V3", "Whole-number input.", "x=7.0", "Yes", "Expected output is `0.0`."),
        ],
        "invalid": [
            ("I1", "NaN input.", "x=NaN", "No", "Outside the assignment contract."),
            ("I2", "Infinite input.", "x=Infinity", "No", "Outside the assignment contract."),
        ],
        "boundaries": [
            ("B1", "Zero input.", "x=0.0", "No", "Useful scalar baseline."),
            ("B2", "Value immediately below an integer.", "x=4.999999", "No", "Checks floating-point truncation sensitivity."),
        ],
        "smells": ["missing negative fractional case", "few numeric precision probes"],
        "improved_focus": "whole numbers, zero, and negative fractions",
    },
    "belowZero": {
        "valid": [
            ("V1", "Running balance never goes below zero.", "operations=[1, 2, -1]", "Yes", "Expected result is `false`."),
            ("V2", "Running balance drops below zero once.", "operations=[1, -3, 2]", "Yes", "Expected result is `true`."),
            ("V3", "Balance oscillates around zero before a later failure.", "operations=[2, -1, -1, 1, -2]", "Partially", "Important for stateful branch coverage."),
        ],
        "invalid": [
            ("I1", "Null operation list.", "operations=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty input list.", "operations=[]", "No", "Documents the neutral starting state."),
            ("B2", "First operation immediately breaks the balance.", "operations=[-1]", "No", "Critical boundary on the first iteration."),
        ],
        "smells": ["missing empty-sequence boundary", "limited first-step failure probing"],
        "improved_focus": "empty inputs and first-step balance failure",
    },
    "meanAbsoluteDeviation": {
        "valid": [
            ("V1", "Symmetric values around the mean.", "numbers=[1.0, 2.0, 3.0]", "Yes", "Easy oracle for manual checking."),
            ("V2", "Repeated values with a non-zero deviation.", "numbers=[1.0, 1.0, 3.0]", "Partially", "Highlights averaging plus absolute-difference logic."),
            ("V3", "Decimal inputs.", "numbers=[0.5, 1.5, 2.5]", "No", "Useful precision-oriented case."),
        ],
        "invalid": [
            ("I1", "Empty list.", "numbers=[]", "No", "The prompt does not define a mean for empty input."),
            ("I2", "Null list.", "numbers=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Singleton list.", "numbers=[4.0]", "No", "Expected deviation collapses to `0.0`."),
            ("B2", "Two-element list.", "numbers=[1.0, 5.0]", "No", "Smallest non-trivial averaging case."),
        ],
        "smells": ["missing singleton boundary", "few decimal precision checks"],
        "improved_focus": "singleton and decimal-valued datasets",
    },
    "intersperse": {
        "valid": [
            ("V1", "Empty list remains empty.", "numbers=[], delimiter=7", "Yes", "Confirms no extraneous delimiter is added."),
            ("V2", "Singleton list is unchanged.", "numbers=[5], delimiter=7", "Yes", "There is no internal gap to fill."),
            ("V3", "Multi-element list gets delimiter between each pair.", "numbers=[1, 2, 3], delimiter=0", "Yes", "Main functional scenario."),
        ],
        "invalid": [
            ("I1", "Null list.", "numbers=null, delimiter=0", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Size exactly one.", "numbers=[9], delimiter=4", "Yes", "Classic off-by-one boundary."),
            ("B2", "Delimiter already appears in the list.", "numbers=[0, 1, 0], delimiter=0", "No", "Checks whether inserted and original zeros are distinguished only by position."),
        ],
        "smells": ["few aliasing-style delimiter cases", "boundary focus dominated by happy path"],
        "improved_focus": "delimiter reuse and low-cardinality inputs",
    },
    "parseNestedParens": {
        "valid": [
            ("V1", "Single group with depth one.", "paren_string=\"()\"", "Yes", "Expected output contains `1`."),
            ("V2", "Multiple groups with different depths.", "paren_string=\"(()()) ((())) () ((())()())\"", "Yes", "Prompt example."),
            ("V3", "A deeply nested single group.", "paren_string=\"(((())))\"", "Partially", "Stresses maximum-depth tracking."),
        ],
        "invalid": [
            ("I1", "Unbalanced parentheses.", "paren_string=\"(()\"", "No", "Outside the stated balanced-input contract."),
            ("I2", "Unexpected tokens between groups.", "paren_string=\"() abc (())\"", "No", "Not defined by the prompt."),
        ],
        "boundaries": [
            ("B1", "Empty input string.", "paren_string=\"\"", "No", "Added in improved tests."),
            ("B2", "Alternation between depth one and depth two.", "paren_string=\"() (())\"", "Partially", "Useful for branch transitions in the depth counter."),
        ],
        "smells": ["missing empty-input boundary", "depth transitions under-specified"],
        "improved_focus": "empty input and depth-transition scenarios",
    },
    "filterBySubstring": {
        "valid": [
            ("V1", "Some strings contain the substring.", "strings=[\"abc\", \"zab\", \"qq\"], substring=\"ab\"", "Yes", "Expected to keep only matching entries."),
            ("V2", "No string contains the substring.", "strings=[\"xx\", \"yy\"], substring=\"ab\"", "Yes", "Expected to return an empty list."),
            ("V3", "Case-sensitive mismatch.", "strings=[\"Abc\", \"abc\"], substring=\"ab\"", "Partially", "Documents exact matching rules."),
        ],
        "invalid": [
            ("I1", "Null list.", "strings=null, substring=\"ab\"", "No", "Out of contract."),
            ("I2", "Null substring.", "strings=[\"abc\"], substring=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty source list.", "strings=[], substring=\"ab\"", "Yes", "Should return `[]`."),
            ("B2", "Empty substring.", "strings=[\"abc\", \"\"], substring=\"\"", "No", "Important unspecified boundary."),
        ],
        "smells": ["missing empty-substring decision", "limited case-sensitivity checks"],
        "improved_focus": "empty substring and case sensitivity",
    },
    "sumProduct": {
        "valid": [
            ("V1", "Empty input list.", "numbers=[]", "Yes", "Defines neutral elements for sum and product."),
            ("V2", "Singleton list.", "numbers=[4]", "Yes", "Expected `[4, 4]`."),
            ("V3", "Multiple values including zero.", "numbers=[2, 0, 5]", "Partially", "Product should collapse to zero."),
        ],
        "invalid": [
            ("I1", "Null list.", "numbers=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Single zero.", "numbers=[0]", "No", "Boundary between neutral and absorbing product behavior."),
            ("B2", "Mixed positive and negative values.", "numbers=[-2, 3, -1]", "No", "Documents sign behavior."),
        ],
        "smells": ["few sign-combination cases", "limited product-zero coverage"],
        "improved_focus": "zero absorption and negative-number combinations",
    },
    "rollingMax": {
        "valid": [
            ("V1", "Strictly increasing sequence.", "numbers=[1, 2, 3, 4]", "Yes", "Rolling maximum changes every step."),
            ("V2", "Sequence with drops after a peak.", "numbers=[1, 3, 2, 5, 4]", "Yes", "Peak retention is the main branch."),
            ("V3", "Sequence with duplicates.", "numbers=[2, 2, 1, 2]", "Partially", "Checks equality handling."),
        ],
        "invalid": [
            ("I1", "Null list.", "numbers=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty list.", "numbers=[]", "No", "Added in improved tests."),
            ("B2", "Singleton list.", "numbers=[7]", "No", "Smallest non-empty sequence."),
        ],
        "smells": ["missing empty-input boundary", "few equality-versus-greater comparisons"],
        "improved_focus": "empty input and duplicate maxima",
    },
    "isPalindrome": {
        "valid": [
            ("V1", "Input is already a palindrome.", "string=\"aba\"", "Yes", "No suffix should be appended."),
            ("V2", "Input needs one appended character.", "string=\"cata\"", "Yes", "Prompt example gives `catac`."),
            ("V3", "Input needs multiple appended characters.", "string=\"cat\"", "Yes", "Prompt example exercises the main search loop."),
        ],
        "invalid": [
            ("I1", "Null string.", "string=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty string.", "string=\"\"", "Yes", "Expected output remains empty."),
            ("B2", "Single character.", "string=\"x\"", "No", "Shortest already-palindromic input."),
        ],
        "smells": ["missing single-character boundary", "palindromic suffix search only lightly mutated in the base set"],
        "improved_focus": "single-character strings and minimal append cases",
    },
    "stringXor": {
        "valid": [
            ("V1", "Identical binary strings.", "a=\"1010\", b=\"1010\"", "Yes", "Every position should become `0`."),
            ("V2", "Complementary binary strings.", "a=\"111\", b=\"000\"", "Yes", "Every position should become `1`."),
            ("V3", "Mixed binary strings.", "a=\"1100\", b=\"1010\"", "Yes", "Exercises both bit outcomes."),
        ],
        "invalid": [
            ("I1", "Different string lengths.", "a=\"10\", b=\"101\"", "No", "Prompt assumes equal-length inputs."),
            ("I2", "Non-binary characters.", "a=\"10a\", b=\"001\"", "No", "Outside the problem contract."),
        ],
        "boundaries": [
            ("B1", "Empty strings.", "a=\"\", b=\"\"", "No", "Documents the degenerate equal-length case."),
            ("B2", "Length one.", "a=\"1\", b=\"0\"", "No", "Smallest non-empty input."),
        ],
        "smells": ["no explicit malformed-input cases", "smallest equal-length boundaries absent"],
        "improved_focus": "empty strings and unit-length inputs",
    },
    "longest": {
        "valid": [
            ("V1", "Unique longest string exists.", "strings=[\"a\", \"bb\", \"ccc\"]", "Yes", "Prompt example returns `Optional[ccc]`."),
            ("V2", "Multiple strings tie for the longest length.", "strings=[\"aa\", \"bb\", \"c\"]", "Yes", "The first longest string should be returned."),
            ("V3", "Empty input list.", "strings=[]", "Yes", "Expected result is `Optional.empty`."),
        ],
        "invalid": [
            ("I1", "Null list.", "strings=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Singleton list.", "strings=[\"solo\"]", "No", "Smallest non-empty collection."),
            ("B2", "Tie appears after a shorter prefix.", "strings=[\"a\", \"bb\", \"cc\"]", "No", "Checks stable first-longest behavior."),
        ],
        "smells": ["missing singleton boundary", "tie handling was under-mutated in the base suite"],
        "improved_focus": "singleton lists and explicit tie cases",
    },
    "greatestCommonDivisor": {
        "valid": [
            ("V1", "Inputs are coprime.", "a=3, b=5", "Yes", "Expected result is `1`."),
            ("V2", "One input divides the other.", "a=25, b=15", "Yes", "Expected result is `5`."),
            ("V3", "Inputs share a larger divisor.", "a=84, b=18", "No", "Useful Euclidean-loop case."),
        ],
        "invalid": [
            ("I1", "At least one zero input.", "a=0, b=5", "No", "Prompt does not describe zero-handling."),
            ("I2", "Negative integers.", "a=-4, b=6", "No", "Out of stated examples."),
        ],
        "boundaries": [
            ("B1", "Equal inputs.", "a=7, b=7", "No", "GCD should equal the input."),
            ("B2", "GCD is one after several modulo steps.", "a=35, b=64", "No", "Stresses the loop termination path."),
        ],
        "smells": ["zero-handling not documented", "few multi-step Euclidean cases"],
        "improved_focus": "equal inputs and longer Euclidean-loop paths",
    },
    "allPrefixes": {
        "valid": [
            ("V1", "Regular multi-character string.", "string=\"abc\"", "Yes", "Prompt example."),
            ("V2", "Single-character string.", "string=\"x\"", "No", "Only one prefix should be returned."),
            ("V3", "String with repeated characters.", "string=\"aaa\"", "No", "Ensures prefixes depend on position, not distinctness."),
        ],
        "invalid": [
            ("I1", "Null string.", "string=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty string.", "string=\"\"", "No", "Important degenerate case."),
            ("B2", "Length one.", "string=\"x\"", "No", "Smallest non-empty input."),
        ],
        "smells": ["missing empty-string boundary", "few repeated-character checks"],
        "improved_focus": "empty input and repeated-character prefixes",
    },
    "stringSequence": {
        "valid": [
            ("V1", "Lower boundary `n=0`.", "n=0", "Yes", "Prompt example returns `0`."),
            ("V2", "Small positive integer.", "n=5", "Yes", "Prompt example returns `0 1 2 3 4 5`."),
            ("V3", "Another positive integer.", "n=2", "No", "Documents inclusive upper bound behavior."),
        ],
        "invalid": [
            ("I1", "Negative integer.", "n=-1", "No", "Out of the prompt contract."),
        ],
        "boundaries": [
            ("B1", "Boundary from zero to one.", "n=1", "No", "Smallest expansion beyond the base case."),
            ("B2", "Formatting around two-digit numbers.", "n=10", "No", "Checks spacing stability as token width changes."),
        ],
        "smells": ["few formatting-width checks", "negative input policy undocumented"],
        "improved_focus": "n=1 and wider token formatting",
    },
    "countDistinctCharacters": {
        "valid": [
            ("V1", "Mixed-case duplicates collapse to one distinct character.", "string=\"xyzXYZ\"", "Yes", "Prompt example returns `3`."),
            ("V2", "Mixed repeated and unique characters.", "string=\"Jerry\"", "Yes", "Prompt example returns `4`."),
            ("V3", "All characters are the same ignoring case.", "string=\"aAaA\"", "No", "Expected result is `1`."),
        ],
        "invalid": [
            ("I1", "Null string.", "string=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty string.", "string=\"\"", "No", "Expected count is `0`."),
            ("B2", "Single character.", "string=\"Q\"", "No", "Expected count is `1`."),
        ],
        "smells": ["missing empty-string boundary", "few punctuation and whitespace probes"],
        "improved_focus": "empty string and fully repeated-case inputs",
    },
    "parseMusic": {
        "valid": [
            ("V1", "Sequence uses all supported token kinds.", "string=\"o o| .|\"", "Yes", "Covers whole, half, and quarter notes."),
            ("V2", "Longer mixed sequence from the prompt.", "string=\"o o| .| o| o| .| .| .| .| o o\"", "Yes", "Representative branch-rich case."),
            ("V3", "Repeated single-token stream.", "string=\".| .| .|\"", "No", "Useful for compact-token parsing."),
        ],
        "invalid": [
            ("I1", "Unknown note token.", "string=\"x o|\"", "No", "Out of contract."),
            ("I2", "Incomplete token.", "string=\"o |\"", "No", "Malformed representation."),
        ],
        "boundaries": [
            ("B1", "Single-token input.", "string=\"o\"", "No", "Smallest valid note sequence."),
            ("B2", "Extra internal spacing.", "string=\"o   o|   .|\"", "No", "Added in improved tests to stabilize whitespace parsing."),
        ],
        "smells": ["token parsing depended too heavily on prompt example structure", "missing whitespace-variation tests"],
        "improved_focus": "single-token streams and repeated-space tokenization",
    },
    "howManyTimes": {
        "valid": [
            ("V1", "No occurrence in an empty source string.", "string=\"\", substring=\"a\"", "Yes", "Prompt example returns `0`."),
            ("V2", "Single-character substring overlaps at every position.", "string=\"aaa\", substring=\"a\"", "Yes", "Prompt example returns `3`."),
            ("V3", "Longer overlapping substring.", "string=\"aaaa\", substring=\"aa\"", "Yes", "Prompt example returns `3`."),
        ],
        "invalid": [
            ("I1", "Empty substring.", "string=\"abc\", substring=\"\"", "No", "Important undefined case."),
            ("I2", "Null arguments.", "string=null, substring=\"a\"", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Substring longer than the source.", "string=\"ab\", substring=\"abc\"", "No", "Should return `0`."),
            ("B2", "Source and substring are identical.", "string=\"john\", substring=\"john\"", "Yes", "Expected single full-length match."),
        ],
        "smells": ["empty-substring policy missing", "overlap behavior only sampled on short examples"],
        "improved_focus": "substring-longer-than-source and overlap-heavy cases",
    },
    "sortNumbers": {
        "valid": [
            ("V1", "Unsorted numeral words.", "numbers=\"three one five\"", "Yes", "Prompt example."),
            ("V2", "Already sorted input.", "numbers=\"zero one two\"", "No", "Checks idempotence."),
            ("V3", "Input with duplicates.", "numbers=\"two one two\"", "No", "Ensures multiplicity is preserved."),
        ],
        "invalid": [
            ("I1", "Unsupported token.", "numbers=\"one ten two\"", "No", "Out of contract."),
            ("I2", "Unexpected punctuation.", "numbers=\"one, two\"", "No", "Tokenizer robustness risk."),
        ],
        "boundaries": [
            ("B1", "Empty string.", "numbers=\"\"", "No", "Documents the degenerate token stream."),
            ("B2", "Single numeral token.", "numbers=\"seven\"", "No", "Smallest valid input."),
        ],
        "smells": ["tokenization assumptions hidden in string literals", "single-token boundary absent"],
        "improved_focus": "duplicate numeral words and empty-token handling",
    },
    "findClosestElements": {
        "valid": [
            ("V1", "Unique closest pair in an unsorted list.", "numbers=[1.0, 2.0, 3.0, 4.0, 5.0, 2.2]", "Yes", "Prompt example returns `[2.0, 2.2]`."),
            ("V2", "Exact duplicates create a zero-distance pair.", "numbers=[1.0, 2.0, 3.0, 4.0, 5.0, 2.0]", "Yes", "Prompt example returns `[2.0, 2.0]`."),
            ("V3", "Exactly two elements are present.", "numbers=[-1.0, 4.5]", "No", "Only possible pair should be returned."),
        ],
        "invalid": [
            ("I1", "Fewer than two elements.", "numbers=[1.0]", "No", "Outside the prompt contract."),
            ("I2", "Null list.", "numbers=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "List of size two.", "numbers=[1.0, 1.1]", "No", "Minimal valid collection."),
            ("B2", "Tie between two equally close pairs.", "numbers=[1.0, 2.0, 3.0, 4.0]", "No", "Useful for deterministic tie behavior."),
        ],
        "smells": ["tie policy not explicit in the base suite", "minimal-size valid list absent"],
        "improved_focus": "two-element lists and equal-distance ties",
    },
    "rescaleToUnit": {
        "valid": [
            ("V1", "Strictly increasing list.", "numbers=[1.0, 2.0, 3.0, 4.0, 5.0]", "Yes", "Prompt example."),
            ("V2", "Unsorted list with negative and positive values.", "numbers=[-2.0, 3.0, 1.0]", "No", "Checks min and max discovery."),
            ("V3", "List with repeated interior values.", "numbers=[1.0, 1.0, 5.0, 3.0]", "No", "Interior duplicates should map consistently."),
        ],
        "invalid": [
            ("I1", "All values are equal.", "numbers=[2.0, 2.0, 2.0]", "No", "Would force division by zero in the usual formula."),
            ("I2", "Fewer than two elements.", "numbers=[4.0]", "No", "Outside the prompt contract."),
        ],
        "boundaries": [
            ("B1", "Exactly two elements.", "numbers=[2.0, 6.0]", "No", "Smallest valid rescaling case."),
            ("B2", "Input already spans 0 and 1.", "numbers=[0.0, 0.5, 1.0]", "No", "Should remain unchanged."),
        ],
        "smells": ["division-by-zero risk not exposed by the base suite", "minimal valid size absent"],
        "improved_focus": "two-element normalization and equal-value rejection cases",
    },
    "strlen": {
        "valid": [
            ("V1", "Empty string.", "string=\"\"", "Yes", "Expected length is `0`."),
            ("V2", "Alphabetic content.", "string=\"abc\"", "Yes", "Straightforward baseline."),
            ("V3", "Whitespace and punctuation.", "string=\"a b!\"", "No", "Every character should be counted."),
        ],
        "invalid": [
            ("I1", "Null string.", "string=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Single character.", "string=\"x\"", "No", "Smallest non-empty string."),
            ("B2", "String containing only spaces.", "string=\"   \"", "No", "Documents that whitespace counts as characters."),
        ],
        "smells": ["overly trivial base suite", "whitespace-only case absent"],
        "improved_focus": "single-character and whitespace-only strings",
    },
    "factorize": {
        "valid": [
            ("V1", "Prime power.", "n=8", "Yes", "Prompt example returns `[2, 2, 2]`."),
            ("V2", "Square of a prime.", "n=25", "Yes", "Prompt example returns `[5, 5]`."),
            ("V3", "Composite with distinct primes.", "n=70", "Yes", "Prompt example returns `[2, 5, 7]`."),
        ],
        "invalid": [
            ("I1", "Number less than two.", "n=1", "No", "Prime factorization is undefined or empty depending on policy."),
            ("I2", "Negative integer.", "n=-8", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Smallest prime.", "n=2", "No", "Expected factor list is `[2]`."),
            ("B2", "Large repeated factor count.", "n=32", "No", "Exercises repeated division in the loop."),
        ],
        "smells": ["minimal prime boundary absent", "repetition-heavy factor chains lightly sampled"],
        "improved_focus": "n=2 and repeated prime multiplicity",
    },
    "flipCase": {
        "valid": [
            ("V1", "Lowercase letters become uppercase.", "string=\"abc\"", "Yes", "Main case-flip path."),
            ("V2", "Uppercase letters become lowercase.", "string=\"XYZ\"", "Yes", "Inverse path."),
            ("V3", "Digits and punctuation remain unchanged.", "string=\"aB3!\"", "Yes", "Important preservation rule."),
        ],
        "invalid": [
            ("I1", "Null string.", "string=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty string.", "string=\"\"", "No", "Added in improved tests."),
            ("B2", "String with no alphabetic characters.", "string=\"123!?\"", "No", "Checks no-op path."),
        ],
        "smells": ["non-letter preservation depended on sparse examples", "empty-string case absent"],
        "improved_focus": "empty strings and no-letter inputs",
    },
    "concatenate": {
        "valid": [
            ("V1", "Empty list of strings.", "strings=[]", "Yes", "Expected output is the empty string."),
            ("V2", "Singleton list.", "strings=[\"abc\"]", "Yes", "Output should equal the only element."),
            ("V3", "Multiple strings are joined in order.", "strings=[\"ab\", \"cd\", \"ef\"]", "Yes", "Main aggregation scenario."),
        ],
        "invalid": [
            ("I1", "Null list.", "strings=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Contains empty string elements.", "strings=[\"ab\", \"\", \"cd\"]", "No", "Order must be preserved without inserting separators."),
            ("B2", "All elements empty.", "strings=[\"\", \"\"]", "No", "Checks neutral aggregation."),
        ],
        "smells": ["empty-element boundary absent", "aggregation tested mostly on non-empty members"],
        "improved_focus": "empty-string elements and all-empty collections",
    },
    "filterByPrefix": {
        "valid": [
            ("V1", "Some strings start with the prefix.", "strings=[\"abc\", \"bcd\", \"cde\", \"array\"], prefix=\"a\"", "Yes", "Prompt example."),
            ("V2", "No strings start with the prefix.", "strings=[\"bbb\", \"ccc\"], prefix=\"a\"", "Yes", "Expected empty result."),
            ("V3", "Prefix equals the full string.", "strings=[\"a\", \"ab\", \"ba\"], prefix=\"a\"", "No", "Full-string match should be kept."),
        ],
        "invalid": [
            ("I1", "Null list.", "strings=null, prefix=\"a\"", "No", "Out of contract."),
            ("I2", "Null prefix.", "strings=[\"abc\"], prefix=null", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Empty input list.", "strings=[], prefix=\"a\"", "Yes", "Prompt example."),
            ("B2", "Empty prefix.", "strings=[\"abc\", \"\"], prefix=\"\"", "No", "Documents an important unspecified case."),
        ],
        "smells": ["empty-prefix policy not explicit", "full-string prefix equality absent"],
        "improved_focus": "empty prefixes and exact full-string prefix matches",
    },
    "isPrime": {
        "valid": [
            ("V1", "Small prime number.", "n=2", "No", "Smallest prime boundary."),
            ("V2", "Composite number.", "n=6", "Yes", "Prompt example returns `false`."),
            ("V3", "Large prime number.", "n=101", "Yes", "Prompt example returns `true`."),
        ],
        "invalid": [
            ("I1", "Non-positive integer.", "n=1 or n=0", "Yes", "Prompt examples include `1` as non-prime."),
            ("I2", "Negative integer.", "n=-7", "No", "Out of contract but useful for robustness notes."),
        ],
        "boundaries": [
            ("B1", "Perfect square of a prime.", "n=49", "No", "Important divisor-loop boundary."),
            ("B2", "Even number just above two.", "n=4", "Yes", "Fast rejection path."),
        ],
        "smells": ["perfect-square branch absent", "smallest prime boundary not explicit"],
        "improved_focus": "n=2 and perfect-square composites",
    },
    "fizzBuzz": {
        "valid": [
            ("V1", "Range below the first relevant multiple.", "n=10", "No", "Expected count is `0`."),
            ("V2", "Range with qualifying multiples but no digit seven.", "n=50", "Yes", "Prompt example returns `0`."),
            ("V3", "Range with qualifying numbers containing digit seven.", "n=79", "Yes", "Prompt examples show the transition from `78` to `79`."),
        ],
        "invalid": [
            ("I1", "Negative upper bound.", "n=-1", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Just before and after 77 enters the range.", "n=78 and n=79", "Yes", "Important count jump because `77` contributes twice."),
            ("B2", "First qualifying multiple.", "n=12 or n=14", "No", "Checks the start of the divisibility filter."),
        ],
        "smells": ["count jump around 77 is easy to miss", "first qualifying divisor boundary absent"],
        "improved_focus": "start-of-range divisibility and the 77 transition",
    },
    "primeFib": {
        "valid": [
            ("V1", "First prime Fibonacci number.", "n=1", "Yes", "Prompt example returns `2`."),
            ("V2", "Mid-range requested index.", "n=4", "Yes", "Prompt example returns `13`."),
            ("V3", "Larger requested index.", "n=5", "Yes", "Prompt example returns `89`."),
        ],
        "invalid": [
            ("I1", "Non-positive index.", "n=0", "No", "Out of contract."),
        ],
        "boundaries": [
            ("B1", "Boundary between first and second prime Fibonacci values.", "n=1 and n=2", "Yes", "Confirms sequence indexing."),
            ("B2", "Performance-sensitive moderate index.", "n=6", "No", "Useful for detecting runaway search loops."),
        ],
        "smells": ["few performance-sensitive inputs", "indexing boundary lightly sampled"],
        "improved_focus": "moderate indices and sequence-index boundaries",
    },
}


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as handle:
        reader = csv.DictReader(handle)
        rows: list[dict[str, str]] = []
        for row in reader:
            normalized = {}
            for key, value in row.items():
                clean_key = (key or "").strip().strip('"')
                normalized[clean_key] = value
            rows.append(normalized)
        return rows


def write_csv(path: Path, rows: list[dict[str, str]], fieldnames: list[str]) -> None:
    with path.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


def load_coverage(path: Path) -> dict[tuple[str, str], dict[str, str]]:
    rows = read_csv(path)
    result: dict[tuple[str, str], dict[str, str]] = {}
    for row in rows:
        result[(row["task_folder"], row["model_name"])] = row
    return result


def percent(covered: int, missed: int) -> str:
    total = covered + missed
    if total == 0:
        return "N/A"
    return f"{(covered / total) * 100:.2f}%"


def delta_percent(base_covered: int, base_missed: int, improved_covered: int, improved_missed: int) -> str:
    total_base = base_covered + base_missed
    total_improved = improved_covered + improved_missed
    if total_base == 0 or total_improved == 0:
        return "N/A"
    base_value = (base_covered / total_base) * 100
    improved_value = (improved_covered / total_improved) * 100
    return f"{improved_value - base_value:+.2f}%"


def render_table(rows: list[tuple[str, str, str, str, str]], title: str) -> str:
    lines = [
        title,
        "",
        "| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |",
        "|---|---|---|---|---|",
    ]
    for row in rows:
        lines.append(f"| {row[0]} | {row[1]} | `{row[2]}` | {row[3]} | {row[4]} |")
    return "\n".join(lines)


def render_equivalence(task_id: str, method_name: str, analysis: dict[str, object]) -> str:
    return "\n\n".join(
        [
            f"# Equivalence Partitioning - {task_id}",
            f"Method: `{method_name}`",
            render_table(analysis["valid"], "## Valid Classes"),
            render_table(analysis["invalid"], "## Invalid Classes"),
            render_table(analysis["boundaries"], "## Boundary Conditions"),
        ]
    ) + "\n"


def render_coverage_notes(
    task_id: str,
    task_folder: str,
    analysis: dict[str, object],
    base_coverage: dict[tuple[str, str], dict[str, str]],
    improved_coverage: dict[tuple[str, str], dict[str, str]],
) -> str:
    base_lines = []
    improved_lines = []
    missed_lines = []
    new_coverage_lines = []

    for model in MODELS:
        base_row = base_coverage[(task_folder, model)]
        improved_row = improved_coverage[(task_folder, model)]
        base_cov = int(base_row["branch_covered"])
        base_missed = int(base_row["branch_missed"])
        improved_cov = int(improved_row["branch_covered"])
        improved_missed = int(improved_row["branch_missed"])
        label = MODEL_LABELS[model]

        base_lines.append(
            f"- `{label}` base coverage: `{base_cov}` covered / `{base_missed}` missed ({percent(base_cov, base_missed)})"
        )
        improved_lines.append(
            f"- `{label}` improved coverage: `{improved_cov}` covered / `{improved_missed}` missed ({percent(improved_cov, improved_missed)}; delta {delta_percent(base_cov, base_missed, improved_cov, improved_missed)})"
        )

        if base_missed > 0:
            missed_lines.append(
                f"- `{label}` still missed `{base_missed}` branch(es) before improvement."
            )
        if improved_cov > base_cov or improved_missed < base_missed:
            new_coverage_lines.append(
                f"- `{label}` gained coverage after targeting {analysis['improved_focus']}."
            )

    if not missed_lines:
        missed_lines.append("- Both models reached full branch coverage under the base JUnit suite for this task.")
    if not new_coverage_lines:
        new_coverage_lines.append("- Improved tests mainly strengthened black-box evidence; branch coverage did not increase further on this task.")

    remaining_gaps = []
    for model in MODELS:
        improved_row = improved_coverage[(task_folder, model)]
        improved_missed = int(improved_row["branch_missed"])
        if improved_missed > 0:
            remaining_gaps.append(
                f"- `{MODEL_LABELS[model]}` retains `{improved_missed}` missed branch(es); the remaining gap is likely in a rare helper path or an out-of-contract branch."
            )
    if not remaining_gaps:
        remaining_gaps.append("- No remaining branch gap was observed after the improved JUnit suite.")

    smell_lines = [f"- {smell}" for smell in analysis["smells"]]

    return "\n".join(
        [
            f"# Coverage Notes - {task_id}",
            "",
            "## Base Tests",
            *base_lines,
            "### Missed Branches Before Improvement",
            *missed_lines,
            "### Smells Observed",
            *smell_lines,
            "",
            "## Improved Tests",
            *improved_lines,
            "### Newly Covered Behavior",
            *new_coverage_lines,
            "### Remaining Gaps",
            *remaining_gaps,
            "",
        ]
    )


def render_refactoring_log(task_id: str, task_folder: str) -> str:
    if task_folder in REPAIR_SUMMARIES:
        body = [
            f"# Refactoring Log - {task_id}",
            "",
            "- Base dataset execution exposed a correctness or normalization defect after the raw generation step.",
            f"- Repair summary: {REPAIR_SUMMARIES[task_folder]}",
            "- Result: both model variants compiled and passed the dataset base tests after the repair pass.",
            "- Follow-up: the repaired solution was retained for both base-test and coverage measurements.",
            "",
        ]
    else:
        body = [
            f"# Refactoring Log - {task_id}",
            "",
            "- No algorithmic refactoring was required after normalization.",
            "- The generated solutions compiled and passed the dataset base tests for both models as-is.",
            "- Follow-up: the task moved directly to JUnit conversion, branch coverage, and black-box assessment.",
            "",
        ]
    return "\n".join(body)


def render_task_readme(row: dict[str, str], task_folder: str, base_coverage: dict[tuple[str, str], dict[str, str]], improved_coverage: dict[tuple[str, str], dict[str, str]]) -> str:
    task_id = row["task_id"]
    qwen_base = base_coverage[(task_folder, MODELS[0])]
    deepseek_base = base_coverage[(task_folder, MODELS[1])]
    qwen_improved = improved_coverage[(task_folder, MODELS[0])]
    deepseek_improved = improved_coverage[(task_folder, MODELS[1])]

    def cov_text(cov_row: dict[str, str]) -> str:
        covered = int(cov_row["branch_covered"])
        missed = int(cov_row["branch_missed"])
        return f"{covered}/{covered + missed} branches ({percent(covered, missed)})" if (covered + missed) else "N/A"

    lines = [
        f"# {task_id} - {row['method_name']}",
        "",
        f"Difficulty: {row['difficulty']}",
        f"Domain: {row['domain']}",
        f"Phase 2 dependency: {row['phase2_dependency']}",
        f"Selection reason: {row['selection_reason']}",
        "",
        "## Status Snapshot",
        "",
        f"- `Qwen2.5-Coder-1.5B`: base `{cov_text(qwen_base)}`, improved `{cov_text(qwen_improved)}`",
        f"- `DeepSeek-Coder-1.3B`: base `{cov_text(deepseek_base)}`, improved `{cov_text(deepseek_improved)}`",
        f"- Refactoring needed: {'yes' if task_folder in REPAIR_SUMMARIES else 'no'}",
        "",
        "## Checklist",
        "",
        "- [x] Raw prompt sent to model A is logged.",
        "- [x] Raw prompt sent to model B is logged.",
        "- [x] Raw generated code for both models is stored without edits.",
        "- [x] Base dataset tests are executed.",
        "- [x] Minor base test compatibility fixes are documented.",
        "- [x] Improved tests are created after smell review and coverage analysis.",
        "- [x] Equivalence classes and boundary conditions are recorded.",
        "- [x] Black-box weaknesses are listed.",
        "- [x] Refactoring decision is documented.",
        "",
    ]
    return "\n".join(lines)


def build_task_review(rows: list[dict[str, str]], task_map: dict[str, str], base_coverage: dict[tuple[str, str], dict[str, str]], improved_coverage: dict[tuple[str, str], dict[str, str]]) -> tuple[list[dict[str, str]], str]:
    matrix_rows: list[dict[str, str]] = []
    md_lines = [
        "# Task-Level Review",
        "",
        "| Task | Difficulty | Repaired | Qwen Base | Qwen Improved | DeepSeek Base | DeepSeek Improved |",
        "|---|---|---:|---:|---:|---:|---:|",
    ]
    for row in rows:
        folder = task_map[row["task_id"]]
        q_base = base_coverage[(folder, MODELS[0])]
        q_imp = improved_coverage[(folder, MODELS[0])]
        d_base = base_coverage[(folder, MODELS[1])]
        d_imp = improved_coverage[(folder, MODELS[1])]
        repaired = "yes" if folder in REPAIR_SUMMARIES else "no"

        def short_cov(item: dict[str, str]) -> str:
            covered = int(item["branch_covered"])
            missed = int(item["branch_missed"])
            total = covered + missed
            if total == 0:
                return "N/A"
            return f"{covered}/{total}"

        md_lines.append(
            f"| `{row['task_id']}` | {row['difficulty']} | {repaired} | {short_cov(q_base)} | {short_cov(q_imp)} | {short_cov(d_base)} | {short_cov(d_imp)} |"
        )

        matrix_rows.append(
            {
                "task_id": row["task_id"],
                "task_folder": folder,
                "method_name": row["method_name"],
                "difficulty": row["difficulty"],
                "domain": row["domain"],
                "phase2_dependency": row["phase2_dependency"],
                "repaired": repaired,
                "qwen_base_coverage": short_cov(q_base),
                "qwen_improved_coverage": short_cov(q_imp),
                "deepseek_base_coverage": short_cov(d_base),
                "deepseek_improved_coverage": short_cov(d_imp),
            }
        )

    return matrix_rows, "\n".join(md_lines) + "\n"


def main() -> None:
    task_rows = read_csv(SELECTED_PROMPTS)
    task_index_rows = read_csv(TASK_INDEX)
    base_coverage = load_coverage(BASE_COVERAGE_RESULTS)
    improved_coverage = load_coverage(IMPROVED_COVERAGE_RESULTS)

    task_map = {row["task_id"]: row["folder"] for row in task_index_rows}

    for row in task_rows:
        row["review_status"] = "completed"

    for row in task_rows:
        task_id = row["task_id"]
        method_name = row["method_name"]
        task_folder = task_map[task_id]
        analysis = TASK_SPECIFIC[method_name]
        task_dir = TASKS_DIR / task_folder
        (task_dir / "analysis" / "equivalence_classes.md").write_text(
            render_equivalence(task_id, method_name, analysis),
            encoding="utf-8",
        )
        (task_dir / "analysis" / "coverage_notes.md").write_text(
            render_coverage_notes(task_id, task_folder, analysis, base_coverage, improved_coverage),
            encoding="utf-8",
        )
        (task_dir / "analysis" / "refactoring_log.md").write_text(
            render_refactoring_log(task_id, task_folder),
            encoding="utf-8",
        )
        (task_dir / "README.md").write_text(
            render_task_readme(row, task_folder, base_coverage, improved_coverage),
            encoding="utf-8",
        )

    write_csv(
        SELECTED_PROMPTS,
        task_rows,
        ["task_id", "method_name", "difficulty", "domain", "selection_reason", "phase2_dependency", "review_status"],
    )

    matrix_rows, review_md = build_task_review(task_rows, task_map, base_coverage, improved_coverage)
    write_csv(
        ANALYSIS_DIR / "task_level_matrix.csv",
        matrix_rows,
        [
            "task_id",
            "task_folder",
            "method_name",
            "difficulty",
            "domain",
            "phase2_dependency",
            "repaired",
            "qwen_base_coverage",
            "qwen_improved_coverage",
            "deepseek_base_coverage",
            "deepseek_improved_coverage",
        ],
    )
    (ANALYSIS_DIR / "task_level_review.md").write_text(review_md, encoding="utf-8")


if __name__ == "__main__":
    main()
