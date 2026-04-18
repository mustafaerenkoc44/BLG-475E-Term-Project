# Phase 2 Mutation Assessment

## Tooling

- command: `mvn -f Phase2/pom.xml -P mutation test`
- engine: PITest `1.20.5`
- test framework bridge: `pitest-junit5-plugin 1.2.3`

## Final Result

- generated mutants: `93`
- killed mutants: `79`
- survived mutants: `14`
- mutation score: `84.95%`
- mutated-line coverage: `110 / 110 = 100.00%`

## Survivor Distribution

- `canonicalizeWord`: `6`
- `scanByWordLength`: `3`
- `howManyTimes`: `2`
- `scanWord`: `1`
- `tokenizeWords`: `1`
- `flipCase`: `1`

## Interpretation

The surviving mutants are not clustered around untested public features.
JaCoCo already reports full branch and line coverage for the selected final
implementation, and the remaining survivors mostly fall into two categories:

1. Equivalent normalization paths.
   Uppercase-only tokens still collapse to the same lowercase observable value
   even when internal predicate mutations change whether `flipCase` or
   `toLowerCase(Locale.ROOT)` performs the last step.
2. Defensive empty-input behavior.
   Several mutants alter blank-input early returns in ways that still yield the
   same externally observable empty result or preserve no-op behavior for
   punctuation and digits.

## Practical Conclusion

The mutation report is therefore treated as a residual-risk register rather
than a sign of missing public test obligations. The selected final
implementation is strong on all three relevant axes:

- public correctness: `18 / 18` tests passed
- structural coverage: `72 / 72` branches and `110 / 110` lines
- mutation resistance: `79 / 93` killed mutants
