package com.remydupont.spotifyrd.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.adapter.PlaylistAdapter
import com.remydupont.spotifyrd.adapter.TracksAdapter
import com.remydupont.spotifyrd.extension.drawable
import com.remydupont.spotifyrd.extension.fetch
import com.remydupont.spotifyrd.extension.gone
import com.remydupont.spotifyrd.extension.string
import com.remydupont.spotifyrd.helper.Constants
import com.remydupont.spotifyrd.helper.PlayerHelper
import com.remydupont.spotifyrd.models.*
import com.remydupont.spotifyrd.network.NetworkManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.content_album.*
import java.util.*
import kotlin.collections.ArrayList


class DetailsActivity : BaseActivity(),
        TracksAdapter.TrackListListener,
        PlaylistAdapter.PlaylistListener {

    private var isFavorite = false
    private var isPlaying = false
    private val tracks: MutableList<Track> = ArrayList()

    companion object {
        private const val ACTION_ADD_FAVORITE = 0
        private const val ACTION_REMOVE_FAVORITE = 1
    }

    /**
     * System Callbacks
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_album)
        toolbar_layout.title = Constants.EMPTY_STRING
        toolbar.title = Constants.EMPTY_STRING
        setSupportActionBar(toolbar)
        fab.setOnClickListener { _ ->
            togglePlayPause()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val albumId = intent?.extras?.getString(Constants.ARG_ALBUM_ID, Constants.EMPTY_STRING) ?: Constants.EMPTY_STRING
        val userId = intent?.extras?.getString(Constants.ARG_USER_ID, Constants.EMPTY_STRING) ?: Constants.EMPTY_STRING
        val playListId = intent?.extras?.getString(Constants.ARG_PLAYLIST_ID, Constants.EMPTY_STRING) ?: Constants.EMPTY_STRING

        if (albumId.isNotEmpty()) {
            getAlbum(albumId)
        } else if (userId.isNotEmpty() && playListId.isNotEmpty()) {
            getPlaylist(userId, playListId)
        } else if (playListId.isNotEmpty()) {
            titleTV.text = playListId
            subtitleTV.gone()
            fab.gone()
            getCategoryPlaylists(playListId)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        menu?.clear()

        var action = ACTION_ADD_FAVORITE
        var title = string(R.string.add_to_favorites)
        var iconResourceId = R.drawable.ic_favorite_empty_white

        if (isFavorite) {
            action = ACTION_REMOVE_FAVORITE
            title = string(R.string.remove_from_favorites)
            iconResourceId = R.drawable.ic_favorite_white
        }

        menu?.add(0, action, Menu.NONE, title)
                ?.setIcon(iconResourceId)
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            ACTION_ADD_FAVORITE -> { addAlbumToFavorite() }
            ACTION_REMOVE_FAVORITE -> { removeAlbumFromFavorite() }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }





    /**
     * Private Functions
     */
    private fun getAlbum(id: String) {
        NetworkManager.instance.service.getAlbum(id).fetch {
            onResponse { _, response ->
                response?.body()?.let { initView(album = it) }
            }
        }
    }

    private fun getPlaylist(userId: String, playlistId: String) {
        NetworkManager.instance.service.getPlaylist(userId, playlistId).fetch {
            onResponse { _, response ->
                response?.body()?.let {
                    initView(it)
                }
            }
        }
    }

    private fun getCategoryPlaylists(id: String) {
        NetworkManager.instance.service.getCategory(id).fetch {
            onResponse { _, response ->
                response?.body()?.playlists?.let {
                    initView(playlistResponse = it)
                }
            }
        }
    }

    private fun initView(album: Album) {

        titleTV.text = String.format(Locale.getDefault(), "%s - %s", album.artists!![0].name, album.name)
        subtitleTV.text = resources.getQuantityString(R.plurals.tracks, album.tracks?.total ?: 1)

        album.images?.let {
            if (it.isNotEmpty()) {
                Picasso.get()
                        .load(it[0].url)
                        .error(R.drawable.ic_audiotrack)
                        .placeholder(R.drawable.ic_audiotrack)
                        .into(albumCover)
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val mDividerItemDecoration = DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation)
            mDividerItemDecoration.setDrawable(drawable(R.drawable.recyclerview_divider)!!)
            addItemDecoration(mDividerItemDecoration)

            album.tracks?.items?.let {
                tracks.clear()
                it.map { track -> tracks.add(track) }
                adapter = TracksAdapter(this@DetailsActivity, it, this@DetailsActivity)
            }
        }

        loader.gone()
    }

    private fun initView(playlistResponse: PlaylistResponse) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)

            playlistResponse.items?.let {
                adapter = PlaylistAdapter(this@DetailsActivity, it, this@DetailsActivity)
            }
        }

        loader.gone()
    }

    private fun initView(playList: PlayListFull) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val mDividerItemDecoration = DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation)
            mDividerItemDecoration.setDrawable(drawable(R.drawable.recyclerview_divider)!!)
            addItemDecoration(mDividerItemDecoration)

            playList.tracks?.items?.let {
                it.filter { it.track != null }
                        .map { tracks.add(it.track!!) }
                adapter = TracksAdapter(this@DetailsActivity, tracks, this@DetailsActivity)
            }
        }

        playList.images?.let {
            if (it.isNotEmpty()) {
                Picasso.get()
                        .load(it[0].url)
                        .error(R.drawable.ic_audiotrack)
                        .placeholder(R.drawable.ic_audiotrack)
                        .into(albumCover)
            }
        }


        loader.gone()
    }

    private fun addAlbumToFavorite() {
        isFavorite = true
        invalidateOptionsMenu()
    }

    private fun removeAlbumFromFavorite() {
        isFavorite = false
        invalidateOptionsMenu()
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            isPlaying = false
            fab.setImageDrawable(drawable(R.drawable.ic_play))
        } else {
            isPlaying = true
            fab.setImageDrawable(drawable(R.drawable.ic_pause_white))

            if (tracks.isNotEmpty())
                PlayerHelper.instance.play(tracks[0])

            for ((i, track) in tracks.withIndex()) {
                if (i > 0) {
                    PlayerHelper.instance.queue(track)
                }
            }

        }
    }




    /**
     * Interfaces Implementation
     */
    override fun onTrackSelected(track: Track) {
        PlayerHelper.instance.play(track)
    }

    override fun onFavoriteClicked(track: Track) {
        // TODO: API call to add or remove the track
        // TODO: Add "user-library-modify" to the permission while getting token
    }

    override fun onPlaylistSelected(playList: PlayList) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(Constants.ARG_USER_ID, playList.owner!!.id)
            putExtra(Constants.ARG_PLAYLIST_ID, playList.id)
        }
        startActivity(intent)
    }
}
