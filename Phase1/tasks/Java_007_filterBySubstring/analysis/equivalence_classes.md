# Equivalence Partitioning - Java/7

Method: `filterBySubstring`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Some strings contain the substring. | `strings=["abc", "zab", "qq"], substring="ab"` | Yes | Expected to keep only matching entries. |
| V2 | No string contains the substring. | `strings=["xx", "yy"], substring="ab"` | Yes | Expected to return an empty list. |
| V3 | Case-sensitive mismatch. | `strings=["Abc", "abc"], substring="ab"` | Yes (improved) | Documents exact matching rules. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list. | `strings=null, substring="ab"` | Yes (improved) | Out of contract. |
| I2 | Null substring. | `strings=["abc"], substring=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty source list. | `strings=[], substring="ab"` | Yes | Should return `[]`. |
| B2 | Empty substring. | `strings=["abc", ""], substring=""` | Yes (improved) | Important unspecified boundary. |
