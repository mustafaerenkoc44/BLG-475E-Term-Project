# Test Smell Checklist

Use this checklist before and after improving tests.

## Common smells to inspect

- `Assertion Roulette`: multiple assertions without clear failure localization
- `Magic Number Test`: unexplained constants with no context
- `Duplicate Assert Logic`: repeated patterns that hide intent
- `General Fixture`: unused setup shared across too many tests
- `Conditional Test Logic`: tests that branch internally
- `Eager Test`: too many behaviors checked in one test
- `Poor Test Names`: behavior or boundary not visible from the test name
- `Missing Negative Cases`: invalid classes are absent
- `Missing Boundary Cases`: edge values are absent

## Review questions

1. Does each test target one clear behavior?
2. Are branch-triggering inputs explicit?
3. Are valid and invalid classes both represented?
4. Are empty, singleton, duplicate, overlap, min, max, and tie cases considered where applicable?
5. Do failure messages or names explain what broke?

