# Equivalence Partitioning - Java/39

Method: `primeFib`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | First prime Fibonacci number. | `n=1` | Yes | Prompt example returns `2`. |
| V2 | Mid-range requested index. | `n=4` | Yes | Prompt example returns `13`. |
| V3 | Larger requested index. | `n=5` | Yes | Prompt example returns `89`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Non-positive index. | `n=0` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Boundary between first and second prime Fibonacci values. | `n=1 and n=2` | Yes | Confirms sequence indexing. |
| B2 | Performance-sensitive moderate index. | `n=6` | No | Useful for detecting runaway search loops. |
