[CmdletBinding()]
param(
    [string]$OutputPath = "..\\data\\raw\\humaneval_java.jsonl.gz"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$scriptDir = $PSScriptRoot
$resolvedOutput = Join-Path $scriptDir $OutputPath
$outputDirectory = Split-Path -Parent $resolvedOutput

New-Item -ItemType Directory -Force -Path $outputDirectory | Out-Null

$datasetUrl = "https://raw.githubusercontent.com/zai-org/CodeGeeX/main/codegeex/benchmark/humaneval-x/java/data/humaneval_java.jsonl.gz"

Write-Host "Downloading HumanEval-X Java dataset..."
Invoke-WebRequest -Uri $datasetUrl -OutFile $resolvedOutput
Write-Host "Saved dataset to $resolvedOutput"
