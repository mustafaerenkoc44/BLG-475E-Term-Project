# Equivalence Partitioning - Java/23

Method: `strlen`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Empty string. | `string=""` | Yes | Expected length is `0`. |
| V2 | Alphabetic content. | `string="abc"` | Yes | Straightforward baseline. |
| V3 | Whitespace and punctuation. | `string="a b!"` | Yes (improved) | Every character should be counted. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null string. | `string=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Single character. | `string="x"` | Yes (improved) | Smallest non-empty string. |
| B2 | String containing only spaces. | `string="   "` | Yes (improved) | Documents that whitespace counts as characters. |
