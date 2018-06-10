package com.remydupont.spotifyrd.models

/**
 * HeaderItem
 *
 * Created by remydupont on 10/06/2018.
 */
data class HeaderItem(var title: String): ViewType {
    override fun getViewType(): Int {
        return ViewTypeConstants.TYPE_HEADER
    }
}