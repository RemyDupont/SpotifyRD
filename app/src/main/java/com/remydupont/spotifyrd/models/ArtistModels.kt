package com.remydupont.spotifyrd.models


/**
 * ArtistModels
 *
 * Created by remydupont on 09/06/2018.
 */
data class ArtistResponse(
        var href: String? = null,
        var items: List<Artist>? = null,
        var limit: Int? = null,
        var next: String? = null,
        var offset: Int? = null,
        var previous: String? = null,
        var total: Int? = null
)

data class Artist(
        var id: String? = null,
        var external_urls: ExternalUrls? = null,
        var href: String? = null,
        var name: String? = null,
        var images: List<Image>? = null,
        var type: String? = null,
        var uri: String? = null,
        var popularity: Int? = null,
        var genres: List<String>? = null,
        var followers: Follower? = null
): ViewType {
    override fun getViewType(): Int {
        return ViewTypeConstants.TYPE_ARTIST
    }
}
