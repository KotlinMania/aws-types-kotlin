// port-lint: source request_id.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * Constant for the [ErrorMetadata] extra field that contains the request ID.
 */
public const val AWS_REQUEST_ID: String = "aws_request_id"

/**
 * Implementers add a function to return an AWS request ID.
 */
public interface RequestId {
    /**
     * Returns the request ID, or null if the service could not be reached.
     */
    public fun requestId(): String?
}

/**
 * HTTP headers collection supporting case-insensitive lookup.
 */
public class Headers(
    initialHeaders: Map<String, List<String>> = emptyMap(),
) : RequestId {
    private val headers: MutableMap<String, MutableList<String>> = mutableMapOf()

    init {
        for ((key, values) in initialHeaders) {
            headers[key.lowercase()] = values.toMutableList()
        }
    }

    /**
     * Gets the first header value matching [name] (case-insensitive).
     */
    public fun get(name: String): String? = headers[name.lowercase()]?.firstOrNull()

    /**
     * Gets all header values matching [name] (case-insensitive).
     */
    public fun getAll(name: String): List<String> = headers[name.lowercase()] ?: emptyList()

    /**
     * Appends a header value.
     */
    public fun append(name: String, value: String) {
        headers.getOrPut(name.lowercase()) { mutableListOf() }.add(value)
    }

    /**
     * Sets a header value, replacing any existing values.
     */
    public fun set(name: String, value: String) {
        headers[name.lowercase()] = mutableListOf(value)
    }

    /**
     * Removes all headers with the given [name] (case-insensitive).
     */
    public fun remove(name: String): List<String>? = headers.remove(name.lowercase())

    override fun requestId(): String? = get("x-amzn-requestid") ?: get("x-amz-request-id")

    override fun equals(other: Any?): Boolean = other is Headers && headers == other.headers

    override fun hashCode(): Int = headers.hashCode()

    public companion object {
        public fun new(): Headers = Headers()
    }
}

/**
 * Error metadata providing structured access to error details.
 */
public data class ErrorMetadata(
    public val code: String? = null,
    public val message: String? = null,
    public val extra: Map<String, String> = emptyMap(),
) : RequestId {
    override fun requestId(): String? = extra[AWS_REQUEST_ID]

    /**
     * Retrieves an extra metadata property.
     */
    public fun extra(key: String): String? = extra[key]

    public class Builder {
        private var code: String? = null
        private var message: String? = null
        private val extra: MutableMap<String, String> = mutableMapOf()

        public fun code(code: String): Builder = apply { this.code = code }

        public fun message(message: String): Builder = apply { this.message = message }

        public fun custom(key: String, value: String): Builder = apply { this.extra[key] = value }

        public fun build(): ErrorMetadata = ErrorMetadata(code = code, message = message, extra = extra.toMap())
    }

    public companion object {
        public fun builder(): Builder = Builder()
    }
}

/**
 * HTTP response containing headers and a body.
 */
public class HttpResponse<B>(
    public val statusCode: Int = 200,
    public val headers: Headers = Headers(),
    public val body: B,
) : RequestId {
    override fun requestId(): String? = headers.requestId()
}

/**
 * Result error from an SDK operation.
 */
public sealed class SdkError<out E, out R> : RequestId {
    public data class ConstructionFailure(
        val causeException: Throwable,
    ) : SdkError<Nothing, Nothing>() {
        public val cause: Throwable get() = causeException

        override fun requestId(): String? = null
    }

    public data class TimeoutError(
        val causeException: Throwable,
    ) : SdkError<Nothing, Nothing>() {
        public val cause: Throwable get() = causeException

        override fun requestId(): String? = null
    }

    public data class DispatchFailure(
        val causeException: Throwable,
    ) : SdkError<Nothing, Nothing>() {
        public val cause: Throwable get() = causeException

        override fun requestId(): String? = null
    }

    public data class ResponseError<R : RequestId>(
        val reason: String,
        val raw: R,
    ) : SdkError<Nothing, R>() {
        public val message: String get() = reason

        override fun requestId(): String? = raw.requestId()
    }

    public data class ServiceError<E, R : RequestId>(
        val err: E,
        val raw: R,
    ) : SdkError<E, R>() {
        override fun requestId(): String? = raw.requestId()
    }
}

/**
 * Applies a request ID to a generic error builder.
 */
public fun applyRequestId(builder: ErrorMetadata.Builder, headers: Headers): ErrorMetadata.Builder {
    val reqId = headers.requestId()
    return if (reqId != null) {
        builder.custom(AWS_REQUEST_ID, reqId)
    } else {
        builder
    }
}
