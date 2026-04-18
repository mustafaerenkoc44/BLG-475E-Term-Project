# Equivalence Partitioning - Java/25

Method: `factorize`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Prime power. | `n=8` | Yes | Prompt example returns `[2, 2, 2]`. |
| V2 | Square of a prime. | `n=25` | Yes | Prompt example returns `[5, 5]`. |
| V3 | Composite with distinct primes. | `n=70` | Yes | Prompt example returns `[2, 5, 7]`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Number less than two. | `n=1` | No | Prime factorization is undefined or empty depending on policy. |
| I2 | Negative integer. | `n=-8` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Smallest prime. | `n=2` | No | Expected factor list is `[2]`. |
| B2 | Large repeated factor count. | `n=32` | No | Exercises repeated division in the loop. |
