// port-lint: source lib.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * The name of the service used to sign this request.
 *
 * Generally, user code should never interact with [SigningName] directly.
 */
public class SigningName private constructor(
    private val value: String,
) {
    /** Returns the string form of this signing name. */
    public fun asString(): String = value

    /** Returns the string form of this signing name. */
    public fun asRef(): String = value

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean = other is SigningName && value == other.value

    override fun hashCode(): Int = value.hashCode()

    public companion object {
        /** Creates a [SigningName] from a static string. */
        public fun fromStatic(name: String): SigningName = SigningName(name)

        /** Creates a [SigningName] from a string. */
        public fun from(name: String): SigningName = SigningName(name)
    }
}
