[CmdletBinding()]
param(
    [string]$BaseResults = "..\\results\\base_test_results.csv",
    [string]$CoverageResults = "..\\results\\base_coverage_results.csv",
    [string]$SummaryCsv = "..\\results\\phase1_summary.csv",
    [string]$SummaryMarkdown = "..\\docs\\analysis\\phase1_summary.md"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$pythonScript = Join-Path $PSScriptRoot "python\\summarize_phase1_results.py"

python $pythonScript `
    --base-results ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $BaseResults))) `
    --coverage-results ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $CoverageResults))) `
    --summary-csv ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $SummaryCsv))) `
    --summary-markdown ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $SummaryMarkdown)))

