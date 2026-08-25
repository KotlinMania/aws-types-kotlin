// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import java.io.File
import java.io.FileNotFoundException

internal actual fun platformGetEnv(key: String): String? = System.getenv(key)

internal actual fun platformReadFile(path: String): ByteArray {
    val file = File(path)
    if (!file.exists() || !file.isFile) {
        throw FsFileNotFoundException("File not found: $path")
    }
    return try {
        file.readBytes()
    } catch (e: FileNotFoundException) {
        throw FsFileNotFoundException("File not found: $path: ${e.message}")
    }
}

internal actual fun platformWriteFile(path: String, contents: ByteArray) {
    val file = File(path)
    file.parentFile?.mkdirs()
    file.writeBytes(contents)
}
