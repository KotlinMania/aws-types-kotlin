# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 9/9 (100.0%)
- **Function parity:** 100/117 matched (target 208) — 85.5%
- **Class/type parity:** 21/31 matched (target 63) — 67.7%
- **Combined symbol parity:** 121/148 matched (target 271) — 81.8%
- **Average inline-code cosine:** 0.43 (function body across 9 matched files)
- **Average documentation cosine:** 0.65 (doc text across 9 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. origin

- **Target:** `awstypes.Origin`
- **Similarity:** 0.45
- **Dependents:** 1
- **Priority Score:** 1032105.6
- **Functions:** 15/18 matched (target 23)
- **Missing functions:** `fmt`, `eq`, `partial_cmp`
- **Types:** 3/3 matched (target 9)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 2. region

- **Target:** `awstypes.Region`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1031005.1
- **Functions:** 4/6 matched (target 27)
- **Missing functions:** `fmt`, `from_iter`
- **Types:** 3/4 matched
- **Missing types:** `Storer`

### 3. app_name

- **Target:** `awstypes.AppName`
- **Similarity:** 0.31
- **Dependents:** 1
- **Priority Score:** 1030906.9
- **Functions:** 4/6 matched (target 13)
- **Missing functions:** `fmt`, `valid_character`
- **Types:** 2/3 matched
- **Missing types:** `Storer`
- **Tests:** 2/2 matched

### 4. sdk_config

- **Target:** `awstypes.SdkConfig`
- **Similarity:** 0.51
- **Dependents:** 1
- **Priority Score:** 1005204.9
- **Functions:** 50/50 matched (target 73)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 19)
- **Missing types:** _none_

### 5. service_config

- **Target:** `awstypes.ServiceConfig`
- **Similarity:** 0.26
- **Dependents:** 0
- **Priority Score:** 71407.4
- **Functions:** 5/9 matched (target 8)
- **Missing functions:** `fmt`, `missing_service_id`, `missing_profile`, `missing_env`
- **Types:** 2/5 matched (target 4)
- **Missing types:** `ErrorKind`, `Error`, `LoadServiceConfig`

### 6. os_shim_internal

- **Target:** `awstypes.OsShimInternal`
- **Similarity:** 0.36
- **Dependents:** 0
- **Priority Score:** 51706.4
- **Functions:** 10/13 matched (target 21)
- **Missing functions:** `default`, `from_raw_map`, `from`
- **Types:** 2/4 matched
- **Missing types:** `Inner`, `Fake`
- **Tests:** 3/3 matched

### 7. endpoint_config

- **Target:** `awstypes.EndpointConfig`
- **Similarity:** 0.22
- **Dependents:** 0
- **Priority Score:** 51307.8
- **Functions:** 3/6 matched (target 8)
- **Missing functions:** `fmt`, `from_str`, `new`
- **Types:** 5/7 matched (target 6)
- **Missing types:** `Storer`, `Err`
- **Tests:** 2/2 matched

### 8. lib

- **Target:** `awstypes.SigningName`
- **Similarity:** 0.48
- **Dependents:** 0
- **Priority Score:** 10505.2
- **Functions:** 3/3 matched (target 8)
- **Missing functions:** _none_
- **Types:** 1/2 matched
- **Missing types:** `Storer`

### 9. request_id

- **Target:** `awstypes.RequestId`
- **Similarity:** 0.79
- **Dependents:** 0
- **Priority Score:** 702.1
- **Functions:** 6/6 matched (target 27)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 12)
- **Missing types:** _none_
- **Tests:** 4/4 matched

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

