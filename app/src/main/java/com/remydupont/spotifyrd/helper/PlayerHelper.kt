package com.remydupont.spotifyrd.helper

import com.spotify.sdk.android.player.Player


/**
 * PlayerHelper
 *
 * Created by remydupont on 11/06/2018.
 */
class PlayerHelper {

    private object Holder { val INSTANCE = PlayerHelper() }

    companion object {
        val instance: PlayerHelper by lazy {
            Holder.INSTANCE
        }
    }

    var player: Player? = null




}