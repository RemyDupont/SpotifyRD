package com.remydupont.spotifyrd.network

import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.SpotifyApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

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

        //instance = this

        val builder = OkHttpClient().newBuilder()
        //builder.authenticator(TokenAuthenticatorV2())
        builder.addInterceptor {
            chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .header("Cache-Control", "no-cache")
                    .header("Accept", "application/json")

            val up = "70c6fb9b8338428293ea6160323f2190:ab3821abe8684480ad4e86e6b49be2ba"
            var clientId: String = android.util.Base64.encodeToString(
                    up.toByteArray(),
                    android.util.Base64.DEFAULT)

            if (clientId.contains("\n"))
                clientId = clientId.split("\n")[0]
            request.header("Authorization", "Basic $clientId")

        /*    val token = AuthHelper.getInstance().authToken
            if (token.isNotEmpty())
                request.header("Authorization", "Bearer $token")
            val vid = SharedPrefsHelper.getInstance().vidCookie
            if (vid != Constants.EMPTY_STRING)
                request.header("Cookie", vid.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
*/
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