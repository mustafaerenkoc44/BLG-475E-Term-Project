# Equivalence Partitioning - Java/29

Method: `filterByPrefix`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Some strings start with the prefix. | `strings=["abc", "bcd", "cde", "array"], prefix="a"` | Yes | Prompt example. |
| V2 | No strings start with the prefix. | `strings=["bbb", "ccc"], prefix="a"` | Yes | Expected empty result. |
| V3 | Prefix equals the full string. | `strings=["a", "ab", "ba"], prefix="a"` | No | Full-string match should be kept. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list. | `strings=null, prefix="a"` | No | Out of contract. |
| I2 | Null prefix. | `strings=["abc"], prefix=null` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty input list. | `strings=[], prefix="a"` | Yes | Prompt example. |
| B2 | Empty prefix. | `strings=["abc", ""], prefix=""` | No | Documents an important unspecified case. |
