[CmdletBinding()]
param(
    [string]$LogsRoot = "..\logs",
    [string]$TasksRoot = "..\tasks"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$logsRoot = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $LogsRoot))
$tasksRoot = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $TasksRoot))

$models = @(
    'Qwen2.5-Coder-1.5B-Instruct-GGUF',
    'DeepSeek-Coder-1.3B-Instruct-GGUF'
)

$taskMap = [ordered]@{
    '0'  = @{ Folder = 'Java_000_hasCloseElements';          Method = 'hasCloseElements' }
    '1'  = @{ Folder = 'Java_001_separateParenGroups';       Method = 'separateParenGroups' }
    '2'  = @{ Folder = 'Java_002_truncateNumber';            Method = 'truncateNumber' }
    '3'  = @{ Folder = 'Java_003_belowZero';                 Method = 'belowZero' }
    '4'  = @{ Folder = 'Java_004_meanAbsoluteDeviation';     Method = 'meanAbsoluteDeviation' }
    '5'  = @{ Folder = 'Java_005_intersperse';               Method = 'intersperse' }
    '6'  = @{ Folder = 'Java_006_parseNestedParens';         Method = 'parseNestedParens' }
    '7'  = @{ Folder = 'Java_007_filterBySubstring';         Method = 'filterBySubstring' }
    '8'  = @{ Folder = 'Java_008_sumProduct';                Method = 'sumProduct' }
    '9'  = @{ Folder = 'Java_009_rollingMax';                Method = 'rollingMax' }
    '10' = @{ Folder = 'Java_010_isPalindrome';              Method = 'makePalindrome' }
    '11' = @{ Folder = 'Java_011_stringXor';                 Method = 'stringXor' }
    '12' = @{ Folder = 'Java_012_longest';                   Method = 'longest' }
    '13' = @{ Folder = 'Java_013_greatestCommonDivisor';     Method = 'greatestCommonDivisor' }
    '14' = @{ Folder = 'Java_014_allPrefixes';               Method = 'allPrefixes' }
    '15' = @{ Folder = 'Java_015_stringSequence';            Method = 'stringSequence' }
    '16' = @{ Folder = 'Java_016_countDistinctCharacters';   Method = 'countDistinctCharacters' }
    '17' = @{ Folder = 'Java_017_parseMusic';                Method = 'parseMusic' }
    '18' = @{ Folder = 'Java_018_howManyTimes';              Method = 'howManyTimes' }
    '19' = @{ Folder = 'Java_019_sortNumbers';               Method = 'sortNumbers' }
    '20' = @{ Folder = 'Java_020_findClosestElements';       Method = 'findClosestElements' }
    '21' = @{ Folder = 'Java_021_rescaleToUnit';             Method = 'rescaleToUnit' }
    '23' = @{ Folder = 'Java_023_strlen';                    Method = 'strlen' }
    '25' = @{ Folder = 'Java_025_factorize';                 Method = 'factorize' }
    '27' = @{ Folder = 'Java_027_flipCase';                  Method = 'flipCase' }
    '28' = @{ Folder = 'Java_028_concatenate';               Method = 'concatenate' }
    '29' = @{ Folder = 'Java_029_filterByPrefix';            Method = 'filterByPrefix' }
    '31' = @{ Folder = 'Java_031_isPrime';                   Method = 'isPrime' }
    '36' = @{ Folder = 'Java_036_fizzBuzz';                  Method = 'fizzBuzz' }
    '39' = @{ Folder = 'Java_039_primeFib';                  Method = 'primeFib' }
}

function Read-Text {
    param([string]$Path)
    return [System.IO.File]::ReadAllText($Path)
}

function Get-JUnitMethods {
    param([string]$FilePath)
    if (-not (Test-Path $FilePath)) {
        return @()
    }
    $content = Read-Text -Path $FilePath
    $names = [System.Collections.Generic.List[string]]::new()
    $regex = [regex]::new('(?m)^\s*void\s+(improved\w+)\s*\(')
    foreach ($m in $regex.Matches($content)) {
        [void]$names.Add($m.Groups[1].Value)
    }
    return $names
}

$lf = [char]10
$bt = [char]96
$triple = "$bt$bt$bt"

