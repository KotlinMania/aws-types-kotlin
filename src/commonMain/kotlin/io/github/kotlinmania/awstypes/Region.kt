// port-lint: source region.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

// Region type for determining the endpoint to send requests to.

/**
 * The region to send requests to.
 *
 * The region MUST be specified on a request. It may be configured globally or on a
 * per-client basis unless otherwise noted. A full list of regions is found in the
 * "Regions and Endpoints" document.
 *
 * See http://docs.aws.amazon.com/general/latest/gr/rande.html for information on AWS regions.
 */
public class Region private constructor(private val value: String) {
    /** Returns the string form of this region. */
    public fun asString(): String = value

    /** Returns the string form of this region. */
    public fun asRef(): String = value

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean = other is Region && value == other.value

    override fun hashCode(): Int = value.hashCode()

    public companion object {
        /** Creates a new [Region] from the given string. */
        public fun new(region: String): Region = Region(region)

        /** Creates a new [Region] from a static string. */
        public fun fromStatic(region: String): Region = Region(region)
    }
}

/** The region to use when signing requests. */
public class SigningRegion private constructor(private val value: String) {
    /** Returns the string form of this signing region. */
    public fun asString(): String = value

    /** Returns the string form of this signing region. */
    public fun asRef(): String = value

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean = other is SigningRegion && value == other.value

    override fun hashCode(): Int = value.hashCode()

    public companion object {
        /** Creates a [SigningRegion] from a [Region]. */
        public fun from(region: Region): SigningRegion = SigningRegion(region.asString())

        /** Creates a [SigningRegion] from a static string. */
        public fun fromStatic(region: String): SigningRegion = SigningRegion(region)

        /** Creates a [SigningRegion] from a string. */
        public fun from(region: String): SigningRegion = fromStatic(region)
    }
}

/**
 * The region set to use when signing SigV4A requests.
 *
 * Generally, user code will not need to interact with [SigningRegionSet]. See [Region].
 */
public class SigningRegionSet private constructor(private val value: String) {
    /** Returns the string form of this signing region set. */
    public fun asString(): String = value

    /** Returns the string form of this signing region set. */
    public fun asRef(): String = value

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean = other is SigningRegionSet && value == other.value

    override fun hashCode(): Int = value.hashCode()

    public companion object {
        /** Creates a [SigningRegionSet] from a [Region]. */
        public fun from(region: Region): SigningRegionSet = SigningRegionSet(region.asString())

        /** Creates a [SigningRegionSet] from a string. */
        public fun from(region: String): SigningRegionSet = SigningRegionSet(region)

        /** Creates a [SigningRegionSet] by joining regions with commas. */
        public fun fromIterable(regions: Iterable<String>): SigningRegionSet =
            fromIterator(regions.iterator())

        /** Creates a [SigningRegionSet] by joining regions from an iterator with commas. */
        public fun fromIterator(regions: Iterator<String>): SigningRegionSet {
            val value = StringBuilder()

            if (regions.hasNext()) {
                value.append(regions.next())
            }

            // If more than one region is present in the iterator, separate remaining regions
            // with commas.
            while (regions.hasNext()) {
                value.append(',')
                value.append(regions.next())
            }

            return SigningRegionSet(value.toString())
        }
    }
}
