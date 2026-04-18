# Equivalence Partitioning - Java/19

Method: `sortNumbers`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Unsorted numeral words. | `numbers="three one five"` | Yes | Prompt example. |
| V2 | Already sorted input. | `numbers="zero one two"` | No | Checks idempotence. |
| V3 | Input with duplicates. | `numbers="two one two"` | No | Ensures multiplicity is preserved. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unsupported token. | `numbers="one ten two"` | No | Out of contract. |
| I2 | Unexpected punctuation. | `numbers="one, two"` | No | Tokenizer robustness risk. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `numbers=""` | No | Documents the degenerate token stream. |
| B2 | Single numeral token. | `numbers="seven"` | No | Smallest valid input. |
