// port-lint: source src/build_metadata.rs
package io.github.kotlinmania.awstypes

// Apple tvOS has no arm in upstream's `cfg!(target_os)` cascade, so it falls
// through to `Other` the same way an unrecognized target would.
internal actual val currentOsFamily: OsFamily = OsFamily.Other
