// port-lint: source src/origin.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

// Types for tracking the origin of config values.

/**
 * A type for tracking the origin of config values.
 *
 * Upstream marks this type non-exhaustive, so callers outside this module must construct it
 * through the factory functions on the companion object rather than the primary constructor.
 */
public class Origin private constructor(internal val inner: Inner) {
    override fun toString(): String = when (inner) {
        Inner.Imds -> "IMDS"
        is Inner.ProfileFile -> when (inner.kind) {
            Kind.Shared -> "shared profile file"
            Kind.Service -> "service profile file"
        }
        is Inner.EnvironmentVariable -> when (inner.kind) {
            Kind.Shared -> "shared environment variable"
            Kind.Service -> "service environment variable"
        }
        is Inner.Programmatic -> when (inner.kind) {
            Kind.Shared -> "shared client"
            Kind.Service -> "service client"
        }
        is Inner.Unknown -> "unknown"
    }

    /**
     * Returns true if the origin was set programmatically i.e. on an `SdkConfig` or service
     * `Config`.
     */
    public fun isClientConfig(): Boolean = inner is Inner.Programmatic

    // Unknown is like NaN. It's not equal to anything, not even itself.
    override fun equals(other: Any?): Boolean {
        if (other !is Origin) return false
        return inner == other.inner
    }

    override fun hashCode(): Int = inner.hashCode()

    /**
     * Partial ordering between two origins.
     *
     * Mirrors upstream `impl PartialOrd for Inner`: returns `null` when either side is
     * [Inner.Unknown], because `Unknown` is incomparable. Otherwise returns a negative integer,
     * zero, or a positive integer following the Kotlin `Comparable` convention.
     */
    public fun partialCompareTo(other: Origin): Int? = inner.partialCompareTo(other.inner)

    public operator fun compareTo(other: Origin): Int = partialCompareTo(other) ?: 0

    public companion object {
        /** The origin is unknown. */
        public fun unknown(): Origin = Origin(Inner.Unknown())

        /** Set with IMDS. */
        public fun imds(): Origin = Origin(Inner.Imds)

        /** Set on a shared config struct. */
        public fun sharedConfig(): Origin = Origin(Inner.Programmatic(Kind.Shared))

        /** Set on a service config struct. */
        public fun serviceConfig(): Origin = Origin(Inner.Programmatic(Kind.Service))

        /** Set by an environment variable. */
        public fun sharedEnvironmentVariable(): Origin =
            Origin(Inner.EnvironmentVariable(Kind.Shared))

        /** Set by a service-specific environment variable. */
        public fun serviceEnvironmentVariable(): Origin =
            Origin(Inner.EnvironmentVariable(Kind.Service))

        /** Set in a profile file. */
        public fun sharedProfileFile(): Origin = Origin(Inner.ProfileFile(Kind.Shared))

        /** Service-specific, set in a profile file. */
        public fun serviceProfileFile(): Origin = Origin(Inner.ProfileFile(Kind.Service))

        /** Default origin — equivalent to upstream's `impl Default for Origin`. */
        public fun default(): Origin = unknown()
    }
}

internal sealed class Inner {
    internal data object Imds : Inner()
    internal data class ProfileFile(val kind: Kind) : Inner()
    internal data class EnvironmentVariable(val kind: Kind) : Inner()
    internal data class Programmatic(val kind: Kind) : Inner()

    // Unknown is like NaN. It's not equal to anything, not even itself.
    internal class Unknown : Inner() {
        override fun equals(other: Any?): Boolean = false
        override fun hashCode(): Int = 0
    }

    private fun isUnknown(): Boolean = this is Unknown

    internal fun partialCompareTo(other: Inner): Int? {
        if (this.isUnknown() || other.isUnknown()) {
            return null
        }

        return when (this) {
            // IMDS is the lowest priority
            Imds -> -1
            // ProfileFile is the second-lowest priority
            is ProfileFile -> when (other) {
                is Imds -> 1
                is ProfileFile -> kind.compareTo(other.kind)
                else -> -1
            }
            // EnvironmentVariable is the second-highest priority
            is EnvironmentVariable -> when (other) {
                is Imds, is ProfileFile -> 1
                is EnvironmentVariable -> kind.compareTo(other.kind)
                else -> -1
            }
            // Programmatic is the highest priority
            is Programmatic -> when (other) {
                is Imds, is EnvironmentVariable, is ProfileFile -> 1
                is Programmatic -> kind.compareTo(other.kind)
                else -> error(
                    "When we have something higher than programmatic we can update this case.",
                )
            }
            is Unknown -> error("unreachable: filtered by isUnknown check above")
        }
    }
}

internal enum class Kind {
    Shared,
    Service,
}
