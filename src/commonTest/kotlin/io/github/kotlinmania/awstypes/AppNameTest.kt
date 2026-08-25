// port-lint: tests app_name.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AppNameTest {
    @Test
    fun validation() {
        val valid = AppName.new("asdf1234ASDF!#$%&'*+-.^_`|~")
        assertEquals("asdf1234ASDF!#$%&'*+-.^_`|~", valid.asString())
        assertEquals("asdf1234ASDF!#$%&'*+-.^_`|~", valid.asRef())
        assertEquals("asdf1234ASDF!#$%&'*+-.^_`|~", valid.toString())

        assertFailsWith<InvalidAppName> {
            AppName.new("foo bar")
        }
        assertFailsWith<InvalidAppName> {
            AppName.new("🚀")
        }
        assertFailsWith<InvalidAppName> {
            AppName.new("")
        }

        assertNotNull(AppName.tryNew("valid-app_1.0"))
        assertNull(AppName.tryNew("invalid space"))
    }

    @Test
    fun logWarnOnce() {
        AppName.resetLengthWarning()
        assertFalse(AppName.wasLengthWarningEmitted())

        AppName.new("not-long")
        assertFalse(AppName.wasLengthWarningEmitted())

        AppName.new("greaterthanfiftycharactersgreaterthanfiftycharacters")
        assertTrue(AppName.wasLengthWarningEmitted())

        AppName.new("anothergreaterthanfiftycharactersgreaterthanfiftycharacters")
        assertTrue(AppName.wasLengthWarningEmitted())
    }

    @Test
    fun equalityAndHashCode() {
        val a1 = AppName.new("my-app")
        val a2 = AppName.new("my-app")
        val b = AppName.new("other-app")

        assertEquals(a1, a2)
        assertEquals(a1.hashCode(), a2.hashCode())
        assertTrue(a1 != b)
    }
}
