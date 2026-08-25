// port-lint: source os_shim_internal.rs
package io.github.kotlinmania.awstypes

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

/**
 * Abstractions for testing code that interacts with the operating system:
 * - Reading environment variables
 * - Reading from the file system
 */

/**
 * Environment variable abstraction.
 *
 * Enables loading environment variables either from the actual process environment or from a map.
 */
public class Env private constructor(
    private val provider: (String) -> String?,
) {
    /**
     * Retrieve a value for the given key, or return null if not present.
     */
    public fun get(key: String): String? = provider(key)

    public companion object {
        private var customRealProvider: ((String) -> String?)? = null

        /**
         * Create an [Env] that delegates to the real process environment.
         */
        public fun real(): Env =
            Env { key ->
                customRealProvider?.invoke(key) ?: platformGetEnv(key)
            }

        /**
         * Create an [Env] from a map.
         */
        public fun fromMap(map: Map<String, String>): Env = Env { map[it] }

        /**
         * Create an [Env] from pairs of (key, value).
         */
        public fun fromSlice(vararg vars: Pair<String, String>): Env = fromMap(vars.toMap())

        /**
         * Create an [Env] from a list of pairs.
         */
        public fun fromList(vars: List<Pair<String, String>>): Env = fromMap(vars.toMap())

        /**
         * Sets a custom provider for [real] during tests.
         */
        internal fun setCustomRealProviderForTesting(provider: ((String) -> String?)?) {
            customRealProvider = provider
        }
    }
}

/**
 * File system abstraction.
 *
 * Simple abstraction enabling in-memory mocking of the file system.
 */
public class Fs private constructor(
    private val reader: (String) -> ByteArray,
    private val writer: (String, ByteArray) -> Unit,
) {
    /**
     * Read the entire contents of a file as a byte array.
     */
    public fun readToEnd(path: String): ByteArray = reader(path)

    /**
     * Read the entire contents of a file as a UTF-8 string.
     */
    public fun readUtf8(path: String): String = readToEnd(path).decodeToString()

    /**
     * Write bytes as the entire contents of a file.
     */
    public fun write(path: String, contents: ByteArray) {
        writer(path, contents)
    }

    /**
     * Write a UTF-8 string as the entire contents of a file.
     */
    public fun writeUtf8(path: String, contents: String) {
        write(path, contents.encodeToByteArray())
    }

    public companion object {
        /**
         * Create [Fs] representing a real file system.
         */
        public fun real(): Fs =
            Fs(
                reader = { path -> platformReadFile(path) },
                writer = { path, contents -> platformWriteFile(path, contents) },
            )

        /**
         * Create [Fs] from a map of file path to byte array.
         */
        public fun fromMap(data: Map<String, ByteArray>): Fs {
            val map = data.toMutableMap()
            return Fs(
                reader = { path ->
                    val norm = normalizePath(path)
                    map[norm] ?: map[path] ?: throw FsFileNotFoundException("File not found: $path")
                },
                writer = { path, contents ->
                    val norm = normalizePath(path)
                    map[norm] = contents
                },
            )
        }

        /**
         * Create [Fs] from pairs of (path, content string).
         */
        public fun fromSlice(vararg files: Pair<String, String>): Fs {
            val map = files.associate { (k, v) -> normalizePath(k) to v.encodeToByteArray() }
            return fromMap(map)
        }

        /**
         * Create [Fs] from a list of pairs of (path, content string).
         */
        public fun fromList(files: List<Pair<String, String>>): Fs {
            val map = files.associate { (k, v) -> normalizePath(k) to v.encodeToByteArray() }
            return fromMap(map)
        }

        /**
         * Create a test filesystem rooted in real files.
         */
        public fun fromTestDir(testDirectory: String, namespacedTo: String): Fs {
            val normNamespace = normalizePath(namespacedTo)
            val normTestDir = normalizePath(testDirectory)
            return Fs(
                reader = { path ->
                    val norm = normalizePath(path)
                    if (!norm.startsWith(normNamespace)) {
                        throw FsFileNotFoundException("Path $path is outside namespace $namespacedTo")
                    }
                    val rel = norm.removePrefix(normNamespace).trimStart('/')
                    val target = if (normTestDir.isEmpty() || normTestDir == ".") rel else "$normTestDir/$rel"
                    platformReadFile(target)
                },
                writer = { path, contents ->
                    val norm = normalizePath(path)
                    if (!norm.startsWith(normNamespace)) {
                        throw FsFileNotFoundException("Path $path is outside namespace $namespacedTo")
                    }
                    val rel = norm.removePrefix(normNamespace).trimStart('/')
                    val target = if (normTestDir.isEmpty() || normTestDir == ".") rel else "$normTestDir/$rel"
                    platformWriteFile(target, contents)
                },
            )
        }

        private fun normalizePath(p: String): String = p.replace('\\', '/').trimEnd('/')
    }
}

/**
 * Exception thrown when a file is not found in [Fs].
 */
public class FsFileNotFoundException(
    message: String,
) : Exception(message)

internal expect fun platformGetEnv(key: String): String?

internal expect fun platformReadFile(path: String): ByteArray

internal expect fun platformWriteFile(path: String, contents: ByteArray)
