// port-lint: source src/build_metadata.rs
package io.github.kotlinmania.awstypes

// Kotlin/Wasm-JS runs across browser and Node.js without a single fixed `target_os` analogue,
// so this falls through to `Other` the same way upstream's `cfg!(target_os)` cascade would
// for an unknown target.
internal actual val currentOsFamily: OsFamily = OsFamily.Other
