// port-lint: tests endpoint_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class EndpointConfigTest {
    @Test
    fun parseOkAccountIdEndpointMode() {
        assertEquals(
            AccountIdEndpointMode.Preferred,
            AccountIdEndpointMode.fromString("preferred"),
        )
        assertEquals(
            AccountIdEndpointMode.Disabled,
            AccountIdEndpointMode.fromString("disabled"),
        )
        assertEquals(
            AccountIdEndpointMode.Required,
            AccountIdEndpointMode.fromString("required"),
        )
        // Case-insensitivity check
        assertEquals(
            AccountIdEndpointMode.Preferred,
            AccountIdEndpointMode.fromString("PREFERRED"),
        )
    }

    @Test
    fun parseErrAccountIdEndpointMode() {
        val err =
            assertFailsWith<AccountIdEndpointModeParseError> {
                AccountIdEndpointMode.fromString("invalid")
            }
        val expected =
            "error parsing string `invalid` as `AccountIdEndpointMode`, valid options are: [\n    \"preferred\",\n    \"disabled\",\n    \"required\",\n]"
        assertEquals(expected, err.message)
        assertNull(AccountIdEndpointMode.tryFromString("invalid"))
    }

    @Test
    fun wrappersAndDisplay() {
        val fips = UseFips(true)
        assertEquals(true, fips.value)

        val dualStack = UseDualStack(false)
        assertEquals(false, dualStack.value)

        val endpointUrl = EndpointUrl("https://example.com")
        assertEquals("https://example.com", endpointUrl.value)

        assertEquals("preferred", AccountIdEndpointMode.Preferred.toString())
        assertEquals("disabled", AccountIdEndpointMode.Disabled.toString())
        assertEquals("required", AccountIdEndpointMode.Required.toString())
    }
}
