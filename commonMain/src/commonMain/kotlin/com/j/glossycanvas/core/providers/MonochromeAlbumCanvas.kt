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
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

object MonochromeAlbumCanvas {
    private const val BASE_URL = "https://artwork.boidu.dev/"

    suspend fun getByAlbumArtist(
        album: String,
        artist: String
    ): CanvasArtwork? {
        return try {
            val response = canvasHttpClient.get(BASE_URL) {
                parameter("s", album)
                parameter("a", artist)
            }
            
            if (response.status == HttpStatusCode.OK) {
                val body = response.body<JsonObject>()
                val videoUrl = body["videoUrl"]?.jsonPrimitive?.contentOrNull
                val animated = body["animated"]?.jsonPrimitive?.contentOrNull
                
                if (!videoUrl.isNullOrBlank() || !animated.isNullOrBlank()) {
                    CanvasArtwork(
                        name = album,
                        artist = artist,
                        videoUrl = videoUrl,
                        animated = animated
                    )
                } else null
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
