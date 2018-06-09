package com.remydupont.spotifyrd.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.remydupont.spotifyrd.application.SpotifyApplication;

/**
 * SharedPrefHelper
 *
 * Created by remydupont on 08/06/2018.
 */
public class SharedPrefHelper {

    private static SharedPrefHelper instance = new SharedPrefHelper();
    private static SharedPreferences mSharedPreferences;

    private static final String SPOTIFY_TOKEN = "SPOTIFY_TOKEN";

    private SharedPrefHelper() {
        mSharedPreferences = SpotifyApplication.instance.getSharedPreferences("GTG_SHARED_PREFS", Context.MODE_PRIVATE);
    }

    public static SharedPrefHelper getInstance() {
        return instance;
    }


    public String getSpotifyToken() {
        return mSharedPreferences.getString(SPOTIFY_TOKEN, "");
    }

    public void storeSpotifyToken(String token) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(SPOTIFY_TOKEN, token);
        edit.apply();
    }

    public void deleteSpotifyToken() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.remove(SPOTIFY_TOKEN);
        edit.apply();
    }

}
