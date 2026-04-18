# Equivalence Partitioning - Java/18

Method: `howManyTimes`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | No occurrence in an empty source string. | `string="", substring="a"` | Yes | Prompt example returns `0`. |
| V2 | Single-character substring overlaps at every position. | `string="aaa", substring="a"` | Yes | Prompt example returns `3`. |
| V3 | Longer overlapping substring. | `string="aaaa", substring="aa"` | Yes | Prompt example returns `3`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Empty substring. | `string="abc", substring=""` | Yes (improved) | Important undefined case. |
| I2 | Null arguments. | `string=null, substring="a"` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Substring longer than the source. | `string="ab", substring="abc"` | Yes (improved) | Should return `0`. |
| B2 | Source and substring are identical. | `string="john", substring="john"` | Yes | Expected single full-length match. |
