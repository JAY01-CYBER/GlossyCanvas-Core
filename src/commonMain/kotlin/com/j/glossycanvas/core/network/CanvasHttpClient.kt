/*
 * ╭────────────────────────────────────────────╮
 * │             Glossy Canvas Core             │
 * │--------------------------------------------│
 * │  Licensed under the GNU GPL v3.0           │
 * │  Crafted for expressive music experience   │
 * ╰────────────────────────────────────────────╯
 */

package com.j.glossycanvas.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal val sharedJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
}

internal val canvasHttpClient = HttpClient {
    install(ContentNegotiation) { 
        json(sharedJson) 
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 4_000
        requestTimeoutMillis = 6_000
        socketTimeoutMillis = 6_000
    }
    install(ContentEncoding) {
        gzip()
        deflate()
    }
    expectSuccess = false
}
