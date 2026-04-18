# Equivalence Partitioning - Java/0

Method: `hasCloseElements`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | At least one pair is closer than the threshold. | `numbers=[1.0, 2.8, 3.0, 4.0, 5.0, 2.0], threshold=0.3` | Yes | Expected result is `true`. |
| V2 | No pair is closer than the threshold. | `numbers=[1.0, 2.0, 3.0], threshold=0.5` | Yes | Expected result is `false`. |
| V3 | Duplicate or near-duplicate values appear in a longer list. | `numbers=[2.0, 2.0, 5.0], threshold=0.1` | Yes (improved) | Improved tests make the equality boundary explicit. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null list input. | `numbers=null, threshold=0.3` | Yes (improved) | Out of dataset contract; noted as robustness risk. |
| I2 | Negative threshold. | `numbers=[1.0, 2.0], threshold=-0.1` | Yes (improved) | Prompt does not define semantics for negative thresholds. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | List size below two. | `numbers=[] or [1.0], threshold=0.3` | Yes (improved) | Improved tests document the degenerate size cases. |
| B2 | Pair distance exactly equals the threshold. | `numbers=[1.0, 1.3], threshold=0.3` | Yes (improved) | Useful for checking `< threshold` versus `<= threshold`. |
