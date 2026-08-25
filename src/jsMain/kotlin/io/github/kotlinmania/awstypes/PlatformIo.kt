// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

internal actual fun platformGetEnv(key: String): String? =
    try {
        val env = js("typeof process !== 'undefined' && process.env ? process.env : {}")
        env[key] as? String
    } catch (_: Throwable) {
        null
    }

internal actual fun platformReadFile(path: String): ByteArray =
    try {
        val fs = js("require('fs')")
        val buffer = fs.readFileSync(path)
        val uint8 = js("new Uint8Array(buffer.buffer, buffer.byteOffset, buffer.length)")
        val size = uint8.length as Int
        val bytes = ByteArray(size)
        for (i in 0 until size) {
            bytes[i] = (uint8[i] as Number).toByte()
        }
        bytes
    } catch (e: Throwable) {
        throw FsFileNotFoundException("File not found: $path: ${e.message}")
    }

internal actual fun platformWriteFile(path: String, contents: ByteArray) {
    try {
        val fs = js("require('fs')")
        val uint8 = js("new Uint8Array(contents.length)")
        for (i in contents.indices) {
            uint8[i] = contents[i]
        }
        val buffer = js("Buffer.from(uint8.buffer, uint8.byteOffset, uint8.byteLength)")
        fs.writeFileSync(path, buffer)
    } catch (e: Throwable) {
        throw FsFileNotFoundException("Cannot write file: $path: ${e.message}")
    }
}
