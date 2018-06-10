package com.remydupont.spotifyrd.models

/**
 * ViewType
 *
 * Created by remydupont on 10/06/2018.
 */
interface ViewType {
    fun getViewType(): Int
}

object ViewTypeConstants {
    const val TYPE_HEADER = 0
    const val TYPE_ARTIST = 1
    const val TYPE_ALBUM = 2
    const val TYPE_PLAYLIST = 3
    const val TYPE_TRACK = 4
}