$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptDir
$pythonScript = Join-Path $scriptDir "python\finalize_phase1_analysis.py"

python $pythonScript

Write-Host "Phase 1 task-level analysis finalized."
