package com.remydupont.spotifyrd.network

import com.remydupont.spotifyrd.models.AlbumResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * SpotifyServiceGtg
 *
 * Created by remydupont on 08/06/2018.
 */
interface SpotifyService {

    @GET("v1/browse/new-releases")
    fun getNewReleases(): Call<AlbumResponse>

}