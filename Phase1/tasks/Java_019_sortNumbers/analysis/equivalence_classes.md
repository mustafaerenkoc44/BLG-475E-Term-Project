# Equivalence Partitioning - Java/19

Method: `sortNumbers`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Unsorted numeral words. | `numbers="three one five"` | Yes | Prompt example. |
| V2 | Already sorted input. | `numbers="zero one two"` | Yes (improved) | Checks idempotence. |
| V3 | Input with duplicates. | `numbers="two one two"` | Yes (improved) | Ensures multiplicity is preserved. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unsupported token. | `numbers="one ten two"` | Yes (improved) | Out of contract. |
| I2 | Unexpected punctuation. | `numbers="one, two"` | Yes (improved) | Tokenizer robustness risk. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `numbers=""` | Yes (improved) | Documents the degenerate token stream. |
| B2 | Single numeral token. | `numbers="seven"` | Yes (improved) | Smallest valid input. |
