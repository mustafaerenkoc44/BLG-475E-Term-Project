# Equivalence Partitioning - Java/12

Method: `longest`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Unique longest string exists. | `strings=["a", "bb", "ccc"]` | Yes | Prompt example returns `Optional[ccc]`. |
| V2 | Multiple strings tie for the longest length. | `strings=["aa", "bb", "c"]` | Yes | The first longest string should be returned. |
| V3 | Empty input list. | `strings=[]` | Yes | Expected result is `Optional.empty`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list. | `strings=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Singleton list. | `strings=["solo"]` | Yes (improved) | Smallest non-empty collection. |
| B2 | Tie appears after a shorter prefix. | `strings=["a", "bb", "cc"]` | Yes (improved) | Checks stable first-longest behavior. |
