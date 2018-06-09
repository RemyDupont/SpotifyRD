package com.remydupont.spotifyrd.network

import com.remydupont.spotifyrd.models.AlbumResponse
import com.remydupont.spotifyrd.models.FeaturedPlayListsResponse
import com.remydupont.spotifyrd.models.Track
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * SpotifyServiceGtg
 *
 * Created by remydupont on 08/06/2018.
 */
interface SpotifyService {

    @GET("v1/browse/new-releases")
    fun getNewReleases(): Call<AlbumResponse>

    @GET("v1/tracks/{id}")
    fun getTrack(@Path("id") id: String): Call<Track>

    @GET("v1/browse/featured-playlists")
    fun getFeatured(): Call<FeaturedPlayListsResponse>

}