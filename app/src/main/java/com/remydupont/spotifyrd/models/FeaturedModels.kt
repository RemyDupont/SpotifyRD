package com.remydupont.spotifyrd.models

/**
 * FeaturedModels
 *
 * Created by remydupont on 09/06/2018.
 */
data class FeaturedPlayListsResponse(
        var message: String? = null,
        var playlists: FeaturedPlayList? = null
)

data class FeaturedPlayList(
        var href: String? = null,
        var items: List<FeaturedItem>? = null,
        var limit: Int? = null,
        var next: String? = null,
        var offset: Int? = null,
        var previous: String? = null,
        var total: Int? = null
)

data class FeaturedItem(
        var collaborative: Boolean? = null,
        var external_urls: ExternalUrls? = null,
        var href: String? = null,
        var id: String? = null,
        var images: List<Image>? = null,
        var name: String? = null,
        var owner: PlayListOwner? = null,
        var public: Any? = null,
        var snapshot_id: String? = null,
        var tracks : PlayListTracks? = null,
        var type: String? = null,
        var uri: String? = null
)

data class PlayListOwner(
        var external_urls: ExternalUrls? = null,
        var href: String? = null,
        var id: String? = null,
        var type: String? = null,
        var uri: String? = null
)

data class PlayListTracks(
        var href: String? = null,
        var total: Int? = null
)

