// port-lint: source service_config.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * A struct used with the [LoadServiceConfig] interface to extract service config from the user's environment.
 *
 * Example configuration structure:
 * ```ini
 * [profile active-profile]
 * services = dev
 *
 * [services dev]
 * service-id =
 *   config-key = config-value
 * ```
 */
public data class ServiceConfigKey(
    public val serviceId: String,
    public val profile: String,
    public val env: String,
) {
    public class Builder {
        private var serviceId: String? = null
        private var profile: String? = null
        private var env: String? = null

        /** Set the service ID. */
        public fun serviceId(serviceId: String): Builder = apply { this.serviceId = serviceId }

        /** Set the profile key. */
        public fun profile(profile: String): Builder = apply { this.profile = profile }

        /** Set the environment key. */
        public fun env(env: String): Builder = apply { this.env = env }

        /**
         * Build the [ServiceConfigKey].
         *
         * Throws [ServiceConfigKeyError] if any of the required fields are missing.
         */
        public fun build(): ServiceConfigKey {
            val sId = serviceId ?: throw ServiceConfigKeyError("couldn't build a ServiceEnvConfigKey: missing required service-id")
            val prof = profile ?: throw ServiceConfigKeyError("couldn't build a ServiceEnvConfigKey: missing required active profile name")
            val e = env ?: throw ServiceConfigKeyError("couldn't build a ServiceEnvConfigKey: missing required environment variable name")
            return ServiceConfigKey(serviceId = sId, profile = prof, env = e)
        }
    }

    public companion object {
        /** Create a new [ServiceConfigKey] builder struct. */
        public fun builder(): Builder = Builder()
    }
}

/**
 * Error type for [ServiceConfigKey.Builder].
 */
public class ServiceConfigKeyError(
    message: String,
) : IllegalArgumentException(message)

/**
 * Implementers of this interface can provide service config defined in a user's environment.
 */
public fun interface LoadServiceConfig {
    /**
     * Given a [ServiceConfigKey], return the value associated with it.
     */
    public fun loadConfig(key: ServiceConfigKey): String?
}
