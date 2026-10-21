// port-lint: source build_metadata.rs
package io.github.kotlinmania.awstypes

// Metadata type used by all service SDKs to provide information about the environment the SDK was
// compiled for.

/** The version of Kotlin that was used to compile the SDK. */
public const val KOTLIN_VERSION: String = "2.3.21"

/** The version number of the `aws-types-kotlin` artifact compiled into the SDK. */
public const val CORE_PKG_VERSION: String = "0.1.0"

/** Metadata about the environment the SDK was compiled for. */
public data class BuildMetadata(
    /** Which version of Kotlin was used to compile the SDK. */
    public val kotlinVersion: String,
    /** The version number of the `aws-types-kotlin` artifact compiled with the SDK. */
    public val corePkgVersion: String,
    /** The OS the SDK was compiled for. */
    public val osFamily: OsFamily,
)

/** Operating system family that the SDK can be compiled for. */
public enum class OsFamily {
    /** Microsoft Windows. */
    Windows,

    /** Linux. */
    Linux,

    /** Apple macOS. */
    Macos,

    /** Google Android. */
    Android,

    /** Apple iOS. */
    Ios,

    /** A different family of operating system. */
    Other,
    ;

    public companion object {
        /** Returns the OS family this artifact was compiled for. */
        public fun fromEnv(): OsFamily = currentOsFamily

        /** Parses an OS family from a string representation. */
        public fun fromString(value: String): OsFamily =
            when (value) {
                "windows" -> Windows
                "macos" -> Macos
                "ios" -> Ios
                "linux" -> Linux
                "android" -> Android
                else -> Other
            }

        /** Parses an OS family from a string representation matching upstream `From<&str>`. */
        public fun from(s: String): OsFamily = fromString(s)
    }
}

/** The OS family this artifact was compiled for. */
internal expect val currentOsFamily: OsFamily

/** Constant build metadata for this artifact. */
public val BUILD_METADATA: BuildMetadata =
    BuildMetadata(
        kotlinVersion = KOTLIN_VERSION,
        corePkgVersion = CORE_PKG_VERSION,
        osFamily = OsFamily.fromEnv(),
    )
