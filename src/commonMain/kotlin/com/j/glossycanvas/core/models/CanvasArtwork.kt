/*
 * ╭────────────────────────────────────────────╮
 * │             Glossy Canvas Core             │
 * │--------------------------------------------│
 * │  Licensed under the GNU GPL v3.0           │
 * │  Crafted for expressive music experience   │
 * ╰────────────────────────────────────────────╯
 */

package com.j.glossycanvas.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CanvasArtwork(
    val name: String? = null,
    val artist: String? = null,
    @SerialName("albumId")
    val albumId: String? = null,
    val albumName: String? = null,
    val static: String? = null,
    val animated: String? = null,
    val videoUrl: String? = null,
) {
    val preferredAnimationUrl: String?
        get() = animated ?: videoUrl
}
