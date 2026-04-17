[CmdletBinding()]
param(
    [string]$TasksRoot = "..\\tasks",
    [string]$ResultsPath = "..\\results\\base_coverage_results.csv",
    [int]$CompileTimeoutSeconds = 20,
    [int]$RunTimeoutSeconds = 20
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "Phase1.Common.ps1")

Assert-Phase1Toolchain
$toolchain = Get-Phase1Toolchain
$pythonScript = Join-Path $PSScriptRoot "python\\run_base_coverage.py"

python $pythonScript `
    --tasks-root ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $TasksRoot))) `
    --results ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $ResultsPath))) `
    --java $toolchain.Java `
    --javac $toolchain.Javac `
    --junit-console $toolchain.JunitConsoleJar `
    --jacoco-agent $toolchain.JacocoAgentJar `
    --jacoco-cli $toolchain.JacocoCliJar `
    --compile-timeout $CompileTimeoutSeconds `
    --run-timeout $RunTimeoutSeconds

