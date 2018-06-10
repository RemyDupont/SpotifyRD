package com.remydupont.spotifyrd.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.activity.AlbumActivity
import com.remydupont.spotifyrd.adapter.HorizontalAlbumAdapter
import com.remydupont.spotifyrd.adapter.HorizontalFeaturedAdapter
import com.remydupont.spotifyrd.extension.fetch
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.helper.Constants
import com.remydupont.spotifyrd.listener.PlayerListener
import com.remydupont.spotifyrd.models.Album
import com.remydupont.spotifyrd.models.Track
import com.remydupont.spotifyrd.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * HomeFragment
 *
 * Created by remydupont on 09/06/2018.
 */
class HomeFragment: BaseFragment(), HorizontalAlbumAdapter.AlbumListener {

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

        initView()
        executeAPICalls()

    }




    /**
     * Private Functions
     */
    private fun initView() {
        play_btn.setOnClickListener {
            track?.let {
                playerListener?.playTrack(it)
            }
        }
    }

    private fun executeAPICalls() {

        NetworkManager.instance?.service?.getTrack("2TpxZ7JUBn3uw46aR7qd6V")?.fetch {
            onResponse { _, response ->
                response?.body()?.let { track = it }
            }
        }

        NetworkManager.instance?.service?.getNewReleases()?.fetch {
            onResponse { _, response ->
                response?.body()?.albums?.items?.let {
                    newReleasesRecyclerView.apply {
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = HorizontalAlbumAdapter(it, this@HomeFragment)
                    }
                }
            }
        }

        NetworkManager.instance?.service?.getFeatured()?.fetch {
            onResponse { _, response ->
                response?.body()?.playlists?.items?.let {
                    featuredPlaylistsTitle.text = response.body()?.message ?: "Featured Playlists"
                    featuredRecyclerView.apply {
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = HorizontalFeaturedAdapter(it)
                    }
                }
            }
        }
    }


    override fun onItemSelected(album: Album) {
        val intent = Intent(activity, AlbumActivity::class.java).apply {
            putExtra(Constants.ARG_ALBUM_ID, album.id)
        }
        startActivity(intent)
    }
}

