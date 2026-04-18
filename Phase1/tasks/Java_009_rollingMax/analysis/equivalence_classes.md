# Equivalence Partitioning - Java/9

Method: `rollingMax`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Strictly increasing sequence. | `numbers=[1, 2, 3, 4]` | Yes | Rolling maximum changes every step. |
| V2 | Sequence with drops after a peak. | `numbers=[1, 3, 2, 5, 4]` | Yes | Peak retention is the main branch. |
| V3 | Sequence with duplicates. | `numbers=[2, 2, 1, 2]` | Yes (improved) | Checks equality handling. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list. | `numbers=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty list. | `numbers=[]` | Yes (improved) | Added in improved tests. |
| B2 | Singleton list. | `numbers=[7]` | Yes (improved) | Smallest non-empty sequence. |
