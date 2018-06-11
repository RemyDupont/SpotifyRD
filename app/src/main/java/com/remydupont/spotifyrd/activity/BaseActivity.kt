package com.remydupont.spotifyrd.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.longToast
import com.remydupont.spotifyrd.helper.PlayerHelper
import com.remydupont.spotifyrd.helper.SharedPrefHelper
import com.spotify.sdk.android.player.*


/**
 *      BaseActivity
 *
 * Created by remydupont on 09/06/2018.
 */
open class BaseActivity: AppCompatActivity(), Player.NotificationCallback, ConnectionStateCallback {

    override fun onDestroy() {
        Spotify.destroyPlayer(this)
        super.onDestroy()
    }


    /**
     * Open Functions
     */
    open fun initPlayer() {
        val playerConfig = Config(this, SharedPrefHelper.instance.spotifyToken, getString(R.string.client_id))
        Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
            override fun onInitialized(spotifyPlayer: SpotifyPlayer) {
                PlayerHelper.instance.player = spotifyPlayer

                val player = PlayerHelper.instance.player
                (player as? SpotifyPlayer)?.addConnectionStateCallback(this@BaseActivity)
                (player as? SpotifyPlayer)?.addNotificationCallback(this@BaseActivity)
            }

            override fun onError(throwable: Throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.message)
            }
        })
    }



    /**
     * Interfaces Implementations
     */
    override fun onLoggedOut() {
        Log.d("MainActivity", "User logged out")
    }

    override fun onLoggedIn() {
        Log.d("MainActivity", "User logged in")

    }

    override fun onConnectionMessage(message: String?) {
        Log.d("MainActivity", "Received connection message: $message")
    }

    override fun onLoginFailed(error: Error?) {
        longToast(R.string.session_expired)
        startActivity(Intent(this, com.remydupont.spotifyrd.activity.LoginActivity::class.java))
        finish()
    }

    override fun onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred")
    }


    override fun onPlaybackError(error: Error?) {
        Log.d("MainActivity", "Playback error received: " + error?.name)
    }

    override fun onPlaybackEvent(playerEvent: PlayerEvent?) {
        Log.d("MainActivity", "Playback event received: " + playerEvent?.name)
    }


}