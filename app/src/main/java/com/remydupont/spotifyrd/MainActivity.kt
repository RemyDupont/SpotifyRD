package com.remydupont.spotifyrd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.remydupont.spotifyrd.network.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.spotify.sdk.android.player.*
import com.spotify.sdk.android.player.Player




class MainActivity : AppCompatActivity(), ConnectionStateCallback, Player.NotificationCallback {
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


    private var mPlayer: Player? = null

    // TODO: Replace with your client ID
    private val CLIENT_ID = "70c6fb9b8338428293ea6160323f2190"

    // TODO: Replace with your redirect URI
    private val REDIRECT_URI = "https://remydupont.com/callbcak"

    override fun onLoggedOut() {
        Log.d("MainActivity", "User logged out")
    }

    override fun onLoggedIn() {
        Log.d("MainActivity", "User logged in")
        mPlayer?.playUri(null, "spotify:track:2TpxZ7JUBn3uw46aR7qd6V", 0, 0)
    }

    override fun onConnectionMessage(message: String?) {
        Log.d("MainActivity", "Received connection message: " + message)
    }

    override fun onLoginFailed(error: Error?) {
        Log.d("MainActivity", "Login failed")
        Log.d("MainActivity", error.toString())
    }

    override fun onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        token_btn.setOnClickListener {
            val builder = AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
            builder.setScopes(arrayOf("user-read-private", "streaming"))
            val request = builder.build()

            AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type == AuthenticationResponse.Type.TOKEN) {
                val playerConfig = Config(this, response.accessToken, CLIENT_ID)
                Spotify.getPlayer(playerConfig, this, object : SpotifyPlayer.InitializationObserver {
                    override fun onInitialized(spotifyPlayer: SpotifyPlayer) {
                        mPlayer = spotifyPlayer
                        mPlayer?.addConnectionStateCallback(this@MainActivity)
                        (mPlayer as SpotifyPlayer).addNotificationCallback(this@MainActivity)
                    }

                    override fun onError(throwable: Throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.message)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        Spotify.destroyPlayer(this)
        super.onDestroy()
    }

}
