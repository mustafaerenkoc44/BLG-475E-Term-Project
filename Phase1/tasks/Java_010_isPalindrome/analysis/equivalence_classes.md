# Equivalence Partitioning - Java/10

Method: `isPalindrome`

## Valid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| V1 | Input is already a palindrome. | `string="aba"` | Yes | No suffix should be appended. |
| V2 | Input needs one appended character. | `string="cata"` | Yes | Prompt example gives `catac`. |
| V3 | Input needs multiple appended characters. | `string="cat"` | Yes | Prompt example exercises the main search loop. |

## Invalid Classes

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| I1 | Null string. | `string=null` | Yes (improved) | Out of contract. |

## Boundary Conditions

| Class ID | Description | Representative Input | Covered By Existing Test? | Notes |
|---|---|---|---|---|
| B1 | Empty string. | `string=""` | Yes | Expected output remains empty. |
| B2 | Single character. | `string="x"` | Yes (improved) | Shortest already-palindromic input. |
