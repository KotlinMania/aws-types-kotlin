// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned
import platform.posix.FILE
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fread
import platform.posix.fseek
import platform.posix.ftell
import platform.posix.fwrite
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
        fseek(file, 0, SEEK_END)
        val size = ftell(file)
        fseek(file, 0, SEEK_SET)
        if (size <= 0L) {
            return ByteArray(0)
        }
        val bytes = ByteArray(size.toInt())
        bytes.usePinned { pinned ->
            fread(pinned.addressOf(0), 1.convert(), size.convert(), file)
        }
        return bytes
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
        if (contents.isNotEmpty()) {
            contents.usePinned { pinned ->
                fwrite(pinned.addressOf(0), 1.convert(), contents.size.convert(), file)
            }
        }
    } finally {
        fclose(file)
    }
}
