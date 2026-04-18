# Equivalence Partitioning - Java/21

Method: `rescaleToUnit`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Strictly increasing list. | `numbers=[1.0, 2.0, 3.0, 4.0, 5.0]` | Yes | Prompt example. |
| V2 | Unsorted list with negative and positive values. | `numbers=[-2.0, 3.0, 1.0]` | No | Checks min and max discovery. |
| V3 | List with repeated interior values. | `numbers=[1.0, 1.0, 5.0, 3.0]` | No | Interior duplicates should map consistently. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | All values are equal. | `numbers=[2.0, 2.0, 2.0]` | No | Would force division by zero in the usual formula. |
| I2 | Fewer than two elements. | `numbers=[4.0]` | No | Outside the prompt contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Exactly two elements. | `numbers=[2.0, 6.0]` | No | Smallest valid rescaling case. |
| B2 | Input already spans 0 and 1. | `numbers=[0.0, 0.5, 1.0]` | No | Should remain unchanged. |
