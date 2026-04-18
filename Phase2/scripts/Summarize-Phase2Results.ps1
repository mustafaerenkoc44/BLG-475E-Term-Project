[CmdletBinding()]
param(
    [string]$ResultsDir = "..\results"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
$culture = [System.Globalization.CultureInfo]::InvariantCulture

$resultsDir = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $ResultsDir))
$csvPath = Join-Path $resultsDir "prompt_comparison_results.csv"
$outPath = Join-Path $resultsDir "prompt_comparison_summary.md"

if (-not (Test-Path $csvPath)) {
    throw "Missing results CSV: $csvPath"
}

$rows = Import-Csv -Path $csvPath

$tableLines = @(
    "# Phase 2 Prompt Comparison Summary",
    "",
    "| Strategy | Model | Compiled | JUnit | Tests Found | Tests Failed | Pass Rate | Branch Covered | Branch Missed | Branch Coverage | Notes |",
    "|---|---|---|---|---:|---:|---:|---:|---:|---:|---|"
)

foreach ($row in $rows) {
    $testsFound = [int]$row.tests_found
    $testsFailed = [int]$row.tests_failed
    $branchesCovered = [int]$row.branch_covered
    $branchesMissed = [int]$row.branch_missed

    $passRate = if ($testsFound -gt 0) {
        [string]::Format(
            $culture,
            "{0:0.00}%",
            (100.0 * ($testsFound - $testsFailed) / [double]$testsFound))
    } else {
        "n/a"
    }

    $branchRate = if (($branchesCovered + $branchesMissed) -gt 0) {
        [string]::Format(
            $culture,
            "{0:0.00}%",
            (100.0 * $branchesCovered / [double]($branchesCovered + $branchesMissed)))
    } else {
        "n/a"
    }

    $tableLines += ("| {0} | {1} | {2} | {3} | {4} | {5} | {6} | {7} | {8} | {9} | {10} |" -f
        $row.strategy,
        $row.model_name,
        $row.compile_success,
        $row.junit_success,
        $testsFound,
        $testsFailed,
        $passRate,
        $branchesCovered,
        $branchesMissed,
        $branchRate,
        $row.notes)
}

[System.IO.File]::WriteAllLines($outPath, $tableLines)
Write-Host "Wrote $outPath"
