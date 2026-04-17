[CmdletBinding()]
param()

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

. (Join-Path $PSScriptRoot "Phase1.Common.ps1")

$toolsRoot = Ensure-Directory (Get-ToolsRoot)
$downloadsRoot = Ensure-Directory (Join-Path $toolsRoot "downloads")

$jdkZip = Join-Path $downloadsRoot "OpenJDK21U-jdk_x64_windows_hotspot_latest.zip"
$mavenZip = Join-Path $downloadsRoot "apache-maven-3.9.11-bin.zip"
$llamaZip = Join-Path $downloadsRoot "llama-b8827-bin-win-cpu-x64.zip"

$jdkUrl = "https://api.adoptium.net/v3/binary/latest/21/ga/windows/x64/jdk/hotspot/normal/eclipse"
$mavenUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.11/apache-maven-3.9.11-bin.zip"
$llamaUrl = "https://github.com/ggml-org/llama.cpp/releases/download/b8827/llama-b8827-bin-win-cpu-x64.zip"
$junitUrl = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.0.3/junit-platform-console-standalone-6.0.3.jar"
$jacocoAgentUrl = "https://repo1.maven.org/maven2/org/jacoco/org.jacoco.agent/0.8.12/org.jacoco.agent-0.8.12-runtime.jar"
$jacocoCliUrl = "https://repo1.maven.org/maven2/org/jacoco/org.jacoco.cli/0.8.12/org.jacoco.cli-0.8.12-nodeps.jar"

$toolchain = Get-Phase1Toolchain

Write-Host "Downloading workspace-local JDK..."
Invoke-WebRequest -Uri $jdkUrl -OutFile $jdkZip
Expand-ZipClean -ZipPath $jdkZip -DestinationPath $toolchain.JdkRoot

Write-Host "Downloading workspace-local Maven..."
Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenZip
Expand-ZipClean -ZipPath $mavenZip -DestinationPath (Join-Path $toolsRoot "maven")

Write-Host "Downloading workspace-local llama.cpp..."
Invoke-WebRequest -Uri $llamaUrl -OutFile $llamaZip
Expand-ZipClean -ZipPath $llamaZip -DestinationPath $toolchain.LlamaRoot

Write-Host "Downloading JUnit console..."
Invoke-WebRequest -Uri $junitUrl -OutFile $toolchain.JunitConsoleJar

Write-Host "Downloading JaCoCo agent..."
Invoke-WebRequest -Uri $jacocoAgentUrl -OutFile $toolchain.JacocoAgentJar

Write-Host "Downloading JaCoCo CLI..."
Invoke-WebRequest -Uri $jacocoCliUrl -OutFile $toolchain.JacocoCliJar

Write-Host "Phase1 toolchain is ready under $toolsRoot"