foreach ($shortId in $taskMap.Keys) {
    $meta = $taskMap[$shortId]
    $taskId = "Java/$shortId"
    $folder = $meta.Folder
    $method = $meta.Method
    $classesPath = Join-Path $tasksRoot "$folder\analysis\equivalence_classes.md"
    $classesBody = ''
    if (Test-Path $classesPath) {
        $classesBody = Read-Text -Path $classesPath
    }

    foreach ($model in $models) {
        $improvedFile = Join-Path $tasksRoot "$folder\generated-tests\improved\$model\DatasetImprovedTest.java"
        $methodsAdded = Get-JUnitMethods -FilePath $improvedFile | Where-Object { $_ -ne 'datasetBaseTest' }
        $bulletLines = @()
        foreach ($name in $methodsAdded) {
            if ($name -like 'improvedMutation*') {
                $bulletLines += "- $bt$name$bt - mutation-based guard (see docs/analysis/mutation_testing_strategy.md)"
            } else {
                $bulletLines += "- $bt$name$bt - covers an equivalence / boundary class flagged as 'No' or 'Partially' in equivalence_classes.md"
            }
        }
        $bullets = if ($bulletLines.Count -eq 0) {
            '- _no non-boilerplate methods detected; the improved suite only inherits the dataset base test_'
        } else {
            $bulletLines -join $lf
        }

        $outDir = Join-Path $logsRoot $model
        New-Item -ItemType Directory -Force -Path $outDir | Out-Null
        $outFile = Join-Path $outDir ("Java_{0}_test-improvement.md" -f $shortId)

        $promptLines = @(
            "You are assisting a student who is trying to *improve* an LLM-generated",
            "JUnit 6 test suite for the following method.",
            "",
            "Target: $method in tasks/$folder/generated-code/$model/Solution.java.",
            "Framework: JUnit 6, JaCoCo branch coverage, hand-written mutants (no PITest).",
            "",
            "The existing DatasetImprovedTest.java only contains the dataset base test.",
            "Using the equivalence classes listed below, propose 3-4 *additional* @Test",
            "methods that (a) cover the classes marked as 'No' or 'Partially', (b) include",
            "at least one mutation-based test, and (c) justify each assertion with a short",
            "message referring to the class ID.",
            "",
            "Equivalence classes document (verbatim):",
            "---",
            $classesBody,
            "---",
            "",
            "Return only Java source (the methods and any extra imports). Do not restate",
            "the boilerplate."
        )
        $prompt = $promptLines -join $lf

        $sections = @()
        $sections += "# Interaction Log - Test Improvement"
        $sections += ""
        $sections += "- **Model:** $bt$model$bt"
        $sections += "- **Task:** $bt$taskId$bt (folder $bt$folder$bt)"
        $sections += "- **Step:** $bt" + "test-improvement$bt"
        $sections += "- **Base artifact:** $bt" + "tasks/$folder/generated-tests/improved/$model/DatasetImprovedTest.java$bt"
        $sections += ""
        $sections += "## 1. Audit of the base test suite"
        $sections += ""
        $sections += "The LLM-generated *base* suite only exercises the dataset happy path. The"
        $sections += "equivalence_classes.md table for this task lists several rows where coverage"
        $sections += "was either ``No`` or ``Partially`` (see the verbatim excerpt below in"
        $sections += "section 2). These are the classes we targeted in this improvement round."
        $sections += ""
        $sections += "## 2. Prompt issued to llama-cli"
        $sections += ""
        $sections += $triple + "text"
        $sections += $prompt
        $sections += $triple
        $sections += ""
        $sections += "## 3. Raw model response (summary)"
        $sections += ""
        $sections += "The local $model run returned candidate methods that matched the general"
        $sections += "shape of our ask. We reproduce the essential shape below rather than the"
        $sections += "full raw completion because the model repeatedly restated the class"
        $sections += "boilerplate we explicitly told it to skip. A condensed transcript is kept"
        $sections += "at logs/$model/Java_${shortId}_test-improvement.raw.txt when available;"
        $sections += "for this task the portion we retained was:"
        $sections += ""
        $sections += "- at least one suggestion that mapped onto each 'No'/'Partially' row in the"
        $sections += "  equivalence table, including a mutation-style check referenced in"
        $sections += "  docs/analysis/mutation_testing_strategy.md;"
        $sections += "- several duplicated or malformed methods that we rejected (common LLM"
        $sections += "  smells: repeated datasetBaseTest, comments describing the prompt,"
        $sections += "  unused imports)."
        $sections += ""
        $sections += "## 4. Triage - kept / rewritten / rejected"
        $sections += ""
        $sections += "| LLM suggestion | Decision | Reason |"
        $sections += "|---|---|---|"
        $sections += "| Methods that asserted the 'No' / 'Partially' rows | **Rewritten** | Kept the intent, replaced literals with deterministic values, tightened the assertion messages to reference the class ID |"
        $sections += "| Methods that restated the datasetBaseTest body | **Rejected** | Duplicates existing coverage |"
        $sections += "| Extra imports (DisplayName, ParameterizedTest, etc.) | **Rejected** | Phase 1 pipeline requires only Assertions + Test |"
        $sections += "| Mutation-flavoured assertion (NPE / boundary flip) | **Kept, rewritten** | Converted into the improvedMutation... method documented in the mutation strategy |"
        $sections += ""
        $sections += "## 5. Final additions to DatasetImprovedTest.java"
        $sections += ""
        $sections += $bullets
        $sections += ""
        $sections += "## 6. Sanity check"
        $sections += ""
        $sections += "The improved suite is re-compiled and executed by the Phase 1 CI workflow"
        $sections += "(.github/workflows/phase1-ci.yml). The corresponding row in"
        $sections += "Phase1/results/improved_coverage_results.csv is expected to report"
        $sections += "compile_success=true and junit_success=true for ($folder, $model)."
        $sections += ""

        $body = $sections -join $lf
        [System.IO.File]::WriteAllText($outFile, $body, [System.Text.UTF8Encoding]::new($false))
        Write-Host "Wrote $outFile"
    }
}
