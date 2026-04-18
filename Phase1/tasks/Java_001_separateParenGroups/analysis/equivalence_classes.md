# Equivalence Partitioning - Java/1

Method: `separateParenGroups`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | A single balanced group is returned as one compact string. | `paren_string="( )"` | Yes | Spaces are ignored. |
| V2 | Multiple top-level groups are split correctly. | `paren_string="( ) (( )) (( )( ))"` | Yes | Representative multi-group scenario from the prompt. |
| V3 | Nested groups remain intact after whitespace stripping. | `paren_string="((()))"` | Yes (improved) | The grouping logic is branch-sensitive around nesting depth. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unbalanced parentheses. | `paren_string="(()"` | Yes (improved) | Prompt assumes balanced groups, so this is out of contract. |
| I2 | Non-parenthesis tokens in the stream. | `paren_string="() abc (())"` | Yes (improved) | Not covered by the dataset. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty or whitespace-only input. | `paren_string="   "` | Yes (improved) | Added as an improved boundary case. |
| B2 | Adjacent groups with no separating spaces. | `paren_string="()()"` | Yes (improved) | Checks whether group separation depends on spaces. |
