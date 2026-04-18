# Equivalence Partitioning - Java/16

Method: `countDistinctCharacters`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Mixed-case duplicates collapse to one distinct character. | `string="xyzXYZ"` | Yes | Prompt example returns `3`. |
| V2 | Mixed repeated and unique characters. | `string="Jerry"` | Yes | Prompt example returns `4`. |
| V3 | All characters are the same ignoring case. | `string="aAaA"` | Yes (improved) | Expected result is `1`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null string. | `string=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `string=""` | Yes (improved) | Expected count is `0`. |
| B2 | Single character. | `string="Q"` | Yes (improved) | Expected count is `1`. |
