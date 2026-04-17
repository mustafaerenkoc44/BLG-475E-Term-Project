Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Get-Phase1Root {
    return [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot ".."))
}

function Get-WorkspaceRoot {
    return [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot "..\\.."))
}

function Get-ToolsRoot {
    return [System.IO.Path]::GetFullPath((Join-Path $PSScriptRoot "..\\..\\.tools"))
}

function Ensure-Directory {
    param([string]$Path)

    New-Item -ItemType Directory -Force -Path $Path | Out-Null
    return [System.IO.Path]::GetFullPath($Path)
}

function Get-OnlyChildDirectory {
    param([string]$Path)

    $dirs = @(Get-ChildItem -Path $Path -Directory)
    if ($dirs.Count -eq 1) {
        return $dirs[0].FullName
    }

    return $null
}

function Expand-ZipClean {
    param(
        [string]$ZipPath,
        [string]$DestinationPath
    )

    if (Test-Path $DestinationPath) {
        Remove-Item -Recurse -Force $DestinationPath
    }

    New-Item -ItemType Directory -Force -Path $DestinationPath | Out-Null
    Expand-Archive -Path $ZipPath -DestinationPath $DestinationPath -Force

    $singleChild = Get-OnlyChildDirectory -Path $DestinationPath
    if ($singleChild) {
        $temp = Join-Path (Split-Path -Parent $DestinationPath) ([System.IO.Path]::GetRandomFileName())
        Move-Item -LiteralPath $singleChild -Destination $temp
        Remove-Item -Recurse -Force $DestinationPath
        Move-Item -LiteralPath $temp -Destination $DestinationPath
    }
}

function Get-Phase1Toolchain {
    $toolsRoot = Get-ToolsRoot
    $jdkRoot = Join-Path $toolsRoot "jdk21"
    $junitJar = Join-Path $toolsRoot "junit-platform-console-standalone-6.0.3.jar"
    $jacocoAgent = Join-Path $toolsRoot "org.jacoco.agent-0.8.12-runtime.jar"
    $jacocoCli = Join-Path $toolsRoot "org.jacoco.cli-0.8.12-nodeps.jar"
    $llamaRoot = Join-Path $toolsRoot "llama.cpp"
    $llamaCli = Join-Path $llamaRoot "llama-cli.exe"
    $llamaCompletion = Join-Path $llamaRoot "llama-completion.exe"

    $javac = Join-Path $jdkRoot "bin\\javac.exe"
    $java = Join-Path $jdkRoot "bin\\java.exe"

    return [pscustomobject]@{
        ToolsRoot = $toolsRoot
        JdkRoot = $jdkRoot
        Java = $java
        Javac = $javac
        JunitConsoleJar = $junitJar
        JacocoAgentJar = $jacocoAgent
        JacocoCliJar = $jacocoCli
        LlamaRoot = $llamaRoot
        LlamaCli = $llamaCli
        LlamaCompletion = $llamaCompletion
    }
}

function Test-Phase1Toolchain {
    $toolchain = Get-Phase1Toolchain

    $checks = @(
        $toolchain.Java,
        $toolchain.Javac,
        $toolchain.JunitConsoleJar,
        $toolchain.JacocoAgentJar,
        $toolchain.JacocoCliJar,
        $toolchain.LlamaCli,
        $toolchain.LlamaCompletion
    )

    foreach ($path in $checks) {
        if (-not (Test-Path $path)) {
            return $false
        }
    }

    return $true
}

function Assert-Phase1Toolchain {
    if (-not (Test-Phase1Toolchain)) {
        throw "Phase1 toolchain is incomplete. Run Setup-Phase1Toolchain.ps1 first."
    }
}

function Write-Utf8NoBomFile {
    param(
        [string]$Path,
        [string]$Content
    )

    $directory = Split-Path -Parent $Path
    if ($directory) {
        New-Item -ItemType Directory -Force -Path $directory | Out-Null
    }

    [System.IO.File]::WriteAllText($Path, $Content, [System.Text.UTF8Encoding]::new($false))
}
