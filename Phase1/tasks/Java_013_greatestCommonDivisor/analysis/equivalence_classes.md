# Equivalence Partitioning - Java/13

Method: `greatestCommonDivisor`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Inputs are coprime. | `a=3, b=5` | Yes | Expected result is `1`. |
| V2 | One input divides the other. | `a=25, b=15` | Yes | Expected result is `5`. |
| V3 | Inputs share a larger divisor. | `a=84, b=18` | Yes (improved) | Useful Euclidean-loop case. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | At least one zero input. | `a=0, b=5` | Yes (improved) | Prompt does not describe zero-handling. |
| I2 | Negative integers. | `a=-4, b=6` | Yes (improved) | Out of stated examples. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Equal inputs. | `a=7, b=7` | Yes (improved) | GCD should equal the input. |
| B2 | GCD is one after several modulo steps. | `a=35, b=64` | Yes (improved) | Stresses the loop termination path. |
