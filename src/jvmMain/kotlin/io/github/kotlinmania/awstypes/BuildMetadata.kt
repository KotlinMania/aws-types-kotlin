// port-lint: source src/build_metadata.rs
package io.github.kotlinmania.awstypes

// The JVM artifact is not bound to a single target OS at build time the way
// upstream's `cfg!(target_os)` cascade is, so the OS family is resolved from
// the host the JVM is running on via the `os.name` system property.
internal actual val currentOsFamily: OsFamily =
    run {
        val osName = System.getProperty("os.name").orEmpty().lowercase()
        when {
            osName.contains("win") -> OsFamily.Windows
            osName.contains("mac") || osName.contains("darwin") -> OsFamily.Macos
            osName.contains("nux") || osName.contains("nix") || osName.contains("aix") -> OsFamily.Linux
            else -> OsFamily.Other
        }
    }
