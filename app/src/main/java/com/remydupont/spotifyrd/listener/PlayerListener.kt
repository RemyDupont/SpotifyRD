package com.remydupont.spotifyrd.listener

/**
 * PlayerListener
 *
 * Created by remydupont on 09/06/2018.
 */
interface PlayerListener {
    fun playTrack(track: String)
    fun pause()
}