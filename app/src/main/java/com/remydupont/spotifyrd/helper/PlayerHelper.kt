package com.remydupont.spotifyrd.helper

import android.util.Log
import com.remydupont.spotifyrd.extension.next
import com.remydupont.spotifyrd.extension.pause
import com.remydupont.spotifyrd.extension.previous
import com.remydupont.spotifyrd.extension.resume
import com.remydupont.spotifyrd.models.Track
import com.spotify.sdk.android.player.Error
import com.spotify.sdk.android.player.Player


/**
 * PlayerHelper
 *
 * Created by remydupont on 11/06/2018.
 */
class PlayerHelper {

    private object Holder { val INSTANCE = PlayerHelper() }

    companion object {
        val instance: PlayerHelper by lazy {
            Holder.INSTANCE
        }
    }

    var player: Player? = null
    var currentTrack: Track? = null



    /**
     * Public Functions
     */
    fun play(track: Track) {
        this.currentTrack = track
        player?.playUri(object : Player.OperationCallback {
            override fun onSuccess() {}
            override fun onError(error: Error?) {}
        }, track.uri, 0, 0)
    }

    fun queue(track: Track) {
        // Seems we can queue only one song, not a entire album or playlist
        player?.queue(object : Player.OperationCallback {
            override fun onSuccess() {
                Log.d("Player", "Track queued Success")
            }
            override fun onError(error: Error?) {
                Log.d("Player", "Track queued Failed")
            }
        }, track.uri)
    }

    fun pause() {
        player?.pause {
            onSuccess {}
            onError {}
        }
    }

    fun resume() {
        player?.resume {
            onSuccess {  }
            onError {  }
        }
    }

    fun next(hasNext: (Boolean) -> Unit) {
        player?.next {
            onSuccess {
                hasNext(player?.metadata?.nextTrack != null) // Even with queued tracks, always nextTrack is always null
            }
            onError {  }
        }
    }

    fun previous(hasPrevious: (Boolean) -> Unit) {
        player?.previous {
            onSuccess {
                hasPrevious(player?.metadata?.prevTrack != null) // Even with queued tracks, always previous is always null
            }
            onError {  }
        }
    }


}