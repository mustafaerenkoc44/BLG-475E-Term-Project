[CmdletBinding()]
param()

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$repoRoot = Split-Path -Parent $PSScriptRoot
$scriptPath = Join-Path $PSScriptRoot "scripts\build_reports.py"

if (-not (Test-Path $scriptPath)) {
    throw "Missing report builder script: $scriptPath"
}

python $scriptPath
