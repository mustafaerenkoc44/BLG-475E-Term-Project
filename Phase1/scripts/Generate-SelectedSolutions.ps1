[CmdletBinding()]
param(
    [string]$DatasetPath = "..\\data\\raw\\humaneval_java.jsonl.gz",
    [string]$SelectionCsv = "..\\data\\selected_prompts.csv",
    [string]$TasksRoot = "..\\tasks",
    [int]$MaxTokens = 384,
    [double]$Temperature = 0.2,
    [string[]]$TaskIds = @(),
    [switch]$SkipExisting
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "Phase1.Common.ps1")

Assert-Phase1Toolchain

$pythonScript = Join-Path $PSScriptRoot "python\\generate_selected_solutions.py"
if (-not (Test-Path $pythonScript)) {
    throw "Missing generator script: $pythonScript"
}

$toolchain = Get-Phase1Toolchain

python $pythonScript `
    --dataset ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $DatasetPath))) `
    --selection ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $SelectionCsv))) `
    --tasks-root ([System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot $TasksRoot))) `
    --llama-completion $toolchain.LlamaCompletion `
    --models-root (Join-Path $toolchain.ToolsRoot "models") `
    --max-tokens $MaxTokens `
    --temperature $Temperature `
    $(foreach ($taskId in $TaskIds) { "--task-id"; $taskId }) `
    $(if ($SkipExisting) { "--skip-existing" })
