# Equivalence Partitioning - Java/6

Method: `parseNestedParens`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Single group with depth one. | `paren_string="()"` | Yes | Expected output contains `1`. |
| V2 | Multiple groups with different depths. | `paren_string="(()()) ((())) () ((())()())"` | Yes | Prompt example. |
| V3 | A deeply nested single group. | `paren_string="(((())))"` | Partially | Stresses maximum-depth tracking. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Unbalanced parentheses. | `paren_string="(()"` | No | Outside the stated balanced-input contract. |
| I2 | Unexpected tokens between groups. | `paren_string="() abc (())"` | No | Not defined by the prompt. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty input string. | `paren_string=""` | No | Added in improved tests. |
| B2 | Alternation between depth one and depth two. | `paren_string="() (())"` | Partially | Useful for branch transitions in the depth counter. |
