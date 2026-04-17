[CmdletBinding()]
param(
    [string]$DatasetPath = "..\\data\\raw\\humaneval_java.jsonl.gz",
    [string]$SelectionCsv = "..\\data\\selected_prompts.csv",
    [string]$OutputRoot = "..\\tasks",
    [string[]]$ModelNames = @(
        "Qwen2.5-Coder-7B-Instruct",
        "DeepSeek-Coder-V2-Lite-Instruct"
    ),
    [switch]$IncludeCanonicalSolution
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
$scriptRoot = $PSScriptRoot

function Resolve-ScriptRelativePath {
    param([string]$Path)
    return [System.IO.Path]::GetFullPath((Join-Path $scriptRoot $Path))
}

function Read-GzipJsonlTasks {
    param([string]$Path)

    $tasks = @{}
    $fileStream = [System.IO.File]::OpenRead($Path)
    try {
        $gzipStream = New-Object System.IO.Compression.GzipStream(
            $fileStream,
            [System.IO.Compression.CompressionMode]::Decompress
        )
        try {
            $reader = New-Object System.IO.StreamReader($gzipStream)
            try {
                while (($line = $reader.ReadLine()) -ne $null) {
                    if ([string]::IsNullOrWhiteSpace($line)) {
                        continue
                    }

                    $task = $line | ConvertFrom-Json
                    $tasks[$task.task_id] = $task
                }
            }
            finally {
                $reader.Close()
            }
        }
        finally {
            $gzipStream.Close()
        }
    }
    finally {
        $fileStream.Close()
    }

    return $tasks
}

function Get-MethodName {
    param([string]$Declaration)

    $match = [regex]::Match($Declaration, "public\s+[\w<>\[\], ?]+\s+(\w+)\s*\(")
    if ($match.Success) {
        return $match.Groups[1].Value
    }

    return "unknownMethod"
}

function Get-TaskFolderName {
    param(
        [string]$TaskId,
        [string]$MethodName
    )

    if ($TaskId -match "^Java/(\d+)$") {
        $number = [int]$Matches[1]
        return ("Java_{0:D3}_{1}" -f $number, $MethodName)
    }

    return ($TaskId -replace "[^A-Za-z0-9_\\-]", "_")
}

function Write-TextFile {
    param(
        [string]$Path,
        [string]$Content
    )

    $directory = Split-Path -Parent $Path
    New-Item -ItemType Directory -Force -Path $directory | Out-Null
    [System.IO.File]::WriteAllText($Path, $Content, [System.Text.UTF8Encoding]::new($false))
}

$datasetFile = Resolve-ScriptRelativePath $DatasetPath
$selectionFile = Resolve-ScriptRelativePath $SelectionCsv
$outputDirectory = Resolve-ScriptRelativePath $OutputRoot

if (-not (Test-Path $datasetFile)) {
    throw "Dataset file not found: $datasetFile. Run Download-HumanEvalJavaDataset.ps1 first."
}

if (-not (Test-Path $selectionFile)) {
    throw "Selection CSV not found: $selectionFile"
}

$selectedRows = Import-Csv -Path $selectionFile
$allTasks = Read-GzipJsonlTasks -Path $datasetFile

New-Item -ItemType Directory -Force -Path $outputDirectory | Out-Null

$indexRows = @()

foreach ($row in $selectedRows) {
    if (-not $allTasks.ContainsKey($row.task_id)) {
        Write-Warning "Skipping missing task: $($row.task_id)"
        continue
    }

    $task = $allTasks[$row.task_id]
    $methodName = if ([string]::IsNullOrWhiteSpace($row.method_name)) {
        Get-MethodName -Declaration $task.declaration
    }
    else {
        $row.method_name
    }

    $folderName = Get-TaskFolderName -TaskId $row.task_id -MethodName $methodName
    $taskRoot = Join-Path $outputDirectory $folderName
    $datasetRoot = Join-Path $taskRoot "dataset"
    $analysisRoot = Join-Path $taskRoot "analysis"
    $generatedCodeRoot = Join-Path $taskRoot "generated-code"
    $generatedTestsRoot = Join-Path $taskRoot "generated-tests"

    New-Item -ItemType Directory -Force -Path $datasetRoot, $analysisRoot, $generatedCodeRoot, $generatedTestsRoot | Out-Null

    Write-TextFile -Path (Join-Path $datasetRoot "prompt.txt") -Content $task.prompt
    Write-TextFile -Path (Join-Path $datasetRoot "declaration.java") -Content $task.declaration
    Write-TextFile -Path (Join-Path $datasetRoot "example_test_dataset.java") -Content $task.example_test
    Write-TextFile -Path (Join-Path $datasetRoot "base_test_dataset.java") -Content $task.test

    if ($IncludeCanonicalSolution) {
        Write-TextFile -Path (Join-Path $datasetRoot "canonical_solution.java") -Content $task.canonical_solution
    }

    $taskReadme = @"
# $($row.task_id) - $methodName

Difficulty: $($row.difficulty)
Domain: $($row.domain)
Phase 2 dependency: $($row.phase2_dependency)
Selection reason: $($row.selection_reason)

## Checklist

- [ ] Raw prompt sent to model A is logged.
- [ ] Raw prompt sent to model B is logged.
- [ ] Raw generated code for both models is stored without edits.
- [ ] Base dataset tests are executed.
- [ ] Minor base test compatibility fixes are documented.
- [ ] Improved tests are created after smell review and coverage analysis.
- [ ] Equivalence classes and boundary conditions are recorded.
- [ ] Black-box weaknesses are listed.
- [ ] Refactoring decision is documented.

"@
    Write-TextFile -Path (Join-Path $taskRoot "README.md") -Content $taskReadme

    $equivalenceTemplate = @"
# Equivalence Partitioning - $($row.task_id)

Method: `$methodName`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 |  |  |  |  |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 |  |  |  |  |

## Boundary Conditions

| Boundary ID | Description | Candidate Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 |  |  |  |  |

"@
    Write-TextFile -Path (Join-Path $analysisRoot "equivalence_classes.md") -Content $equivalenceTemplate

    $coverageTemplate = @"
# Coverage Notes - $($row.task_id)

## Base Tests

- Branch coverage:
- Missed branches:
- Smells observed:

## Improved Tests

- Branch coverage:
- Newly covered branches:
- Remaining gaps:

"@
    Write-TextFile -Path (Join-Path $analysisRoot "coverage_notes.md") -Content $coverageTemplate

    $refactorTemplate = @"
# Refactoring Log - $($row.task_id)

## Trigger

- Why refactoring was needed:

## Evidence

- Failing test:
- Missed branch:
- Black-box gap:

## Follow-up

- New prompt used:
- Code changes made by model:
- Retest result:

"@
    Write-TextFile -Path (Join-Path $analysisRoot "refactoring_log.md") -Content $refactorTemplate

    foreach ($modelName in $ModelNames) {
        $modelRoot = Join-Path $generatedCodeRoot $modelName
        $modelBaseTestsRoot = Join-Path (Join-Path $generatedTestsRoot "base") $modelName
        $modelImprovedTestsRoot = Join-Path (Join-Path $generatedTestsRoot "improved") $modelName

        New-Item -ItemType Directory -Force -Path $modelRoot, $modelBaseTestsRoot, $modelImprovedTestsRoot | Out-Null

        Write-TextFile -Path (Join-Path $modelRoot "README.md") -Content "# Store raw generated Java solution here as `Solution.java`."
        Write-TextFile -Path (Join-Path $modelBaseTestsRoot "README.md") -Content "# Store the adapted base JUnit tests for this model here."
        Write-TextFile -Path (Join-Path $modelImprovedTestsRoot "README.md") -Content "# Store improved JUnit tests after smell and coverage analysis here."
    }

    $indexRows += [pscustomobject]@{
        task_id = $row.task_id
        method_name = $methodName
        difficulty = $row.difficulty
        domain = $row.domain
        folder = $folderName
    }
}

$indexRows |
    Sort-Object task_id |
    Export-Csv -Path (Join-Path $outputDirectory "task_index.csv") -NoTypeInformation -Encoding UTF8

Write-Host "Phase 1 task workspace generated under $outputDirectory"
