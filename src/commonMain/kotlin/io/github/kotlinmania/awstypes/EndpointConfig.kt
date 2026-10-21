// port-lint: source endpoint_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

public const val PREFERRED: String = "preferred"
public const val DISABLED: String = "disabled"
public const val REQUIRED: String = "required"

/**
 * Parameter wrapper for [UseFips].
 */
public data class UseFips(
    public val value: Boolean,
) {
    /** Storer marker type mirroring upstream `Storable::Storer = StoreReplace<UseFips>`. */
    public interface Storer
}

/**
 * Parameter wrapper for [UseDualStack].
 */
public data class UseDualStack(
    public val value: Boolean,
) {
    /** Storer marker type mirroring upstream `Storable::Storer = StoreReplace<UseDualStack>`. */
    public interface Storer
}

/**
 * Parameter wrapper for [EndpointUrl].
 */
public data class EndpointUrl(
    public val value: String,
) {
    /** Storer marker type mirroring upstream `Storable::Storer = StoreReplace<EndpointUrl>`. */
    public interface Storer
}

/**
 * Setting to control the account ID-based routing behavior.
 */
public enum class AccountIdEndpointMode {
    /**
     * The endpoint should include account ID if available.
     */
    Preferred,

    /**
     * A resolved endpoint does not include account ID.
     */
    Disabled,

    /**
     * The endpoint must include account ID. If the account ID isn't available, the SDK throws an error.
     */
    Required,
    ;

    /** Storer marker type mirroring upstream `Storable::Storer = StoreReplace<Self>`. */
    public interface Storer

    /** Returns the string representation of this mode. */
    public fun asString(): String =
        when (this) {
            Preferred -> PREFERRED
            Disabled -> DISABLED
            Required -> REQUIRED
        }

    /** Formats this mode matching upstream `Display::fmt`. */
    public fun fmt(): String = asString()

    override fun toString(): String = asString()

    public companion object {
        public val DEFAULT: AccountIdEndpointMode = Preferred

        /** Returns all available mode variants. */
        public fun allVariants(): List<AccountIdEndpointMode> = entries.toList()

        /**
         * Parses a string into an [AccountIdEndpointMode].
         *
         * Throws [AccountIdEndpointModeParseError] if the string does not match any valid mode.
         */
        public fun fromString(modeStr: String): AccountIdEndpointMode =
            when {
                modeStr.equals(PREFERRED, ignoreCase = true) -> Preferred
                modeStr.equals(DISABLED, ignoreCase = true) -> Disabled
                modeStr.equals(REQUIRED, ignoreCase = true) -> Required
                else -> throw AccountIdEndpointModeParseError.new(modeStr)
            }

        /**
         * Parses a string into an [AccountIdEndpointMode] matching upstream string conversion.
         */
        public fun fromStr(modeStr: String): AccountIdEndpointMode = fromString(modeStr)

        /**
         * Parses a string into an [AccountIdEndpointMode], returning null if invalid.
         */
        public fun tryFromString(modeStr: String): AccountIdEndpointMode? =
            try {
                fromString(modeStr)
            } catch (_: AccountIdEndpointModeParseError) {
                null
            }
    }
}

/**
 * Error encountered when failing to parse a string into [AccountIdEndpointMode].
 */
public class AccountIdEndpointModeParseError(
    public val modeString: String,
) : IllegalArgumentException(
        "error parsing string `$modeString` as `AccountIdEndpointMode`, valid options are: " +
            "[\n    \"preferred\",\n    \"disabled\",\n    \"required\",\n]",
    ) {
    /** Formats this error matching upstream `Display::fmt`. */
    public fun fmt(): String = message.orEmpty()

    public companion object {
        /** Creates a new [AccountIdEndpointModeParseError]. */
        public fun new(modeString: String): AccountIdEndpointModeParseError =
            AccountIdEndpointModeParseError(modeString)
    }
}

/** Error alias for [AccountIdEndpointModeParseError] mirroring upstream `FromStr::Err`. */
public typealias Err = AccountIdEndpointModeParseError

/** Storer type alias for endpoint config types mirroring upstream `Storable::Storer`. */
public typealias Storer = Any
