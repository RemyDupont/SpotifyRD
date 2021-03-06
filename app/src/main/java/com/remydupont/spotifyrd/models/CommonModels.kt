package com.remydupont.spotifyrd.models


/**
 * CommonModels
 *
 * Created by remydupont on 09/06/2018.
 */


data class ExternalUrls(
        var spotify: String? = null
)


data class ExternalIds(
        var isrc: String? = null
)


data class Image(
        var url: String? = null,
        var width: Int? = null,
        var height: Int? = null
)


data class Follower(
        var href: String? = null,
        var total: Int? = null
)