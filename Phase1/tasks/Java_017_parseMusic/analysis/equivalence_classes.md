# Equivalence Partitioning - Java/17

Method: `parseMusic`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Sequence uses all supported token kinds. | `string="o o| .|"` | Yes | Covers whole, half, and quarter notes. |
| V2 | Longer mixed sequence from the prompt. | `string="o o| .| o| o| .| .| .| .| o o"` | Yes | Representative branch-rich case. |
| V3 | Repeated single-token stream. | `string=".| .| .|"` | No | Useful for compact-token parsing. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unknown note token. | `string="x o|"` | No | Out of contract. |
| I2 | Incomplete token. | `string="o |"` | No | Malformed representation. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Single-token input. | `string="o"` | No | Smallest valid note sequence. |
| B2 | Extra internal spacing. | `string="o   o|   .|"` | No | Added in improved tests to stabilize whitespace parsing. |
