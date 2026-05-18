// port-lint: source src/build_metadata.rs
package io.github.kotlinmania.awstypes

// Kotlin/Wasm-WASI targets a WebAssembly system interface rather than a fixed
// `target_os`, so this falls through to `Other` the same way upstream's
// `cfg!(target_os)` cascade would for an unknown target.
internal actual val currentOsFamily: OsFamily = OsFamily.Other
