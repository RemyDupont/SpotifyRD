package com.remydupont.spotifyrd.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.remydupont.spotifyrd.application.SpotifyApplication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * SharedPrefHelper
 *
 * Created by remydupont on 08/06/2018.
 */
public class SharedPrefHelper {

    private static SharedPrefHelper instance = new SharedPrefHelper();
    private static SharedPreferences mSharedPreferences;

    private static final String SPOTIFY_TOKEN = "SPOTIFY_TOKEN";
    private static final String EXPIRE_AT = "EXPIRE_AT";

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

        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 1);  // Add 1 hour because the token validity is 3600s
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
        String expireDate = dateFormat.format(now.getTime());

        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(SPOTIFY_TOKEN, token);
        edit.putString(EXPIRE_AT, expireDate);
        edit.apply();
    }

    public void deleteSpotifyToken() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.remove(SPOTIFY_TOKEN);
        edit.remove(EXPIRE_AT);
        edit.apply();
    }

    public boolean isTokenValid() {
        Date now = Calendar.getInstance().getTime();
        String expireDate = mSharedPreferences.getString(EXPIRE_AT, "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
        try {
            Date expire = dateFormat.parse(expireDate);
            return now.before(expire);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

}
