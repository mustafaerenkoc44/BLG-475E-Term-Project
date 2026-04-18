# Equivalence Partitioning - Java/27

Method: `flipCase`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Lowercase letters become uppercase. | `string="abc"` | Yes | Main case-flip path. |
| V2 | Uppercase letters become lowercase. | `string="XYZ"` | Yes | Inverse path. |
| V3 | Digits and punctuation remain unchanged. | `string="aB3!"` | Yes | Important preservation rule. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null string. | `string=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `string=""` | Yes (improved) | Added in improved tests. |
| B2 | String with no alphabetic characters. | `string="123!?"` | Yes (improved) | Checks no-op path. |
