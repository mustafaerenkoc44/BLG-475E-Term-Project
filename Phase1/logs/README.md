# Logs

Every interaction with each LLM should be stored here.

Minimum required content per log:

- full prompt
- full raw response
- brief note describing how the output was used or why it was modified

You can create log stubs with:

`./scripts/New-InteractionLog.ps1 -ModelName "<model>" -TaskId "Java/18" -Step "code-generation"`

