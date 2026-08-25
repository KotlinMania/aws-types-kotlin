// port-lint: tests request_id.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RequestIdTest {
    @Test
    fun testExtractRequestId() {
        val headers = Headers.new()
        assertNull(headers.requestId())

        headers.append("x-amzn-requestid", "some-request-id")
        assertEquals("some-request-id", headers.requestId())

        headers.append("x-amz-request-id", "other-request-id")
        assertEquals("some-request-id", headers.requestId())

        headers.remove("x-amzn-requestid")
        assertEquals("other-request-id", headers.requestId())
    }

    @Test
    fun testApplyRequestId() {
        val headers = Headers.new()
        assertEquals(
            ErrorMetadata.builder().build(),
            applyRequestId(ErrorMetadata.builder(), headers).build(),
        )

        headers.append("x-amzn-requestid", "some-request-id")
        val expected =
            ErrorMetadata
                .builder()
                .custom(AWS_REQUEST_ID, "some-request-id")
                .build()
        assertEquals(
            expected,
            applyRequestId(ErrorMetadata.builder(), headers).build(),
        )
    }

    @Test
    fun testErrorMetadataRequestIdImpl() {
        val err =
            ErrorMetadata
                .builder()
                .custom(AWS_REQUEST_ID, "some-request-id")
                .build()
        assertEquals("some-request-id", err.requestId())
    }

    @Test
    fun testRequestIdSdkError() {
        val headersWithout = Headers.new()
        val withoutRequestId = HttpResponse(statusCode = 200, headers = headersWithout, body = "")

        val headersWith = Headers.new().apply { append("x-amzn-requestid", "some-request-id") }
        val withRequestId = HttpResponse(statusCode = 200, headers = headersWith, body = "")

        val errRespWithout = SdkError.ResponseError("test", withoutRequestId)
        assertEquals(null, errRespWithout.requestId())

        val errRespWith = SdkError.ResponseError("test", withRequestId)
        assertEquals("some-request-id", errRespWith.requestId())

        val errServWithout = SdkError.ServiceError("service error", withoutRequestId)
        assertEquals(null, errServWithout.requestId())

        val errServWith = SdkError.ServiceError("service error", withRequestId)
        assertEquals("some-request-id", errServWith.requestId())
    }
}
