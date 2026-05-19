# port-lint Proposed Changes

**Generated:** 2026-05-18
**Source:** tmp/aws-types/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/awstypes

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/awstypes/Origin.kt` | `// port-lint: source src/origin.rs` | `// port-lint: source origin.rs` | `origin.rs` | `port-lint provenance header matched only after fallback normalization: 'src/origin.rs' vs expected 'origin.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/awstypes/OriginTest.kt` | `// port-lint: source src/origin.rs` | `// port-lint: source origin.rs` | `origin.rs` | `port-lint provenance header matched only after fallback normalization: 'src/origin.rs' vs expected 'origin.rs'` |
