// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

internal actual fun platformGetEnv(key: String): String? = null

internal actual fun platformReadFile(path: String): ByteArray = throw FsFileNotFoundException("File not found: $path (file system access not supported on WasmJS target)")

internal actual fun platformWriteFile(path: String, contents: ByteArray): Unit = throw FsFileNotFoundException("Cannot write file: $path (file system access not supported on WasmJS target)")
