package com.remydupont.spotifyrd.models

/**
 * TrackModels
 *
 * Created by remydupont on 09/06/2018.
 */

data class Track(
        var album: Album? = null,
        var artists: List<Artist>? = null,
        var available_markets: List<String>? = null,
        var disc_number: Int? = null,
        var duration_ms: Int? = null,
        var explicit: Boolean? = null,
        var external_ids: ExternalIds? = null,
        var external_urls: ExternalUrls? = null,
        var href: String? = null,
        var id: String? = null,
        var is_local: Boolean? = null,
        var name: String? = null,
        var popularity: Int? = null,
        var preview_url: String? = null,
        var track_number: Int? = null,
        var type: String? = null,
        var uri: String? = null
): ViewType {
    override fun getViewType(): Int {
        return ViewTypeConstants.TYPE_TRACK
    }
}