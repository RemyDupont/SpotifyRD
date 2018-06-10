package com.remydupont.spotifyrd.extension


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * KTCallback
 *
 * Created by remydupont on 06/06/2018.
 */
class KTCallback<T>: Callback<T> {

    private var _onResponse: ((Call<T>?, response: Response<T>?) -> Unit)? = null
    private var _onFailure: ((Call<T>?, Throwable?) -> Unit)? = null

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        _onResponse?.invoke(call, response)
    }

    override fun onFailure(call: Call<T>?, throwable: Throwable?) {
        _onFailure?.invoke(call, throwable)
    }

    fun onResponse(listener: (Call<T>?, response: Response<T>?) -> Unit) {
        _onResponse = listener
    }

    fun onFailure(listener: (Call<T>?, Throwable?) -> Unit) {
        _onFailure = listener
    }

}

inline fun <T> Call<T>.fetch(init: KTCallback<T>.() -> Unit) = enqueue(KTCallback<T>().apply(init))