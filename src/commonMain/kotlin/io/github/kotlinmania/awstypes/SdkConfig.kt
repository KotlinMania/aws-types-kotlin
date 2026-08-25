// port-lint: source sdk_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * Behavior version configured for an SDK client.
 */
public data class BehaviorVersion(
    public val version: String,
) {
    public companion object {
        public fun latest(): BehaviorVersion = BehaviorVersion("latest")

        public fun v20231109(): BehaviorVersion = BehaviorVersion("2023-11-09")

        public fun v20240328(): BehaviorVersion = BehaviorVersion("2024-03-28")
    }
}

/**
 * Retry mode for SDK operations.
 */
public enum class RetryMode {
    Standard,
    Adaptive,
}

/**
 * Configuration for operation retries.
 */
public data class RetryConfig(
    public val mode: RetryMode = RetryMode.Standard,
    public val maxAttempts: Int = 3,
    public val initialBackoffMillis: Long = 100L,
    public val maxBackoffMillis: Long = 20000L,
) {
    public fun withMaxAttempts(maxAttempts: Int): RetryConfig = copy(maxAttempts = maxAttempts)

    public companion object {
        public fun standard(): RetryConfig = RetryConfig(mode = RetryMode.Standard)

        public fun adaptive(): RetryConfig = RetryConfig(mode = RetryMode.Adaptive)

        public fun disabled(): RetryConfig = RetryConfig(maxAttempts = 1)
    }
}

/**
 * Configuration for timeouts on SDK operations and HTTP requests.
 */
public data class TimeoutConfig(
    public val operationTimeoutMillis: Long? = null,
    public val operationAttemptTimeoutMillis: Long? = null,
    public val connectTimeoutMillis: Long? = null,
    public val readTimeoutMillis: Long? = null,
) {
    public class Builder {
        private var operationTimeoutMillis: Long? = null
        private var operationAttemptTimeoutMillis: Long? = null
        private var connectTimeoutMillis: Long? = null
        private var readTimeoutMillis: Long? = null

        public fun operationTimeoutMillis(millis: Long): Builder = apply { this.operationTimeoutMillis = millis }

        public fun operationAttemptTimeoutMillis(millis: Long): Builder = apply { this.operationAttemptTimeoutMillis = millis }

        public fun connectTimeoutMillis(millis: Long): Builder = apply { this.connectTimeoutMillis = millis }

        public fun readTimeoutMillis(millis: Long): Builder = apply { this.readTimeoutMillis = millis }

        public fun build(): TimeoutConfig =
            TimeoutConfig(
                operationTimeoutMillis = operationTimeoutMillis,
                operationAttemptTimeoutMillis = operationAttemptTimeoutMillis,
                connectTimeoutMillis = connectTimeoutMillis,
                readTimeoutMillis = readTimeoutMillis,
            )
    }

    public companion object {
        public fun builder(): Builder = Builder()

        public fun disabled(): TimeoutConfig = TimeoutConfig()
    }
}

/**
 * Configuration for stalled stream protection.
 */
public data class StalledStreamProtectionConfig(
    public val isEnabled: Boolean = false,
    public val gracePeriodMillis: Long = 5000L,
    public val minThroughputBytesPerSecond: Long = 1L,
) {
    public class Builder(
        private val isEnabled: Boolean,
    ) {
        private var gracePeriodMillis: Long = 5000L
        private var minThroughputBytesPerSecond: Long = 1L

        public fun gracePeriodMillis(millis: Long): Builder = apply { this.gracePeriodMillis = millis }

        public fun minThroughputBytesPerSecond(bytes: Long): Builder = apply { this.minThroughputBytesPerSecond = bytes }

        public fun build(): StalledStreamProtectionConfig =
            StalledStreamProtectionConfig(
                isEnabled = isEnabled,
                gracePeriodMillis = gracePeriodMillis,
                minThroughputBytesPerSecond = minThroughputBytesPerSecond,
            )
    }

    public companion object {
        public fun enabled(): Builder = Builder(true)

        public fun disabled(): StalledStreamProtectionConfig = StalledStreamProtectionConfig(false)
    }
}

/**
 * Request checksum calculation strategy.
 */
public enum class RequestChecksumCalculation {
    WhenSupported,
    WhenRequired,
}

/**
 * Response checksum validation strategy.
 */
public enum class ResponseChecksumValidation {
    WhenSupported,
    WhenRequired,
}

/**
 * Preference order of authentication schemes.
 */
public data class AuthSchemePreference(
    public val preferences: List<String> = emptyList(),
) {
    public constructor(vararg preferences: String) : this(preferences.toList())
}

/**
 * Asynchronous sleep interface.
 */
