# Equivalence Partitioning - Java/4

Method: `meanAbsoluteDeviation`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Symmetric values around the mean. | `numbers=[1.0, 2.0, 3.0]` | Yes | Easy oracle for manual checking. |
| V2 | Repeated values with a non-zero deviation. | `numbers=[1.0, 1.0, 3.0]` | Partially | Highlights averaging plus absolute-difference logic. |
| V3 | Decimal inputs. | `numbers=[0.5, 1.5, 2.5]` | No | Useful precision-oriented case. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Empty list. | `numbers=[]` | No | The prompt does not define a mean for empty input. |
| I2 | Null list. | `numbers=null` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Singleton list. | `numbers=[4.0]` | No | Expected deviation collapses to `0.0`. |
| B2 | Two-element list. | `numbers=[1.0, 5.0]` | No | Smallest non-trivial averaging case. |
