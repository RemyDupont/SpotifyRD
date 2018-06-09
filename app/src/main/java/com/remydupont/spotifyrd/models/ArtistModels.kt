package com.remydupont.spotifyrd.models

/**
 * ArtistModels
 *
 * Created by remydupont on 09/06/2018.
 */

data class Artist(
        var id: String? = null,
        var external_urls: ExternalUrls? = null,
        var href: String? = null,
        var name: String? = null,
        var type: String? = null,
        var uri: String? = null
)