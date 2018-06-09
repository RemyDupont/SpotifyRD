package com.remydupont.spotifyrd.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.helper.SharedPrefHelper
import com.spotify.sdk.android.player.*
import com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.LoginActivity


/**
 *      BaseActivity
 *
 * Created by remydupont on 09/06/2018.
 */
open class BaseActivity: AppCompatActivity(), Player.NotificationCallback, ConnectionStateCallback {

    open var mPlayer: Player? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            Log.e("BaseActivity", response.code)
            if (response.type == AuthenticationResponse.Type.TOKEN) {
                SharedPrefHelper.getInstance().storeSpotifyToken(response.accessToken)
                Log.d("SpotifyLogin",
                        "code: ${response.code}, error: ${response.error},expirein: ${response.expiresIn}, state: ${response.state}, type: ${response.type}")
            }
        }
    }

    override fun onDestroy() {
        Spotify.destroyPlayer(this)
        super.onDestroy()
    }


    /**
     * Open Functions
     */
    open fun initPlayer() {
        val playerConfig = Config(this, SharedPrefHelper.getInstance().spotifyToken, getString(R.string.client_id))
        Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
            override fun onInitialized(spotifyPlayer: SpotifyPlayer) {
                mPlayer = spotifyPlayer
                (mPlayer as? SpotifyPlayer)?.addConnectionStateCallback(this@BaseActivity)
                (mPlayer as? SpotifyPlayer)?.addNotificationCallback(this@BaseActivity)
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
        Log.d("MainActivity", "Login failed aaa")
        Log.d("MainActivity", error.toString())
        val request = AuthenticationRequest
                .Builder(getString(R.string.client_id), AuthenticationResponse.Type.CODE,  getString(R.string.redirect_uri))
                .setScopes(arrayOf("user-read-private", "streaming"))
                .build()
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    override fun onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred")
    }


    override fun onPlaybackError(error: Error?) {
        Log.d("MainActivity", "Playback error received: " + error?.name)
        when (error) {
        // Handle error type as necessary
            else -> {
            }
        }    }

    override fun onPlaybackEvent(playerEvent: PlayerEvent?) {
        Log.d("MainActivity", "Playback event received: " + playerEvent?.name)
        when (playerEvent) {
        // Handle event type as necessary
            else -> {
            }
        }    }


}