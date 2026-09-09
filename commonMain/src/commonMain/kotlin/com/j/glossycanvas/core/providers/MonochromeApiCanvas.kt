/*
 * ╭────────────────────────────────────────────╮
 * │             Glossy Canvas Core             │
 * │--------------------------------------------│
 * │  Licensed under the GNU GPL v3.0           │
 * │  Crafted for expressive music experience   │
 * ╰────────────────────────────────────────────╯
 */

package com.j.glossycanvas.core.providers

import com.j.glossycanvas.core.models.CanvasArtwork
import com.j.glossycanvas.core.network.canvasHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.*

object MonochromeApiCanvas {
    private val INSTANCES = listOf(
        "https://api.monochrome.tf/",
        "https://monochrome-api.samidy.com/"
    )

    suspend fun getBySongArtist(
        song: String,
        artist: String,
        album: String? = null
    ): CanvasArtwork? {
        val query = if (!album.isNullOrBlank()) "$artist - $song - $album" else "$artist - $song"
        
        return coroutineScope {
            val deferredResults = INSTANCES.map { baseUrl ->
                async { fetchFromInstance(baseUrl, query, song, artist) }
            }
            deferredResults.awaitAll().firstOrNull { it != null }
        }
    }

    private suspend fun fetchFromInstance(
        baseUrl: String,
        query: String,
        song: String,
        artist: String
    ): CanvasArtwork? {
        return try {
            val response = canvasHttpClient.get("${baseUrl}search/") {
                parameter("s", query)
            }
            if (response.status != HttpStatusCode.OK) return null

            val root = response.body<JsonObject>()
            val tracksSection = findSearchSection(root, "tracks") ?: return null
            val items = tracksSection.jsonObject["items"]?.jsonArray ?: return null

            for (item in items) {
                val track = item.jsonObject
                val trackTitle = track["title"]?.jsonPrimitive?.contentOrNull
                
                if (trackTitle != null && !trackTitle.contains(song, ignoreCase = true)) continue

                val artists = track["artists"]?.jsonArray
                val resultArtist = artists?.firstOrNull()?.jsonObject?.get("name")?.jsonPrimitive?.contentOrNull
                if (resultArtist != null && !resultArtist.contains(artist, ignoreCase = true) && !artist.contains(resultArtist, ignoreCase = true)) continue

                val albumObj = track["album"]?.jsonObject ?: continue
                val videoCover = albumObj["videoCover"]?.jsonPrimitive?.contentOrNull
                
                if (!videoCover.isNullOrBlank()) {
                    val videoUrl = formatVideoUrl(videoCover)
                    if (videoUrl != null) {
                        return CanvasArtwork(
                            name = trackTitle ?: song,
                            artist = resultArtist ?: artist,
                            videoUrl = videoUrl
                        )
                    }
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    private fun findSearchSection(source: JsonElement, key: String): JsonElement? {
        if (source is JsonObject) {
            if (source.containsKey("items") && source["items"] is JsonArray) return source
            if (source.containsKey(key)) {
                val found = findSearchSection(source[key]!!, key)
                if (found != null) return found
            }
            for (value in source.values) {
                val found = findSearchSection(value, key)
                if (found != null) return found
            }
        } else if (source is JsonArray) {
            for (element in source) {
                val found = findSearchSection(element, key)
                if (found != null) return found
            }
        }
        return null
    }

    private fun formatVideoUrl(id: String): String? {
        val parts = id.split("-")
        if (parts.size != 5) return null
        return "https://resources.tidal.com/videos/${parts[0]}/${parts[1]}/${parts[2]}/${parts[3]}/${parts[4]}/1280x1280.mp4"
    }
}
