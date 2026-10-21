# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 9/10 (90.0%)
- **Function parity:** 158/158 matched (target 256) — 100.0%
- **Class/type parity:** 31/31 matched (target 76) — 100.0%
- **Combined symbol parity:** 189/189 matched (target 332) — 100.0%
- **Average inline-code cosine:** 0.13 (function body across 9 matched files)
- **Average documentation cosine:** 0.56 (doc text across 9 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 9 files with <0.60 function similarity
- **Needs Review:** 0 files with 0.60-0.84 function similarity
- **Excellent:** 0 files with >=0.85 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. sdk_config

- **Target:** `awstypes.SdkConfig`
- **Similarity:** 0.12
- **Dependents:** 1
- **Priority Score:** 1007408.8
- **Functions:** 72/72 matched (target 95)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 19)
- **Missing types:** _none_

### 2. origin

- **Target:** `awstypes.Origin`
- **Similarity:** 0.12
- **Dependents:** 1
- **Priority Score:** 1002108.8
- **Functions:** 18/18 matched (target 28)
- **Missing functions:** _none_
- **Types:** 3/3 matched (target 9)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 3. region

- **Target:** `awstypes.Region`
- **Similarity:** 0.16
- **Dependents:** 1
- **Priority Score:** 1001608.4
- **Functions:** 12/12 matched (target 30)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 5)
- **Missing types:** _none_

### 4. app_name

- **Target:** `awstypes.AppName`
- **Similarity:** 0.06
- **Dependents:** 1
- **Priority Score:** 1001009.4
- **Functions:** 7/7 matched (target 16)
- **Missing functions:** _none_
- **Types:** 3/3 matched (target 4)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 5. os_shim_internal

- **Target:** `awstypes.OsShimInternal`
- **Similarity:** 0.11
- **Dependents:** 0
- **Priority Score:** 2008.9
- **Functions:** 16/16 matched (target 28)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 9)
- **Missing types:** _none_
- **Tests:** 3/3 matched

### 6. service_config

- **Target:** `awstypes.ServiceConfig`
- **Similarity:** 0.12
- **Dependents:** 0
- **Priority Score:** 1808.8
- **Functions:** 13/13 matched (target 17)
- **Missing functions:** _none_
- **Types:** 5/5 matched (target 7)
- **Missing types:** _none_

### 7. endpoint_config

- **Target:** `awstypes.EndpointConfig`
- **Similarity:** 0.08
- **Dependents:** 0
- **Priority Score:** 1409.2
- **Functions:** 7/7 matched (target 12)
- **Missing functions:** _none_
- **Types:** 7/7 matched (target 8)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 8. request_id

- **Target:** `awstypes.RequestId`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 1106.5
- **Functions:** 10/10 matched (target 25)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 12)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 9. build_metadata

- **Target:** `awstypes.BuildMetadataTest`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 509.4
- **Functions:** 3/3 matched (target 5)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

