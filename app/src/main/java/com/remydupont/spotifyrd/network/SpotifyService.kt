package com.remydupont.spotifyrd.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * SpotifyServiceGtg
 *
 * Created by remydupont on 08/06/2018.
 */
interface SpotifyService {

    @FormUrlEncoded
    @POST("token")
    fun getToken(@Field("grant_type") type: String): Call<ResponseBody>


}