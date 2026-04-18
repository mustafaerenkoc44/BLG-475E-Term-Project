# Refactoring Log - Java/17

- Base dataset execution exposed a correctness or normalization defect after the raw generation step.
- Repair summary: Repaired token parsing so `o`, `o|`, and `.|` are decoded consistently without skipping or duplicating notes.
- Result: both model variants compiled and passed the dataset base tests after the repair pass.
- Follow-up: the repaired solution was retained for both base-test and coverage measurements.
