# Equivalence Partitioning - Java/8

Method: `sumProduct`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Empty input list. | `numbers=[]` | Yes | Defines neutral elements for sum and product. |
| V2 | Singleton list. | `numbers=[4]` | Yes | Expected `[4, 4]`. |
| V3 | Multiple values including zero. | `numbers=[2, 0, 5]` | Partially | Product should collapse to zero. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list. | `numbers=null` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Single zero. | `numbers=[0]` | No | Boundary between neutral and absorbing product behavior. |
| B2 | Mixed positive and negative values. | `numbers=[-2, 3, -1]` | No | Documents sign behavior. |
