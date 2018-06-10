package com.remydupont.spotifyrd.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.helper.SharedPrefHelper
import com.spotify.sdk.android.authentication.*
import com.spotify.sdk.android.authentication.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        if (SharedPrefHelper.instance.spotifyToken.isNotEmpty()
                && SharedPrefHelper.instance.isTokenValid) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            SharedPrefHelper.instance.deleteSpotifyToken()
            init()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == LoginActivity.REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type == AuthenticationResponse.Type.TOKEN) {
                SharedPrefHelper.instance.storeSpotifyToken(response.accessToken)
                Toast.makeText(this, "Log In :)", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun init() {
        login_button.setOnClickListener {
            val builder = AuthenticationRequest
                    .Builder(getString(R.string.client_id), AuthenticationResponse.Type.TOKEN, getString(R.string.redirect_uri))
            builder.setScopes(arrayOf("user-read-private", "streaming"))
            val request = builder.build()

            AuthenticationClient.openLoginActivity(this, LoginActivity.REQUEST_CODE, request)
        }
    }


}
