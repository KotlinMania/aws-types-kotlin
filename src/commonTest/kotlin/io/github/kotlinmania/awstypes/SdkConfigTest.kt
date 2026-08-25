// port-lint: tests sdk_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SdkConfigTest {
    @Test
    fun testDefaultBuilder() {
        val config = SdkConfig.builder().build()
        assertNull(config.region())
        assertNull(config.endpointUrl())
        assertNull(config.appName())
        assertNull(config.retryConfig())
        assertNull(config.timeoutConfig())
        assertNull(config.behaviorVersion())
        assertNull(config.useFips())
        assertNull(config.useDualStack())
        assertNull(config.disableRequestCompression())
        assertNull(config.requestMinCompressionSizeBytes())
        assertNull(config.requestChecksumCalculation())
        assertNull(config.responseChecksumValidation())
        assertEquals("unknown", config.getOrigin("region").toString())
        assertFalse(config.getOrigin("region").isClientConfig())
    }

    @Test
    fun testFullBuilderConfiguration() {
        val appName = AppName.new("test-app")
        val region = Region.new("us-west-2")
        val retryConfig = RetryConfig.standard().withMaxAttempts(5)
        val timeoutConfig =
            TimeoutConfig
                .builder()
                .operationTimeoutMillis(5000L)
                .operationAttemptTimeoutMillis(2000L)
                .connectTimeoutMillis(1000L)
                .readTimeoutMillis(3000L)
                .build()
        val stalled =
            StalledStreamProtectionConfig
                .enabled()
                .gracePeriodMillis(3000L)
                .minThroughputBytesPerSecond(10L)
                .build()

        val config =
            SdkConfig
                .builder()
                .appName(appName)
                .region(region)
                .accountIdEndpointMode(AccountIdEndpointMode.Preferred)
                .endpointUrl("https://custom.endpoint.com")
                .retryConfig(retryConfig)
                .timeoutConfig(timeoutConfig)
                .stalledStreamProtection(stalled)
                .useFips(true)
                .useDualStack(false)
                .behaviorVersion(BehaviorVersion.latest())
                .disableRequestCompression(false)
                .requestMinCompressionSizeBytes(20480L)
                .requestChecksumCalculation(RequestChecksumCalculation.WhenSupported)
                .responseChecksumValidation(ResponseChecksumValidation.WhenRequired)
                .authSchemePreference(AuthSchemePreference("sigv4a", "sigv4"))
                .apply {
                    insertOrigin("region", Origin.SHARED_ENVIRONMENT_VARIABLE)
                }.build()

        assertEquals(appName, config.appName())
        assertEquals(region, config.region())
        assertEquals(AccountIdEndpointMode.Preferred, config.accountIdEndpointMode())
        assertEquals("https://custom.endpoint.com", config.endpointUrl())
        assertEquals(retryConfig, config.retryConfig())
        assertEquals(timeoutConfig, config.timeoutConfig())
        assertEquals(stalled, config.stalledStreamProtection())
        assertEquals(true, config.useFips())
        assertEquals(false, config.useDualStack())
        assertEquals(BehaviorVersion.latest(), config.behaviorVersion())
        assertEquals(false, config.disableRequestCompression())
        assertEquals(20480L, config.requestMinCompressionSizeBytes())
        assertEquals(RequestChecksumCalculation.WhenSupported, config.requestChecksumCalculation())
        assertEquals(ResponseChecksumValidation.WhenRequired, config.responseChecksumValidation())
        assertEquals(listOf("sigv4a", "sigv4"), config.authSchemePreference()?.preferences)
        assertEquals(Origin.SHARED_ENVIRONMENT_VARIABLE, config.getOrigin("region"))
    }

    @Test
    fun testToBuilderRoundTrip() {
        val original =
            SdkConfig
                .builder()
                .region(Region.new("eu-central-1"))
                .endpointUrl("https://eu.endpoint.com")
                .useFips(false)
                .behaviorVersion(BehaviorVersion.v20231109())
                .build()

        val modified =
            original
                .toBuilder()
                .region(Region.new("eu-west-1"))
                .useFips(true)
                .build()

        assertEquals("eu-central-1", original.region()?.asString())
        assertEquals(false, original.useFips())
        assertEquals("eu-west-1", modified.region()?.asString())
        assertEquals(true, modified.useFips())
        assertEquals("https://eu.endpoint.com", modified.endpointUrl())
        assertEquals(BehaviorVersion.v20231109(), modified.behaviorVersion())
    }

    @Test
    fun testInterfacesAndDelegation() {
        var sleptMillis: Long? = null
        val sleep = AsyncSleep { millis -> sleptMillis = millis }
        val sharedSleep = SharedAsyncSleep(sleep)

        val timeSource = TimeSource { 123456789L }
        val sharedTime = SharedTimeSource(timeSource)
        assertEquals(123456789L, sharedTime.nowEpochMillis())

        val creds = ProvideCredentials { "dummy-creds" }
        val sharedCreds = SharedCredentialsProvider(creds)

        val token = ProvideToken { "dummy-token" }
        val sharedToken = SharedTokenProvider(token)

        val config =
            SdkConfig
                .builder()
                .sleepImpl(sharedSleep)
                .timeSource(sharedTime)
                .credentialsProvider(sharedCreds)
                .tokenProvider(sharedToken)
                .build()

        assertNotNull(config.sleepImpl())
        assertNotNull(config.timeSource())
        assertNotNull(config.credentialsProvider())
        assertNotNull(config.tokenProvider())
    }
}
