package com.remydupont.spotifyrd.models

/**
 * AlbumModels
 *
 * Created by remydupont on 09/06/2018.
 */
data class AlbumResponse(
        var albums: Albums? = null
)

data class Albums(
        var href: String? = null,
        var items: List<Album>? = null,
        var limit: Int? = null,
        var next: String? = null,
        var offset: Int? = null,
        var previous: String? = null,
        var total: Int? = null
)

data class Album(
        var album_type: String? = null,
        var artists: List<Artist>? = null,
        var available_markets: List<String>? = null,
        var external_urls: ExternalUrls? = null,
        var href: String? = null,
        var id: String? = null,
        var images: List<Image>? = null,
        var name: String? = null,
        var release_date: String? = null,
        var release_date_precision: String? = null,
        var type: String? = null,
        var uri: String? = null
): ViewType {
    override fun getViewType(): Int {
        return ViewTypeConstants.TYPE_ALBUM
    }
}
