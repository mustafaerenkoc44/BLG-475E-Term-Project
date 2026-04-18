param(
    [Parameter(Mandatory = $true)]
    [string]$StudentNames,

    [Parameter(Mandatory = $true)]
    [string]$StudentIds,

    [string]$RepoUrl,

    [string]$GroupRoles
)

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$javaFiles = Get-ChildItem -Path (Join-Path $projectRoot "tasks") -Recurse -Include *.java -File

foreach ($file in $javaFiles) {
    $content = Get-Content -Path $file.FullName -Raw
    $content = $content.Replace("<student_name>", $StudentNames)
    $content = $content.Replace("<student_id>", $StudentIds)
    Set-Content -Path $file.FullName -Value $content -Encoding UTF8
}

$reportDraft = Join-Path $projectRoot "docs\report\phase1_report_draft.md"
if (Test-Path $reportDraft) {
    $reportContent = Get-Content -Path $reportDraft -Raw
    if ($RepoUrl) {
        $reportContent = $reportContent.Replace("<repo_url>", $RepoUrl)
    }
    if ($GroupRoles) {
        $reportContent = $reportContent.Replace("<group_roles>", $GroupRoles)
    }
    Set-Content -Path $reportDraft -Value $reportContent -Encoding UTF8
}

Write-Host "Submission metadata updated across Java headers and the Phase 1 report draft."
