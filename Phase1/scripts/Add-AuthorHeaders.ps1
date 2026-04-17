[CmdletBinding()]
param(
    [string]$TasksRoot = "..\\tasks"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$pythonScript = Join-Path $PSScriptRoot "python\\add_author_headers.py"

python $pythonScript `
    --tasks-root ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $TasksRoot)))