public fun interface AsyncSleep {
    public suspend fun sleep(durationMillis: Long)
}

public class SharedAsyncSleep(
    private val delegate: AsyncSleep,
) : AsyncSleep by delegate

/**
 * Time source interface.
 */
public fun interface TimeSource {
    public fun nowEpochMillis(): Long
}

public class SharedTimeSource(
    private val delegate: TimeSource,
) : TimeSource by delegate

/**
 * HTTP client interface marker.
 */
public interface HttpClient

public class SharedHttpClient(
    public val client: HttpClient,
) : HttpClient

/**
 * Identity cache interface.
 */
public interface ResolveCachedIdentity

public class SharedIdentityCache(
    public val cache: ResolveCachedIdentity,
) : ResolveCachedIdentity

/**
 * Credentials provider interface.
 */
public fun interface ProvideCredentials {
    public suspend fun provideCredentials(): Any?
}

public class SharedCredentialsProvider(
    private val delegate: ProvideCredentials,
) : ProvideCredentials by delegate

/**
 * Bearer auth token provider interface.
 */
public fun interface ProvideToken {
    public suspend fun provideToken(): Any?
}

public class SharedTokenProvider(
    private val delegate: ProvideToken,
) : ProvideToken by delegate

/**
 * AWS Shared Configuration representation agnostic of a specific service.
 */
