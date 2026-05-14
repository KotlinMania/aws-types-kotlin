// port-lint: source src/origin.rs
package io.github.kotlinmania.awstypes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class OriginTest {
    @Test
    fun testPrecedenceLowToHigh() {
        // Lowest to highest precedence
        val list = listOf(
            Origin.imds(),
            Origin.sharedProfileFile(),
            Origin.serviceProfileFile(),
            Origin.sharedEnvironmentVariable(),
            Origin.serviceEnvironmentVariable(),
            Origin.sharedConfig(),
            Origin.serviceConfig(),
        )

        for (i in 0 until list.size - 1) {
            val a = list[i]
            val b = list[i + 1]
            assertTrue(a < b)
        }
    }

    @Test
    fun testPrecedenceHighToLow() {
        // Highest to lowest precedence
        val list = listOf(
            Origin.serviceConfig(),
            Origin.sharedConfig(),
            Origin.serviceEnvironmentVariable(),
            Origin.sharedEnvironmentVariable(),
            Origin.serviceProfileFile(),
            Origin.sharedProfileFile(),
            Origin.imds(),
        )

        for (i in 0 until list.size - 1) {
            val a = list[i]
            val b = list[i + 1]
            assertTrue(a > b)
        }
    }

    @Test
    fun testUnknownIsNotEqual() {
        assertNotEquals(Origin.unknown(), Origin.imds())
        assertNotEquals(Origin.unknown(), Origin.sharedConfig())
        assertNotEquals(Origin.unknown(), Origin.serviceConfig())
        assertNotEquals(Origin.unknown(), Origin.sharedEnvironmentVariable())
        assertNotEquals(Origin.unknown(), Origin.serviceEnvironmentVariable())
        assertNotEquals(Origin.unknown(), Origin.sharedProfileFile())
        assertNotEquals(Origin.unknown(), Origin.serviceProfileFile())
        assertNotEquals(Origin.unknown(), Origin.unknown())
    }

    @Test
    fun testSelfEquality() {
        assertEquals(Origin.imds(), Origin.imds())
        assertEquals(Origin.sharedConfig(), Origin.sharedConfig())
        assertEquals(Origin.serviceConfig(), Origin.serviceConfig())
        assertEquals(
            Origin.sharedEnvironmentVariable(),
            Origin.sharedEnvironmentVariable(),
        )
        assertEquals(
            Origin.serviceEnvironmentVariable(),
            Origin.serviceEnvironmentVariable(),
        )
        assertEquals(Origin.sharedProfileFile(), Origin.sharedProfileFile())
        assertEquals(
            Origin.serviceProfileFile(),
            Origin.serviceProfileFile(),
        )
    }
}
