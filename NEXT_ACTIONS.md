# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 9/10 (90.0%)
- **Function parity:** 114/158 matched (target 208) — 72.2%
- **Class/type parity:** 24/31 matched (target 66) — 77.4%
- **Combined symbol parity:** 138/189 matched (target 274) — 73.0%
- **Average inline-code cosine:** 0.10 (function body across 9 matched files)
- **Average documentation cosine:** 0.58 (doc text across 9 matched files)
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
- **Similarity:** 0.10
- **Dependents:** 1
- **Priority Score:** 1227409.0
- **Functions:** 50/72 matched (target 73)
- **Missing functions:** `region`, `account_id_endpoint_mode`, `auth_scheme_preference`, `endpoint_url`, `retry_config`, `timeout_config`, `sleep_impl`, `identity_cache`, `credentials_provider`, `token_provider`, `time_source`, `app_name`, `http_client`, `use_fips`, `use_dual_stack`, `disable_request_compression`, `request_checksum_calculation`, `response_checksum_validation`, `request_min_compression_size_bytes`, `stalled_stream_protection`, `behavior_version`, `service_config`
- **Types:** 2/2 matched (target 19)
- **Missing types:** _none_

### 2. app_name

- **Target:** `awstypes.AppName`
- **Similarity:** 0.04
- **Dependents:** 1
- **Priority Score:** 1041009.6
- **Functions:** 4/7 matched (target 13)
- **Missing functions:** `fmt`, `valid_character`, `fmt`
- **Types:** 2/3 matched
- **Missing types:** `Storer`
- **Tests:** 2/2 matched

### 3. origin

- **Target:** `awstypes.Origin`
- **Similarity:** 0.13
- **Dependents:** 1
- **Priority Score:** 1032108.7
- **Functions:** 15/18 matched (target 23)
- **Missing functions:** `fmt`, `eq`, `partial_cmp`
- **Types:** 3/3 matched (target 9)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 4. region

- **Target:** `awstypes.Region`
- **Similarity:** 0.16
- **Dependents:** 1
- **Priority Score:** 1001608.4
- **Functions:** 12/12 matched (target 30)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 5)
- **Missing types:** _none_

### 5. service_config

- **Target:** `awstypes.ServiceConfig`
- **Similarity:** 0.03
- **Dependents:** 0
- **Priority Score:** 101809.7
- **Functions:** 5/13 matched (target 8)
- **Missing functions:** `service_id`, `profile`, `env`, `fmt`, `fmt`, `missing_service_id`, `missing_profile`, `missing_env`
- **Types:** 3/5 matched
- **Missing types:** `ErrorKind`, `Error`

### 6. os_shim_internal

- **Target:** `awstypes.OsShimInternal`
- **Similarity:** 0.07
- **Dependents:** 0
- **Priority Score:** 62009.3
- **Functions:** 12/16 matched (target 21)
- **Missing functions:** `default`, `from_raw_map`, `default`, `from`
- **Types:** 2/4 matched
- **Missing types:** `Inner`, `Fake`
- **Tests:** 3/3 matched

### 7. endpoint_config

- **Target:** `awstypes.EndpointConfig`
- **Similarity:** 0.04
- **Dependents:** 0
- **Priority Score:** 61409.6
- **Functions:** 3/7 matched (target 8)
- **Missing functions:** `fmt`, `from_str`, `new`, `fmt`
- **Types:** 5/7 matched (target 6)
- **Missing types:** `Storer`, `Err`
- **Tests:** 2/2 matched

### 8. request_id

- **Target:** `awstypes.RequestId`
- **Similarity:** 0.29
- **Dependents:** 0
- **Priority Score:** 1107.1
- **Functions:** 10/10 matched (target 27)
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

