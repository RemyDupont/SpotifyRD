package com.remydupont.spotifyrd.network

import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.helper.SharedPrefHelper
import com.remydupont.spotifyrd.application.SpotifyApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * NetworkManager
 *
 * Created by remydupont on 08/06/2018.
 */
class NetworkManager {

    companion object {
        var instance: NetworkManager? = null
        get() {
            if (field == null)
                instance = NetworkManager()
            return field
        }
    }

    var service: SpotifyService
    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    init {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor {
            chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .header("Cache-Control", "no-cache")
                    .header("Accept", "application/json")

            val token = SharedPrefHelper.getInstance().spotifyToken ?: null
            token?.let {
                request.header("Authorization", "Bearer $token")
            }

            chain.proceed(request.build())
        }

        okHttpClient = builder.build()

        retrofit = Retrofit.Builder()
                .baseUrl(SpotifyApplication.instance.getContext().getString(R.string.base_url))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(SpotifyService::class.java)
    }

}