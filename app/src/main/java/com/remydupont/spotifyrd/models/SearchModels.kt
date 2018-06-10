package com.remydupont.spotifyrd.models

/**
 * SearchModels
 *
 * Created by remydupont on 09/06/2018.
 */
data class SearchResponse(
        var artists: ArtistResponse? = null,
        var albums: Albums? = null,
        var playLists: PlaylistResponse? = null
)