package com.remydupont.spotifyrd.listener

import com.remydupont.spotifyrd.models.Track


/**
 * PlayerListener
 *
 * Created by remydupont on 09/06/2018.
 */
interface PlayerListener {
    fun playTrack(track: Track)
    fun pause()
}