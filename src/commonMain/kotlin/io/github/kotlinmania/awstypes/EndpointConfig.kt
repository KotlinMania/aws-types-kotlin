// port-lint: source endpoint_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * Parameter wrapper for [UseFips].
 */
public data class UseFips(
    public val value: Boolean,
)

/**
 * Parameter wrapper for [UseDualStack].
 */
public data class UseDualStack(
    public val value: Boolean,
)

/**
 * Parameter wrapper for [EndpointUrl].
 */
public data class EndpointUrl(
    public val value: String,
)

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

    /** Returns the string representation of this mode. */
    public fun asString(): String =
        when (this) {
            Preferred -> "preferred"
            Disabled -> "disabled"
            Required -> "required"
        }

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
                modeStr.equals("preferred", ignoreCase = true) -> Preferred
                modeStr.equals("disabled", ignoreCase = true) -> Disabled
                modeStr.equals("required", ignoreCase = true) -> Required
                else -> throw AccountIdEndpointModeParseError(modeStr)
            }

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
    )
