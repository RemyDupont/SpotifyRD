package com.remydupont.spotifyrd.activity

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.*
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.remydupont.spotifyrd.fragment.HomeFragment
import com.remydupont.spotifyrd.listener.PlayerListener
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.fragment.DiscoverFragment
import com.remydupont.spotifyrd.fragment.SearchFragment
import com.remydupont.spotifyrd.models.Track
import com.spotify.sdk.android.player.Player
import com.spotify.sdk.android.player.Error
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*


class MainActivity : BaseActivity(), PlayerListener {


    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(bottom_sheet)
    }
    private var currentView: Int = 0

    /**
     * System Callbacks
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initPlayer()
        initBottomSheet()

        bottomNavigationView.selectedItemId = R.id.action_home

    }



    /**
     * Private Functions
     */


    private fun initView() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    if (currentView != R.id.action_home) {
                        replaceFragment(HomeFragment.newInstance())
                        currentView = R.id.action_home
                    }
                }
                R.id.action_discover -> {
                    if (currentView != R.id.action_discover) {
                        replaceFragment(DiscoverFragment.newInstance())
                        currentView = R.id.action_discover
                    }
                }
                R.id.action_search -> {
                    if (currentView != R.id.action_search) {
                        replaceFragment(SearchFragment.newInstance())
                        currentView = R.id.action_search
                    }
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String = "", addToBackStack: Boolean = false) {

        val ft = supportFragmentManager.beginTransaction()
        if (addToBackStack)
            ft.addToBackStack(tag)

        ft.replace(R.id.contentFrame, fragment, tag)
        ft.commit()
    }

    private fun initBottomSheet() {

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    STATE_EXPANDED -> {
                        collapsedLayout.visibility = View.GONE
                    }
                    STATE_COLLAPSED -> {
                        collapsedLayout.visibility = View.VISIBLE
                    }
                    STATE_DRAGGING -> {
                        if (collapsedLayout.visibility == View.VISIBLE) {
                            collapsedLayout.visibility = View.GONE
                        }
                    }
                }
            }

        })

        expandBtn.setOnClickListener {
            bottomSheetBehavior.state = STATE_EXPANDED
        }
        collapseBtn.setOnClickListener {
            bottomSheetBehavior.state = STATE_COLLAPSED
        }

        playPauseBtn.setOnClickListener { togglePlay() }
        playPauseExpanded.setOnClickListener { togglePlay() }
    }

    private fun setBottomSheetTrack(track: Track) {

        songTitleCollapsed.text = track.name
        songTitleExpanded.text = track.name

        track.album?.images?.let {
            if (it.isNotEmpty()) {
                Picasso.get()
                        .load(it[0].url)
                        .error(R.drawable.ic_audiotrack)
                        .placeholder(R.drawable.ic_audiotrack)
                        .into(albumThumb)

                Picasso.get()
                        .load(it[0].url)
                        .error(R.drawable.ic_audiotrack)
                        .placeholder(R.drawable.ic_audiotrack)
                        .into(albumCoverFullSize)
            }
        }

        val durationSeconds = (track.duration_ms ?: 0)/ 1000
        val minutes = durationSeconds.div(60)
        val seconds = durationSeconds % 60
        totalTime.text = "$minutes:$seconds"
    }

    private fun togglePlay() {
        if (mPlayer?.playbackState?.isPlaying == true) {
            pause()
            playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_play))
            playPauseExpanded.setImageDrawable(resources.getDrawable(R.drawable.ic_play))
        } else {
            resume()
            playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_pause))
            playPauseExpanded.setImageDrawable(resources.getDrawable(R.drawable.ic_pause))
        }
    }

    private fun resume() {
        mPlayer?.resume(object : Player.OperationCallback {
            override fun onSuccess() {

            }

            override fun onError(p0: Error?) {

            }
        })
    }


    /**
     * Interface Implementation
     */
    override fun playTrack(track: Track) {
        mPlayer?.playUri(object : Player.OperationCallback {
            override fun onSuccess() {
            }
            override fun onError(error: Error?) {
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_LONG).show()
            }
        }, track.uri, 0, 0)

        if (bottom_sheet.visibility == View.GONE)
            bottom_sheet.visibility = View.VISIBLE

        setBottomSheetTrack(track)
    }

    override fun pause() {
        mPlayer?.pause(object : Player.OperationCallback {
            override fun onSuccess() {

            }
            override fun onError(p0: Error?) {

            }
        })
    }



}
