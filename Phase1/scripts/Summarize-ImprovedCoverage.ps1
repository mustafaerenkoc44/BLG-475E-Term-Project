[CmdletBinding()]
param(
    [string]$BaseCoverage = "..\\results\\base_coverage_results.csv",
    [string]$ImprovedCoverage = "..\\results\\improved_coverage_results.csv",
    [string]$OutputMarkdown = "..\\docs\\analysis\\improved_coverage_summary.md"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$pythonScript = Join-Path $PSScriptRoot "python\\summarize_improved_coverage.py"

python $pythonScript `
    --base-coverage ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $BaseCoverage))) `
    --improved-coverage ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $ImprovedCoverage))) `
    --output-markdown ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $OutputMarkdown)))

