# Equivalence Partitioning - Java/14

Method: `allPrefixes`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Regular multi-character string. | `string="abc"` | Yes | Prompt example. |
| V2 | Single-character string. | `string="x"` | Yes (improved) | Only one prefix should be returned. |
| V3 | String with repeated characters. | `string="aaa"` | Yes (improved) | Ensures prefixes depend on position, not distinctness. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null string. | `string=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `string=""` | Yes (improved) | Important degenerate case. |
| B2 | Length one. | `string="x"` | Yes (improved) | Smallest non-empty input. |
