# Equivalence Partitioning - Java/15

Method: `stringSequence`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Lower boundary `n=0`. | `n=0` | Yes | Prompt example returns `0`. |
| V2 | Small positive integer. | `n=5` | Yes | Prompt example returns `0 1 2 3 4 5`. |
| V3 | Another positive integer. | `n=2` | Yes (improved) | Documents inclusive upper bound behavior. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Negative integer. | `n=-1` | Yes (improved) | Out of the prompt contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Boundary from zero to one. | `n=1` | Yes (improved) | Smallest expansion beyond the base case. |
| B2 | Formatting around two-digit numbers. | `n=10` | Yes (improved) | Checks spacing stability as token width changes. |
