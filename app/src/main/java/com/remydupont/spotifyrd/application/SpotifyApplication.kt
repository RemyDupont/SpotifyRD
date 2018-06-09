package com.remydupont.spotifyrd.application

import android.app.Application

/**
 * SpotifyApplication
 *
 * Created by remydupont on 08/06/2018.
 */
class SpotifyApplication: Application() {

    companion object {
        lateinit var instance: SpotifyApplication
    }

    init {
        instance = this
    }

    fun getContext(): SpotifyApplication {
        return instance
    }
}