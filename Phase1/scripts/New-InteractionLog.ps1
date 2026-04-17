[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)]
    [string]$ModelName,

    [Parameter(Mandatory = $true)]
    [string]$TaskId,

    [Parameter(Mandatory = $true)]
    [string]$Step,

    [string]$OutputRoot = "..\\logs"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$scriptDir = $PSScriptRoot
$resolvedRoot = [System.IO.Path]::GetFullPath((Join-Path $scriptDir $OutputRoot))
$modelRoot = Join-Path $resolvedRoot $ModelName

New-Item -ItemType Directory -Force -Path $modelRoot | Out-Null

$safeTaskId = $TaskId -replace "[^A-Za-z0-9_\\-]", "_"
$safeStep = $Step -replace "[^A-Za-z0-9_\\-]", "_"
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$outputFile = Join-Path $modelRoot "${timestamp}_${safeTaskId}_${safeStep}.md"

$template = @"
# Interaction Log

- Model: $ModelName
- Task: $TaskId
- Step: $Step
- Timestamp: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")

## Full Prompt

Paste the exact prompt here.

## Raw Model Response

Paste the exact response here.

## Usage Note

State how the output was used, or why it was modified/rejected.

"@

[System.IO.File]::WriteAllText($outputFile, $template, [System.Text.UTF8Encoding]::new($false))
Write-Host "Created log template at $outputFile"