public data class SdkConfig(
    public val appName: AppName? = null,
    public val authSchemePreference: AuthSchemePreference? = null,
    public val identityCache: SharedIdentityCache? = null,
    public val credentialsProvider: SharedCredentialsProvider? = null,
    public val tokenProvider: SharedTokenProvider? = null,
    public val region: Region? = null,
    public val accountIdEndpointMode: AccountIdEndpointMode? = null,
    public val endpointUrl: String? = null,
    public val retryConfig: RetryConfig? = null,
    public val sleepImpl: SharedAsyncSleep? = null,
    public val timeSource: SharedTimeSource? = null,
    public val timeoutConfig: TimeoutConfig? = null,
    public val stalledStreamProtectionConfig: StalledStreamProtectionConfig? = null,
    public val httpClient: SharedHttpClient? = null,
    public val useFips: Boolean? = null,
    public val useDualStack: Boolean? = null,
    public val behaviorVersion: BehaviorVersion? = null,
    public val serviceConfig: LoadServiceConfig? = null,
    public val configOrigins: Map<String, Origin> = emptyMap(),
    public val disableRequestCompression: Boolean? = null,
    public val requestMinCompressionSizeBytes: Long? = null,
    public val requestChecksumCalculation: RequestChecksumCalculation? = null,
    public val responseChecksumValidation: ResponseChecksumValidation? = null,
) {
    public fun region(): Region? = region

    public fun accountIdEndpointMode(): AccountIdEndpointMode? = accountIdEndpointMode

    public fun authSchemePreference(): AuthSchemePreference? = authSchemePreference

    public fun endpointUrl(): String? = endpointUrl

    public fun retryConfig(): RetryConfig? = retryConfig

    public fun timeoutConfig(): TimeoutConfig? = timeoutConfig

    public fun sleepImpl(): SharedAsyncSleep? = sleepImpl

    public fun identityCache(): SharedIdentityCache? = identityCache

    public fun credentialsProvider(): SharedCredentialsProvider? = credentialsProvider

    public fun tokenProvider(): SharedTokenProvider? = tokenProvider

    public fun timeSource(): SharedTimeSource? = timeSource

    public fun appName(): AppName? = appName

    public fun httpClient(): SharedHttpClient? = httpClient

    public fun useFips(): Boolean? = useFips

    public fun useDualStack(): Boolean? = useDualStack

    public fun disableRequestCompression(): Boolean? = disableRequestCompression

    public fun requestChecksumCalculation(): RequestChecksumCalculation? = requestChecksumCalculation

    public fun responseChecksumValidation(): ResponseChecksumValidation? = responseChecksumValidation

    public fun requestMinCompressionSizeBytes(): Long? = requestMinCompressionSizeBytes

    public fun stalledStreamProtection(): StalledStreamProtectionConfig? = stalledStreamProtectionConfig

    public fun behaviorVersion(): BehaviorVersion? = behaviorVersion

    public fun serviceConfig(): LoadServiceConfig? = serviceConfig

    public fun getOrigin(setting: String): Origin = configOrigins[setting] ?: Origin.UNKNOWN

    public fun toBuilder(): Builder = intoBuilder()

    public fun intoBuilder(): Builder =
        Builder().apply {
            appName(this@SdkConfig.appName)
            authSchemePreference(this@SdkConfig.authSchemePreference)
            identityCache(this@SdkConfig.identityCache)
            credentialsProvider(this@SdkConfig.credentialsProvider)
            tokenProvider(this@SdkConfig.tokenProvider)
            region(this@SdkConfig.region)
            accountIdEndpointMode(this@SdkConfig.accountIdEndpointMode)
            endpointUrl(this@SdkConfig.endpointUrl)
            retryConfig(this@SdkConfig.retryConfig)
            sleepImpl(this@SdkConfig.sleepImpl)
            timeSource(this@SdkConfig.timeSource)
            timeoutConfig(this@SdkConfig.timeoutConfig)
            stalledStreamProtection(this@SdkConfig.stalledStreamProtectionConfig)
            httpClient(this@SdkConfig.httpClient)
            useFips(this@SdkConfig.useFips)
            useDualStack(this@SdkConfig.useDualStack)
            behaviorVersion(this@SdkConfig.behaviorVersion)
            serviceConfig(this@SdkConfig.serviceConfig)
            disableRequestCompression(this@SdkConfig.disableRequestCompression)
            requestMinCompressionSizeBytes(this@SdkConfig.requestMinCompressionSizeBytes)
            requestChecksumCalculation(this@SdkConfig.requestChecksumCalculation)
            responseChecksumValidation(this@SdkConfig.responseChecksumValidation)
            for ((k, v) in this@SdkConfig.configOrigins) {
                insertOrigin(k, v)
            }
        }

    public class Builder {
        private var appName: AppName? = null
        private var authSchemePreference: AuthSchemePreference? = null
        private var identityCache: SharedIdentityCache? = null
        private var credentialsProvider: SharedCredentialsProvider? = null
        private var tokenProvider: SharedTokenProvider? = null
        private var region: Region? = null
        private var accountIdEndpointMode: AccountIdEndpointMode? = null
        private var endpointUrl: String? = null
        private var retryConfig: RetryConfig? = null
        private var sleepImpl: SharedAsyncSleep? = null
        private var timeSource: SharedTimeSource? = null
        private var timeoutConfig: TimeoutConfig? = null
        private var stalledStreamProtectionConfig: StalledStreamProtectionConfig? = null
        private var httpClient: SharedHttpClient? = null
        private var useFips: Boolean? = null
        private var useDualStack: Boolean? = null
        private var behaviorVersion: BehaviorVersion? = null
        private var serviceConfig: LoadServiceConfig? = null
        private val configOrigins: MutableMap<String, Origin> = mutableMapOf()
        private var disableRequestCompression: Boolean? = null
        private var requestMinCompressionSizeBytes: Long? = null
        private var requestChecksumCalculation: RequestChecksumCalculation? = null
        private var responseChecksumValidation: ResponseChecksumValidation? = null

        public fun region(region: Region?): Builder = apply { this.region = region }

        public fun setRegion(region: Region?): Builder = apply { this.region = region }

        public fun accountIdEndpointMode(mode: AccountIdEndpointMode?): Builder = apply { this.accountIdEndpointMode = mode }

        public fun setAccountIdEndpointMode(mode: AccountIdEndpointMode?): Builder = apply { this.accountIdEndpointMode = mode }

        public fun endpointUrl(endpointUrl: String?): Builder = apply { this.endpointUrl = endpointUrl }

        public fun setEndpointUrl(endpointUrl: String?): Builder = apply { this.endpointUrl = endpointUrl }

        public fun requestChecksumCalculation(calc: RequestChecksumCalculation?): Builder = apply { this.requestChecksumCalculation = calc }

        public fun setRequestChecksumCalculation(calc: RequestChecksumCalculation?): Builder = apply { this.requestChecksumCalculation = calc }

        public fun responseChecksumValidation(valid: ResponseChecksumValidation?): Builder = apply { this.responseChecksumValidation = valid }

        public fun setResponseChecksumValidation(valid: ResponseChecksumValidation?): Builder = apply { this.responseChecksumValidation = valid }

        public fun retryConfig(retryConfig: RetryConfig?): Builder = apply { this.retryConfig = retryConfig }

        public fun setRetryConfig(retryConfig: RetryConfig?): Builder = apply { this.retryConfig = retryConfig }

        public fun timeoutConfig(timeoutConfig: TimeoutConfig?): Builder = apply { this.timeoutConfig = timeoutConfig }

        public fun setTimeoutConfig(timeoutConfig: TimeoutConfig?): Builder = apply { this.timeoutConfig = timeoutConfig }

        public fun sleepImpl(sleepImpl: SharedAsyncSleep?): Builder = apply { this.sleepImpl = sleepImpl }

        public fun setSleepImpl(sleepImpl: SharedAsyncSleep?): Builder = apply { this.sleepImpl = sleepImpl }

        public fun identityCache(identityCache: SharedIdentityCache?): Builder = apply { this.identityCache = identityCache }

        public fun setIdentityCache(identityCache: SharedIdentityCache?): Builder = apply { this.identityCache = identityCache }

        public fun credentialsProvider(credentialsProvider: SharedCredentialsProvider?): Builder = apply { this.credentialsProvider = credentialsProvider }

        public fun setCredentialsProvider(credentialsProvider: SharedCredentialsProvider?): Builder = apply { this.credentialsProvider = credentialsProvider }

        public fun tokenProvider(tokenProvider: SharedTokenProvider?): Builder = apply { this.tokenProvider = tokenProvider }

        public fun setTokenProvider(tokenProvider: SharedTokenProvider?): Builder = apply { this.tokenProvider = tokenProvider }

        public fun appName(appName: AppName?): Builder = apply { this.appName = appName }

        public fun setAppName(appName: AppName?): Builder = apply { this.appName = appName }

        public fun httpClient(httpClient: SharedHttpClient?): Builder = apply { this.httpClient = httpClient }

        public fun setHttpClient(httpClient: SharedHttpClient?): Builder = apply { this.httpClient = httpClient }

        public fun useFips(useFips: Boolean?): Builder = apply { this.useFips = useFips }

        public fun setUseFips(useFips: Boolean?): Builder = apply { this.useFips = useFips }

        public fun useDualStack(useDualStack: Boolean?): Builder = apply { this.useDualStack = useDualStack }

        public fun setUseDualStack(useDualStack: Boolean?): Builder = apply { this.useDualStack = useDualStack }

        public fun timeSource(timeSource: SharedTimeSource?): Builder = apply { this.timeSource = timeSource }

        public fun setTimeSource(timeSource: SharedTimeSource?): Builder = apply { this.timeSource = timeSource }

        public fun disableRequestCompression(disable: Boolean?): Builder = apply { this.disableRequestCompression = disable }

        public fun setDisableRequestCompression(disable: Boolean?): Builder = apply { this.disableRequestCompression = disable }

        public fun requestMinCompressionSizeBytes(bytes: Long?): Builder = apply { this.requestMinCompressionSizeBytes = bytes }

        public fun setRequestMinCompressionSizeBytes(bytes: Long?): Builder = apply { this.requestMinCompressionSizeBytes = bytes }

        public fun behaviorVersion(version: BehaviorVersion?): Builder = apply { this.behaviorVersion = version }

        public fun setBehaviorVersion(version: BehaviorVersion?): Builder = apply { this.behaviorVersion = version }

        public fun serviceConfig(serviceConfig: LoadServiceConfig?): Builder = apply { this.serviceConfig = serviceConfig }

        public fun setServiceConfig(serviceConfig: LoadServiceConfig?): Builder = apply { this.serviceConfig = serviceConfig }

        public fun authSchemePreference(pref: AuthSchemePreference?): Builder = apply { this.authSchemePreference = pref }

        public fun setAuthSchemePreference(pref: AuthSchemePreference?): Builder = apply { this.authSchemePreference = pref }

        public fun insertOrigin(setting: String, origin: Origin) {
            configOrigins[setting] = origin
        }

        public fun stalledStreamProtection(config: StalledStreamProtectionConfig?): Builder = apply { this.stalledStreamProtectionConfig = config }

        public fun setStalledStreamProtection(config: StalledStreamProtectionConfig?): Builder = apply { this.stalledStreamProtectionConfig = config }

        public fun build(): SdkConfig =
            SdkConfig(
                appName = appName,
                authSchemePreference = authSchemePreference,
                identityCache = identityCache,
                credentialsProvider = credentialsProvider,
                tokenProvider = tokenProvider,
                region = region,
                accountIdEndpointMode = accountIdEndpointMode,
                endpointUrl = endpointUrl,
                retryConfig = retryConfig,
                sleepImpl = sleepImpl,
                timeSource = timeSource,
                timeoutConfig = timeoutConfig,
                stalledStreamProtectionConfig = stalledStreamProtectionConfig,
                httpClient = httpClient,
                useFips = useFips,
                useDualStack = useDualStack,
                behaviorVersion = behaviorVersion,
                serviceConfig = serviceConfig,
                configOrigins = configOrigins.toMap(),
                disableRequestCompression = disableRequestCompression,
                requestMinCompressionSizeBytes = requestMinCompressionSizeBytes,
                requestChecksumCalculation = requestChecksumCalculation,
                responseChecksumValidation = responseChecksumValidation,
            )
    }

    public companion object {
        public fun builder(): Builder = Builder()
    }
}
