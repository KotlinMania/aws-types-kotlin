// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

import io.github.kotlinmania.io.buffered
import io.github.kotlinmania.io.files.FileNotFoundException
import io.github.kotlinmania.io.files.Path
import io.github.kotlinmania.io.files.SystemFileSystem
import io.github.kotlinmania.io.readByteArray
import io.github.kotlinmania.libc.unix.getenv

internal actual fun platformGetEnv(key: String): String? = getenv(key)

internal actual fun platformReadFile(path: String): ByteArray {
    val p = Path(path)
    if (!SystemFileSystem.exists(p)) {
        throw FsFileNotFoundException("File not found: $path")
    }
    return try {
        SystemFileSystem.source(p).buffered().use { it.readByteArray() }
    } catch (e: FileNotFoundException) {
        throw FsFileNotFoundException("File not found: $path: ${e.message}")
    } catch (e: Exception) {
        throw FsFileNotFoundException("Cannot read file: $path: ${e.message}")
    }
}

internal actual fun platformWriteFile(path: String, contents: ByteArray) {
    try {
        val p = Path(path)
        p.parent?.let { SystemFileSystem.createDirectories(it) }
        SystemFileSystem.sink(p).buffered().use { it.write(contents) }
    } catch (e: Exception) {
        throw FsFileNotFoundException("Cannot write file: $path: ${e.message}")
    }
}
