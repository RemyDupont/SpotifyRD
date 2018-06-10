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
        var items: List<PlayList>? = null,
        var limit: Int? = null,
        var next: String? = null,
        var offset: Int? = null,
        var previous: String? = null,
        var total: Int? = null
)

