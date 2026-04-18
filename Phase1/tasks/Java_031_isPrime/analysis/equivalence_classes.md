# Equivalence Partitioning - Java/31

Method: `isPrime`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Small prime number. | `n=2` | No | Smallest prime boundary. |
| V2 | Composite number. | `n=6` | Yes | Prompt example returns `false`. |
| V3 | Large prime number. | `n=101` | Yes | Prompt example returns `true`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Non-positive integer. | `n=1 or n=0` | Yes | Prompt examples include `1` as non-prime. |
| I2 | Negative integer. | `n=-7` | No | Out of contract but useful for robustness notes. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Perfect square of a prime. | `n=49` | No | Important divisor-loop boundary. |
| B2 | Even number just above two. | `n=4` | Yes | Fast rejection path. |
