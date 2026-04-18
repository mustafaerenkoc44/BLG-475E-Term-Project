# Equivalence Partitioning - Java/36

Method: `fizzBuzz`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Range below the first relevant multiple. | `n=10` | No | Expected count is `0`. |
| V2 | Range with qualifying multiples but no digit seven. | `n=50` | Yes | Prompt example returns `0`. |
| V3 | Range with qualifying numbers containing digit seven. | `n=79` | Yes | Prompt examples show the transition from `78` to `79`. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Negative upper bound. | `n=-1` | No | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Just before and after 77 enters the range. | `n=78 and n=79` | Yes | Important count jump because `77` contributes twice. |
| B2 | First qualifying multiple. | `n=12 or n=14` | No | Checks the start of the divisibility filter. |
