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
        assertEquals("s3", key.serviceId())
        assertEquals("dev", key.profile)
        assertEquals("dev", key.profile())
        assertEquals("AWS_ENDPOINT_URL_S3", key.env)
        assertEquals("AWS_ENDPOINT_URL_S3", key.env())
    }

    @Test
    fun testBuilderMissingFields() {
        val err1 = assertFailsWith<Error> {
            ServiceConfigKey
                .builder()
                .profile("dev")
                .env("AWS_ENDPOINT_URL_S3")
                .build()
        }
        assertEquals(ErrorKind.MissingServiceId, err1.kind)
        assertEquals("couldn't build a ServiceEnvConfigKey: missing required service-id", err1.fmt())

        val err2 = assertFailsWith<Error> {
            ServiceConfigKey
                .builder()
                .serviceId("s3")
                .env("AWS_ENDPOINT_URL_S3")
                .build()
        }
        assertEquals(ErrorKind.MissingProfile, err2.kind)
        assertEquals("couldn't build a ServiceEnvConfigKey: missing required active profile name", err2.fmt())

        val err3 = assertFailsWith<Error> {
            ServiceConfigKey
                .builder()
                .serviceId("s3")
                .profile("dev")
                .build()
        }
        assertEquals(ErrorKind.MissingEnv, err3.kind)
        assertEquals("couldn't build a ServiceEnvConfigKey: missing required environment variable name", err3.fmt())
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
