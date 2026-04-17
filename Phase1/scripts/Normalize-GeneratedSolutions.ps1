[CmdletBinding()]
param(
    [string]$TasksRoot = "..\\tasks"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$pythonScript = Join-Path $PSScriptRoot "python\\normalize_generated_solutions.py"

python $pythonScript `
    --tasks-root ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $TasksRoot)))

