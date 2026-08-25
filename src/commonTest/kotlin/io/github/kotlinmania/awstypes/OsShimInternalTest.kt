// port-lint: tests os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class OsShimInternalTest {
    @Test
    fun envWorks() {
        val env = Env.fromSlice("FOO" to "BAR")
        assertEquals("BAR", env.get("FOO"))
        assertNull(env.get("OTHER"))
    }

    @Test
    fun envFromSlice() {
        val env = Env.fromSlice("foo" to "bar")
        assertEquals("bar", env.get("foo"))
        assertNull(env.get("bar"))
    }

    @Test
    fun envFromListAndMap() {
        val env = Env.fromList(listOf("k1" to "v1", "k2" to "v2"))
        assertEquals("v1", env.get("k1"))
        assertEquals("v2", env.get("k2"))
        assertNull(env.get("k3"))
    }

    @Test
    fun testFsMock() {
        val fs = Fs.fromSlice("foo" to "bar")
        assertEquals("bar", fs.readToEnd("foo").decodeToString())
        assertEquals("bar", fs.readUtf8("foo"))

        assertFailsWith<FsFileNotFoundException> {
            fs.readToEnd("bar")
        }

        fs.writeUtf8("baz", "hello world")
        assertEquals("hello world", fs.readUtf8("baz"))
    }

    @Test
    fun testFsFromMap() {
        val fs = Fs.fromMap(mapOf("/path/to/file.txt" to "contents".encodeToByteArray()))
        assertEquals("contents", fs.readUtf8("/path/to/file.txt"))
        assertEquals("contents", fs.readUtf8("\\path\\to\\file.txt"))
    }

    @Test
    fun fsFromTestDirWorks() {
        val fs = Fs.fromMap(mapOf("/users/test-data/sample.txt" to "data".encodeToByteArray()))
        assertEquals("data", fs.readUtf8("/users/test-data/sample.txt"))
        assertFailsWith<FsFileNotFoundException> {
            fs.readToEnd("doesntexist")
        }
    }

    @Test
    fun fsRoundTripFileWithReal() {
        val fs = Fs.fromMap(emptyMap())
        assertFailsWith<FsFileNotFoundException> {
            fs.readToEnd("test-file")
        }
        fs.write("test-file", "test".encodeToByteArray())
        val result = fs.readToEnd("test-file")
        assertEquals("test", result.decodeToString())
    }
}
