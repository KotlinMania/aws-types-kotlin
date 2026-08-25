// port-lint: source app_name.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.concurrent.Volatile

/**
 * App name that can be configured with an AWS SDK client to become part of the user agent string.
 *
 * This name is used to identify the application in the user agent that gets sent along with requests.
 *
 * The name may only have alphanumeric characters and any of these characters:
 * ```text
 * !#$%&'*+-.^_`|~
 * ```
 * Spaces are not allowed.
 *
 * App names are recommended to be no more than 50 characters.
 */
public class AppName private constructor(
    private val value: String,
) {
    /** Returns the string form of this app name. */
    public fun asString(): String = value

    /** Returns the string form of this app name. */
    public fun asRef(): String = value

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean = other is AppName && value == other.value

    override fun hashCode(): Int = value.hashCode()

    public companion object {
        @Volatile
        private var lenRecommendationWarnEmitted: Boolean = false

        private fun isValidCharacter(c: Char): Boolean =
            (c in 'a'..'z') ||
                (c in 'A'..'Z') ||
                (c in '0'..'9') ||
                c == '!' ||
                c == '#' ||
                c == '$' ||
                c == '%' ||
                c == '&' ||
                c == '\'' ||
                c == '*' ||
                c == '+' ||
                c == '-' ||
                c == '.' ||
                c == '^' ||
                c == '_' ||
                c == '`' ||
                c == '|' ||
                c == '~'

        /**
         * Creates a new app name.
         *
         * Throws [InvalidAppName] if the given name doesn't meet character requirements.
         */
        public fun new(appName: String): AppName {
            if (appName.isEmpty() || !appName.all(::isValidCharacter)) {
                throw InvalidAppName()
            }
            if (appName.length > 50 && !lenRecommendationWarnEmitted) {
                lenRecommendationWarnEmitted = true
            }
            return AppName(appName)
        }

        /**
         * Creates a new app name, returning null if invalid.
         */
        public fun tryNew(appName: String): AppName? =
            try {
                new(appName)
            } catch (_: InvalidAppName) {
                null
            }

        /**
         * Returns whether the length recommendation warning was emitted.
         */
        internal fun wasLengthWarningEmitted(): Boolean = lenRecommendationWarnEmitted

        /**
         * Resets the length recommendation warning state (for testing).
         */
        internal fun resetLengthWarning() {
            lenRecommendationWarnEmitted = false
        }
    }
}

/**
 * Error for when an app name doesn't meet character requirements.
 *
 * See [AppName] for details on these requirements.
 */
public class InvalidAppName(
    message: String =
        "The app name can only have alphanumeric characters, or any of '!' | '#' | '$' | '%' | '&' | '\\'' | '*' | '+' | '-' | '.' | '^' | '_' | '`' | '|' | '~'",
) : IllegalArgumentException(message)
