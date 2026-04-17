[CmdletBinding()]
param(
    [string]$DatasetPath = "..\\data\\raw\\humaneval_java.jsonl.gz"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "Phase1.Common.ps1")

$buildScript = Join-Path $PSScriptRoot "Build-Phase1Workspace.ps1"
& $buildScript -DatasetPath $DatasetPath

