package com.remydupont.spotifyrd.extension


import com.spotify.sdk.android.player.Error
import com.spotify.sdk.android.player.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * KTCallback
 *
 * Created by remydupont on 06/06/2018.
 */
class KTOperationCallback: Player.OperationCallback {

    private var _onSuccess: (() -> Unit)? = null
    private var _onError: ((Error?) -> Unit)? = null

    override fun onSuccess() {
        _onSuccess?.invoke()
    }

    override fun onError(error: Error?) {
        _onError?.invoke(error)
    }

    fun onSuccess(listener: () -> Unit) {
        _onSuccess = listener
    }

    fun onError(listener: (Error?) -> Unit) {
        _onError = listener
    }

}

inline fun Player.pause(init: KTOperationCallback.() -> Unit) = pause(KTOperationCallback().apply(init))
inline fun Player.resume(init: KTOperationCallback.() -> Unit) = resume(KTOperationCallback().apply(init))