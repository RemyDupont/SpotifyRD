package com.remydupont.spotifyrd.network

import com.remydupont.spotifyrd.helper.Constants
import com.remydupont.spotifyrd.models.*
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

    @GET("v1/browse/categories")
    fun getCategories(): Call<CategoriesResponse>

    @GET("v1/browse/categories/{category_id}/playlists")
    fun getCategory(@Path("category_id") categoryId: String): Call<PlayListObject>

    @GET("v1/search")
    fun search(
            @Query("q") query: String,
            @Query("type") type: String = Constants.SEARCH_ARG_DEFAULT,
            @Query("limit") limit: Int = 3
    ): Call<SearchResponse>

    @GET("v1/albums/{id}")
    fun getAlbum(@Path("id") id: String): Call<Album>

}