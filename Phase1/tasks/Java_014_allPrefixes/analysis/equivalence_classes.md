# Equivalence Partitioning - Java/14

Method: `allPrefixes`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Regular multi-character string. | `string="abc"` | Yes | Prompt example. |
| V2 | Single-character string. | `string="x"` | No | Only one prefix should be returned. |
| V3 | String with repeated characters. | `string="aaa"` | No | Ensures prefixes depend on position, not distinctness. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null string. | `string=null` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `string=""` | No | Important degenerate case. |
| B2 | Length one. | `string="x"` | No | Smallest non-empty input. |
