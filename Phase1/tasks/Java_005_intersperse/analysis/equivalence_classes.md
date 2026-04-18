# Equivalence Partitioning - Java/5

Method: `intersperse`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Empty list remains empty. | `numbers=[], delimiter=7` | Yes | Confirms no extraneous delimiter is added. |
| V2 | Singleton list is unchanged. | `numbers=[5], delimiter=7` | Yes | There is no internal gap to fill. |
| V3 | Multi-element list gets delimiter between each pair. | `numbers=[1, 2, 3], delimiter=0` | Yes | Main functional scenario. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list. | `numbers=null, delimiter=0` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Size exactly one. | `numbers=[9], delimiter=4` | Yes | Classic off-by-one boundary. |
| B2 | Delimiter already appears in the list. | `numbers=[0, 1, 0], delimiter=0` | Yes (improved) | Checks whether inserted and original zeros are distinguished only by position. |
