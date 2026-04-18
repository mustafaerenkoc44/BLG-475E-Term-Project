[CmdletBinding()]
param(
    [string]$ResultsDir = "..\results",
    [int]$CompileTimeoutSeconds = 30,
    [int]$RunTimeoutSeconds = 30
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$phase2Root = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot ".."))
$repoRoot = [System.IO.Path]::GetFullPath((Join-Path $phase2Root ".."))
$resultsDir = [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $ResultsDir))

$java = Join-Path $repoRoot ".tools\jdk21\bin\java.exe"
$javac = Join-Path $repoRoot ".tools\jdk21\bin\javac.exe"
$junit = Join-Path $repoRoot ".tools\junit-platform-console-standalone-6.0.3.jar"
$jacocoAgent = Join-Path $repoRoot ".tools\org.jacoco.agent-0.8.12-runtime.jar"
$jacocoCli = Join-Path $repoRoot ".tools\org.jacoco.cli-0.8.12-nodeps.jar"
$scriptPath = Join-Path $phase2Root "scripts\python\run_phase2_prompt_comparison.py"
$resultsCsv = Join-Path $resultsDir "prompt_comparison_results.csv"

foreach ($required in @($java, $javac, $junit, $jacocoAgent, $jacocoCli, $scriptPath)) {
    if (-not (Test-Path $required)) {
        throw "Required file is missing: $required"
    }
}

New-Item -ItemType Directory -Force -Path $resultsDir | Out-Null

python $scriptPath `
    --phase2-root $phase2Root `
    --results $resultsCsv `
    --java $java `
    --javac $javac `
    --junit-console $junit `
    --jacoco-agent $jacocoAgent `
    --jacoco-cli $jacocoCli `
    --compile-timeout $CompileTimeoutSeconds `
    --run-timeout $RunTimeoutSeconds

Write-Host "Wrote $resultsCsv"
