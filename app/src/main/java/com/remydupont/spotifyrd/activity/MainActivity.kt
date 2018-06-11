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
import com.remydupont.spotifyrd.extension.*
import com.remydupont.spotifyrd.fragment.DiscoverFragment
import com.remydupont.spotifyrd.fragment.SearchFragment
import com.remydupont.spotifyrd.helper.Constants
import com.remydupont.spotifyrd.helper.PlayerHelper
import com.remydupont.spotifyrd.models.Track
import com.spotify.sdk.android.player.Player
import com.spotify.sdk.android.player.Error
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.util.*


class MainActivity : BaseActivity(), PlayerListener {


    private var currentView: Int = 0
    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(bottom_sheet) }


    /**
     * System Callbacks
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initPlayer()
        initBottomSheet()

    }



    /**
     * Private Functions
     */
    private fun initView() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == currentView)
                return@setOnNavigationItemSelectedListener true

            when (item.itemId) {
                R.id.action_home -> {
                    replaceFragment(HomeFragment.newInstance())
                }
                R.id.action_discover -> {
                    replaceFragment(DiscoverFragment.newInstance())
                }
                R.id.action_search -> {
                    replaceFragment(SearchFragment.newInstance())
                }
            }

            currentView = item.itemId
            true
        }

        bottomNavigationView.selectedItemId = R.id.action_home
    }

    private fun initBottomSheet() {

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Offset == 1 when expanded, == 0 when collapsed
                val alpha = Math.abs(slideOffset - 1.0)
                collapsedLayout.alpha = alpha.toFloat()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    STATE_EXPANDED -> {
                        collapsedLayout.alpha = 0F
                        collapsedLayout.gone()
                    }
                    STATE_COLLAPSED -> {
                        collapsedLayout.alpha = 1F
                        if (!collapsedLayout.isVisible())
                            collapsedLayout.visible()
                    }
                    STATE_DRAGGING -> {
                        if (!collapsedLayout.isVisible())
                            collapsedLayout.visible()
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

        artistTextView.text = track.artists?.get(0)?.name ?: string(R.string.artist)
        albumTextView.text = track.album?.name ?: string(R.string.album)

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
        totalTime.text = String.format(Locale.getDefault(), "%d:%d", minutes, seconds)
    }

    private fun togglePlay() {
        if (PlayerHelper.instance.player?.playbackState?.isPlaying == true) {
            pause()
            playPauseBtn.setImageDrawable(drawable(R.drawable.ic_play))
            playPauseExpanded.setImageDrawable(drawable(R.drawable.ic_play))
        } else {
            resume()
            playPauseBtn.setImageDrawable(drawable(R.drawable.ic_pause))
            playPauseExpanded.setImageDrawable(drawable(R.drawable.ic_pause))
        }
    }

    private fun resume() {
        PlayerHelper.instance.player?.resume {}
    }

    private fun replaceFragment(fragment: Fragment, tag: String = Constants.EMPTY_STRING, addToBackStack: Boolean = false) {

        val ft = supportFragmentManager.beginTransaction()
        if (addToBackStack)
            ft.addToBackStack(tag)

        ft.replace(R.id.contentFrame, fragment, tag)
        ft.commit()
    }




    /**
     * Interface Implementation
     */
    override fun playTrack(track: Track) {
        PlayerHelper.instance.player?.playUri(object : Player.OperationCallback {
            override fun onSuccess() {
            }
            override fun onError(error: Error?) {
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_LONG).show()
            }
        }, track.uri, 0, 0)

        setBottomSheetTrack(track)
    }

    override fun pause() {
        PlayerHelper.instance.player?.pause { }
    }



}
