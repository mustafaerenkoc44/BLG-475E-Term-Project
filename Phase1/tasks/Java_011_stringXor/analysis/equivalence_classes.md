# Equivalence Partitioning - Java/11

Method: `stringXor`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Identical binary strings. | `a="1010", b="1010"` | Yes | Every position should become `0`. |
| V2 | Complementary binary strings. | `a="111", b="000"` | Yes | Every position should become `1`. |
| V3 | Mixed binary strings. | `a="1100", b="1010"` | Yes | Exercises both bit outcomes. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Different string lengths. | `a="10", b="101"` | Yes (improved) | Prompt assumes equal-length inputs. |
| I2 | Non-binary characters. | `a="10a", b="001"` | Yes (improved) | Outside the problem contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty strings. | `a="", b=""` | Yes (improved) | Documents the degenerate equal-length case. |
| B2 | Length one. | `a="1", b="0"` | Yes (improved) | Smallest non-empty input. |
