# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 1/9 (11.1%)
- **Function parity:** 15/155 matched (target 23) — 9.7%
- **Class/type parity:** 3/29 matched (target 9) — 10.3%
- **Combined symbol parity:** 18/184 matched (target 32) — 9.8%
- **Average inline-code cosine:** 0.67 (function body across 1 matched files)
- **Average documentation cosine:** 0.81 (doc text across 1 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. origin

- **Target:** `awstypes.Origin [PROVENANCE-FALLBACK]`
- **Similarity:** 0.67
- **Dependents:** 1
- **Priority Score:** 1032103.3
- **Functions:** 15/18 matched (target 23)
- **Missing functions:** `fmt`, `eq`, `partial_cmp`
- **Types:** 3/3 matched (target 9)
- **Missing types:** _none_
- **Tests:** 4/4 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/origin.rs` vs expected `origin.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/origin.rs` vs expected `origin.rs`
- **Proposed provenance header:** `// port-lint: source origin.rs` (current: `// port-lint: source src/origin.rs`)
- **Proposed provenance header:** `// port-lint: source origin.rs` (current: `// port-lint: source src/origin.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/aws-types/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/awstypes kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

