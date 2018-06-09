package com.remydupont.spotifyrd.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remydupont.spotifyrd.listener.PlayerListener
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.extension.longToast
import com.remydupont.spotifyrd.models.AlbumResponse
import com.remydupont.spotifyrd.models.Track
import com.remydupont.spotifyrd.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * HomeFragment
 *
 * Created by remydupont on 09/06/2018.
 */
class HomeFragment: BaseFragment() {

    private var playerListener: PlayerListener? = null
    private var track: Track? = null

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    /**
     * System Callbacks
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PlayerListener) {
            playerListener = context
        } else {
            throw ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        /*NetworkManager.instance?.service?.getNewReleases()?.enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>?, response: Response<AlbumResponse>?) {
                response?.body()?.let {
                    longToast("New Releases OK")
                }
            }

            override fun onFailure(call: Call<AlbumResponse>?, t: Throwable?) {
                longToast("New Releases Failure")
            }
        } )*/

        NetworkManager.instance?.service?.getTrack("2TpxZ7JUBn3uw46aR7qd6V")?.enqueue(object : Callback<Track> {
            override fun onResponse(call: Call<Track>?, response: Response<Track>?) {
                track = response?.body()
            }

            override fun onFailure(call: Call<Track>?, t: Throwable?) {
                longToast("Track Fail")
            }
        })
    }


    /**
     * Private Functions
     */
    private fun init() {
        play_btn.setOnClickListener {
            track?.let {
                playerListener?.playTrack(it)
            }
        }

        stop_btn.setOnClickListener {
            playerListener?.pause()
        }
    }



}

