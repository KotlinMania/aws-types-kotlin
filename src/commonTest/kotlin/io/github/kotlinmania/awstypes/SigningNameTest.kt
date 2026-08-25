// port-lint: tests lib.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SigningNameTest {
    @Test
    fun testSigningNameCreationAndEquality() {
        val s1 = SigningName.fromStatic("s3")
        val s2 = SigningName.from("s3")
        val s3 = SigningName.from("dynamodb")

        assertEquals("s3", s1.asString())
        assertEquals("s3", s1.asRef())
        assertEquals("s3", s1.toString())
        assertEquals(s1, s2)
        assertEquals(s1.hashCode(), s2.hashCode())
        assertTrue(s1 != s3)
    }
}
