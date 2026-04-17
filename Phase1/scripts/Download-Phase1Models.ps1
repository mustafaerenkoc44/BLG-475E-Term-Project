[CmdletBinding()]
param()

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "Phase1.Common.ps1")

$modelsRoot = Ensure-Directory (Join-Path (Get-ToolsRoot) "models")

$modelDownloads = @(
    @{
        Name = "Qwen2.5-Coder-1.5B-Instruct-GGUF"
        File = "qwen2.5-coder-1.5b-instruct-q4_k_m.gguf"
        Url = "https://huggingface.co/Qwen/Qwen2.5-Coder-1.5B-Instruct-GGUF/resolve/main/qwen2.5-coder-1.5b-instruct-q4_k_m.gguf?download=true"
    },
    @{
        Name = "DeepSeek-Coder-1.3B-Instruct-GGUF"
        File = "deepseek-coder-1.3b-instruct.Q4_K_M.gguf"
        Url = "https://huggingface.co/TheBloke/deepseek-coder-1.3b-instruct-GGUF/resolve/main/deepseek-coder-1.3b-instruct.Q4_K_M.gguf?download=true"
    }
)

foreach ($item in $modelDownloads) {
    $modelDir = Ensure-Directory (Join-Path $modelsRoot $item.Name)
    $targetFile = Join-Path $modelDir $item.File
    if (Test-Path $targetFile) {
        Write-Host "Skipping existing model file: $targetFile"
        continue
    }

    Write-Host "Downloading $($item.Name)..."
    Invoke-WebRequest -Uri $item.Url -OutFile $targetFile
}

Write-Host "Model downloads completed under $modelsRoot"

