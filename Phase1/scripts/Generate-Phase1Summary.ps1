[CmdletBinding()]
param(
    [string]$ResultsDir = "..\results"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$resultsDir = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $ResultsDir))
$baseCsv = Join-Path $resultsDir "base_coverage_results.csv"
$impCsv = Join-Path $resultsDir "improved_coverage_results.csv"
$outCsv = Join-Path $resultsDir "phase1_summary.csv"

if (-not (Test-Path $baseCsv)) { throw "Missing base CSV: $baseCsv" }
if (-not (Test-Path $impCsv))  { throw "Missing improved CSV: $impCsv" }

$base = Import-Csv $baseCsv
$imp  = Import-Csv $impCsv

$index = @{}
foreach ($row in $imp) {
    $key = "$($row.task_folder)::$($row.model_name)"
    $index[$key] = $row
}

$rows = foreach ($b in $base) {
    $key = "$($b.task_folder)::$($b.model_name)"
    $i = $index[$key]
    [pscustomobject]@{
        task_folder             = $b.task_folder
        model_name              = $b.model_name
        base_compile_success    = $b.compile_success
        base_junit_success      = $b.junit_success
        base_branch_covered     = $b.branch_covered
        base_branch_missed      = $b.branch_missed
        base_notes              = $b.notes
        improved_compile_success = if ($i) { $i.compile_success } else { 'missing' }
        improved_junit_success   = if ($i) { $i.junit_success }   else { 'missing' }
        improved_branch_covered  = if ($i) { $i.branch_covered }  else { '0' }
        improved_branch_missed   = if ($i) { $i.branch_missed }   else { '0' }
        improved_notes           = if ($i) { $i.notes }           else { 'missing' }
    }
}

$rows | Export-Csv -NoTypeInformation -Encoding UTF8 -Path $outCsv

Write-Host "Wrote $outCsv"
Write-Host ("Rows: {0}" -f $rows.Count)

$baseQ = $base | Where-Object { $_.model_name -like 'Qwen*' }
$baseD = $base | Where-Object { $_.model_name -like 'DeepSeek*' }
$impQ  = $imp  | Where-Object { $_.model_name -like 'Qwen*' }
$impD  = $imp  | Where-Object { $_.model_name -like 'DeepSeek*' }

function Report-Coverage($label, $rows) {
    $cov  = ($rows | Measure-Object branch_covered -Sum).Sum
    $miss = ($rows | Measure-Object branch_missed -Sum).Sum
    $total = $cov + $miss
    if ($total -eq 0) {
        Write-Host ("{0,-40} covered=0/0 -> n/a" -f $label)
    } else {
        Write-Host ("{0,-40} covered={1}/{2} -> {3:P2}" -f $label, $cov, $total, ($cov / $total))
    }
    $fails = @($rows | Where-Object { $_.compile_success -ne 'true' -or $_.junit_success -ne 'true' })
    if ($fails.Count -gt 0) {
        $summary = ($fails | ForEach-Object { "$($_.task_folder) ($($_.notes))" } | Sort-Object | Get-Unique) -join ', '
        Write-Host ("  FAILURES: {0}" -f $summary)
    }
}

Report-Coverage "Base     Qwen"     $baseQ
Report-Coverage "Base     DeepSeek" $baseD
Report-Coverage "Improved Qwen"     $impQ
Report-Coverage "Improved DeepSeek" $impD
