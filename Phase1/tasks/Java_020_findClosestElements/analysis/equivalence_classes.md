# Equivalence Partitioning - Java/20

Method: `findClosestElements`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Unique closest pair in an unsorted list. | `numbers=[1.0, 2.0, 3.0, 4.0, 5.0, 2.2]` | Yes | Prompt example returns `[2.0, 2.2]`. |
| V2 | Exact duplicates create a zero-distance pair. | `numbers=[1.0, 2.0, 3.0, 4.0, 5.0, 2.0]` | Yes | Prompt example returns `[2.0, 2.0]`. |
| V3 | Exactly two elements are present. | `numbers=[-1.0, 4.5]` | Yes (improved) | Only possible pair should be returned. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Fewer than two elements. | `numbers=[1.0]` | Yes (improved) | Outside the prompt contract. |
| I2 | Null list. | `numbers=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | List of size two. | `numbers=[1.0, 1.1]` | Yes (improved) | Minimal valid collection. |
| B2 | Tie between two equally close pairs. | `numbers=[1.0, 2.0, 3.0, 4.0]` | Yes (improved) | Useful for deterministic tie behavior. |
