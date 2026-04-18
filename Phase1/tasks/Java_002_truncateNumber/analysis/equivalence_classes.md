# Equivalence Partitioning - Java/2

Method: `truncateNumber`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Positive non-integer input. | `x=3.5` | Yes | The fractional part should be preserved. |
| V2 | Negative non-integer input. | `x=-2.75` | Yes (improved) | Documents sign handling at the fractional boundary. |
| V3 | Whole-number input. | `x=7.0` | Yes | Expected output is `0.0`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | NaN input. | `x=NaN` | Yes (improved) | Outside the assignment contract. |
| I2 | Infinite input. | `x=Infinity` | Yes (improved) | Outside the assignment contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Zero input. | `x=0.0` | Yes (improved) | Useful scalar baseline. |
| B2 | Value immediately below an integer. | `x=4.999999` | Yes (improved) | Checks floating-point truncation sensitivity. |
