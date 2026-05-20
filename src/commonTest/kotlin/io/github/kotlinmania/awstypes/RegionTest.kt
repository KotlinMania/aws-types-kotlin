// port-lint: source src/region.rs
package io.github.kotlinmania.awstypes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class RegionTest {
    @Test
    fun regionStringAccessAndDisplay() {
        val region = Region.new("us-east-1")

        assertEquals("us-east-1", region.asString())
        assertEquals("us-east-1", region.toString())
        assertEquals(region, Region.fromStatic("us-east-1"))
        assertNotEquals(region, Region.new("us-west-2"))
    }

    @Test
    fun signingRegionFromRegionAndStaticString() {
        val region = Region.new("eu-west-1")

        assertEquals("eu-west-1", SigningRegion.from(region).asString())
        assertEquals(SigningRegion.fromStatic("eu-west-1"), SigningRegion.from("eu-west-1"))
    }

    @Test
    fun signingRegionSetFromRegionStringAndIterable() {
        val region = Region.new("ap-south-1")

        assertEquals("ap-south-1", SigningRegionSet.from(region).asString())
        assertEquals("ap-south-1", SigningRegionSet.from("ap-south-1").asString())
        assertEquals(
            "us-east-1,us-west-2,eu-central-1",
            SigningRegionSet.fromIterable(listOf("us-east-1", "us-west-2", "eu-central-1")).asString(),
        )
        assertEquals("", SigningRegionSet.fromIterable(emptyList()).asString())
    }
}
