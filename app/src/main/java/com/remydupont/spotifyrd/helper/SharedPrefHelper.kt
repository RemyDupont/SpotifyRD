package com.remydupont.spotifyrd.helper

import android.content.Context
import android.content.SharedPreferences
import com.remydupont.spotifyrd.application.SpotifyApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * SharedPrefHelper
 *
 * Created by remydupont on 08/06/2018.
 */
class SharedPrefHelper private constructor() {

    private var mSharedPreferences: SharedPreferences =
            SpotifyApplication.instance.getSharedPreferences("GTG_SHARED_PREFS", Context.MODE_PRIVATE)

    companion object {

        val instance = SharedPrefHelper()

        private const val SPOTIFY_TOKEN = "SPOTIFY_TOKEN"
        private const val EXPIRE_AT = "EXPIRE_AT"
    }

    val spotifyToken: String
        get() = mSharedPreferences.getString(SPOTIFY_TOKEN, "")

    val isTokenValid: Boolean
        get() {
            val now = Calendar.getInstance().time
            val expireDate = mSharedPreferences.getString(EXPIRE_AT, "")

            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
            try {
                val expire = dateFormat.parse(expireDate)
                return now.before(expire)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return false
        }

    fun storeSpotifyToken(token: String) {

        val now = Calendar.getInstance()
        now.add(Calendar.HOUR, 1)  // Add 1 hour because the token validity is 3600s
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
        val expireDate = dateFormat.format(now.time)

        val edit = mSharedPreferences.edit()
        edit.putString(SPOTIFY_TOKEN, token)
        edit.putString(EXPIRE_AT, expireDate)
        edit.apply()
    }

    fun deleteSpotifyToken() {
        val edit = mSharedPreferences.edit()
        edit.remove(SPOTIFY_TOKEN)
        edit.remove(EXPIRE_AT)
        edit.apply()
    }


}
