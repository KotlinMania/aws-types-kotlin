// port-lint: source src/build_metadata.rs
package io.github.kotlinmania.awstypes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BuildMetadataTest {
    // obviously a slightly brittle test. Will be a small update for Kotlin 3.0 :-)
    @Test
    fun validBuildMetadata() {
        val meta = BUILD_METADATA
        assertTrue(meta.kotlinVersion.startsWith("2.") || meta.kotlinVersion.startsWith("3."))
        // In our release process towards GA, the package version could either be 0. or 1.
        // so we need to make this assertion flexible.
        assertTrue(meta.corePkgVersion.startsWith("0.") || meta.corePkgVersion.startsWith("1."))
    }

    @Test
    fun osFamilyFromString() {
        assertEquals(OsFamily.Windows, OsFamily.fromString("windows"))
        assertEquals(OsFamily.Macos, OsFamily.fromString("macos"))
        assertEquals(OsFamily.Ios, OsFamily.fromString("ios"))
        assertEquals(OsFamily.Linux, OsFamily.fromString("linux"))
        assertEquals(OsFamily.Android, OsFamily.fromString("android"))
        assertEquals(OsFamily.Other, OsFamily.fromString("plan9"))
    }
}
