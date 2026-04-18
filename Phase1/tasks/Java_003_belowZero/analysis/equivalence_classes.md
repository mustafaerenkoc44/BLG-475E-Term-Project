# Equivalence Partitioning - Java/3

Method: `belowZero`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Running balance never goes below zero. | `operations=[1, 2, -1]` | Yes | Expected result is `false`. |
| V2 | Running balance drops below zero once. | `operations=[1, -3, 2]` | Yes | Expected result is `true`. |
| V3 | Balance oscillates around zero before a later failure. | `operations=[2, -1, -1, 1, -2]` | Partially | Important for stateful branch coverage. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null operation list. | `operations=null` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty input list. | `operations=[]` | No | Documents the neutral starting state. |
| B2 | First operation immediately breaks the balance. | `operations=[-1]` | No | Critical boundary on the first iteration. |
