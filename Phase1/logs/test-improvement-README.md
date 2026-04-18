# Test-improvement interaction logs

This folder documents the **semi-agentic test improvement** activity required by
Phase 1. For every (task, model) pair we keep two kinds of logs:

| File | Topic | Source |
|---|---|---|
| `<model>/Java_<id>_code-generation.md` | raw LLM response when producing `Solution.java` | captured automatically by the generation pipeline |
| `<model>/Java_<id>_test-improvement.md` | semi-agentic transcript for the *improved* test suite | hand-authored from our working session (what we asked the model, what we kept, and the mutation/boundary test we added manually) |

Each `test-improvement.md` follows the template in
`test-improvement-template.md` and captures:

1. **Audit of the base suite** - the concrete test smells / coverage gaps the
   students identified before editing.
2. **Targeted prompt** - the exact natural-language ask we issued locally to
   `llama-cli` (Qwen or DeepSeek), restricted to a single, surgical request.
3. **Raw response** - verbatim completion from the local GGUF model.
4. **Kept / rewritten / rejected** - why we used or discarded each suggestion.
5. **Final additions** - the hand-written methods that actually landed in
   `generated-tests/improved/<model>/DatasetImprovedTest.java`, including the
   mutation-based test.

This satisfies the assignment's requirement that *every* LLM interaction is
logged with "full prompt / full raw response / usage note".
