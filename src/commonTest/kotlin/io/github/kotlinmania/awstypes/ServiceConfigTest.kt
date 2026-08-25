// port-lint: tests service_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ServiceConfigTest {
    @Test
    fun testBuilderSuccess() {
        val key =
            ServiceConfigKey
                .builder()
                .serviceId("s3")
                .profile("dev")
                .env("AWS_ENDPOINT_URL_S3")
                .build()

        assertEquals("s3", key.serviceId)
        assertEquals("dev", key.profile)
        assertEquals("AWS_ENDPOINT_URL_S3", key.env)
    }

    @Test
    fun testBuilderMissingFields() {
        assertFailsWith<ServiceConfigKeyError> {
            ServiceConfigKey
                .builder()
                .profile("dev")
                .env("AWS_ENDPOINT_URL_S3")
                .build()
        }

        assertFailsWith<ServiceConfigKeyError> {
            ServiceConfigKey
                .builder()
                .serviceId("s3")
                .env("AWS_ENDPOINT_URL_S3")
                .build()
        }

        assertFailsWith<ServiceConfigKeyError> {
            ServiceConfigKey
                .builder()
                .serviceId("s3")
                .profile("dev")
                .build()
        }
    }

    @Test
    fun testLoadServiceConfig() {
        val map =
            mapOf(
                ServiceConfigKey("s3", "dev", "AWS_ENDPOINT_URL_S3") to "http://localhost:4566",
            )
        val loader = LoadServiceConfig { key -> map[key] }

        val key = ServiceConfigKey("s3", "dev", "AWS_ENDPOINT_URL_S3")
        assertEquals("http://localhost:4566", loader.loadConfig(key))

        val missingKey = ServiceConfigKey("dynamodb", "dev", "AWS_ENDPOINT_URL_DDB")
        assertEquals(null, loader.loadConfig(missingKey))
    }
}
