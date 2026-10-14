// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.EOF
import platform.posix.FILE
import platform.posix.fclose
import platform.posix.fgetc
import platform.posix.fopen
import platform.posix.fputc
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
internal actual fun platformGetEnv(key: String): String? = getenv(key)?.toKString()

@OptIn(ExperimentalForeignApi::class)
internal actual fun platformReadFile(path: String): ByteArray {
    val file: CPointer<FILE>? = fopen(path, "rb")
    if (file == null) {
        throw FsFileNotFoundException("File not found: $path")
    }
    try {
        val bytes = ArrayList<Byte>()
        while (true) {
            val c = fgetc(file)
            if (c == EOF) break
            bytes.add(c.toByte())
        }
        return bytes.toByteArray()
    } finally {
        fclose(file)
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun platformWriteFile(path: String, contents: ByteArray) {
    val file: CPointer<FILE>? = fopen(path, "wb")
    if (file == null) {
        throw FsFileNotFoundException("Cannot open file for writing: $path")
    }
    try {
        for (b in contents) {
            val res = fputc(b.toInt() and 0xFF, file)
            if (res == EOF) {
                throw FsFileNotFoundException("Failed to write to file: $path")
            }
        }
    } finally {
        fclose(file)
    }
}
