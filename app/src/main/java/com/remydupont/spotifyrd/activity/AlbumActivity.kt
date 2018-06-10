package com.remydupont.spotifyrd.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.adapter.TracksAdapter
import com.remydupont.spotifyrd.extension.drawable
import com.remydupont.spotifyrd.extension.fetch
import com.remydupont.spotifyrd.helper.Constants
import com.remydupont.spotifyrd.models.Album
import com.remydupont.spotifyrd.models.Track
import com.remydupont.spotifyrd.network.NetworkManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.content_album.*
import java.util.*


class AlbumActivity : BaseActivity(), TracksAdapter.TrackListListener {

    private var isFavorite = false
    private var isPlaying = false

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


        val id = intent?.extras?.getString(Constants.ARG_ALBUM_ID, Constants.EMPTY_STRING) ?: Constants.EMPTY_STRING
        if (id.isNotEmpty())
            getAlbum(id)

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        menu?.clear()

        var action = ACTION_ADD_FAVORITE
        var title = "Add To Favorites"
        var iconResourceId = R.drawable.ic_favorite_empty_white

        if (isFavorite) {
            action = ACTION_REMOVE_FAVORITE
            title = "Remove FRom Favorites"
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
        }
        return super.onOptionsItemSelected(item)
    }





    /**
     * Private Functions
     */
    private fun getAlbum(id: String) {
        NetworkManager.instance?.service?.getAlbum(id)?.fetch {
            onResponse { _, response ->
                response?.body()?.let { initView(it) }
            }
        }
    }

    private fun initView(album: Album) {

        albumTitle.text = String.format(Locale.getDefault(), "%s - %s", album.artists!![0].name, album.name)
        albumTrackNumber.text = String.format(Locale.getDefault(), "%d tracks", album.tracks?.total)

        album.images?.let {
            if (it.isNotEmpty()) {
                Picasso.get()
                        .load(it[0].url)
                        .error(R.drawable.ic_audiotrack)
                        .placeholder(R.drawable.ic_audiotrack)
                        .into(albumCover)
            }
        }

        trackRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val mDividerItemDecoration = DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation)
            mDividerItemDecoration.setDrawable(drawable(R.drawable.recyclerview_divider)!!)
            addItemDecoration(mDividerItemDecoration)

            album.tracks?.items?.let {
                adapter = TracksAdapter(this@AlbumActivity, it, this@AlbumActivity)
            }
        }
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
        }
    }




    /**
     * Interface Implementation
     */
    override fun onTrackSelected(track: Track) {
        // TODO: Play the selected track
    }

    override fun onFavoriteClicked(track: Track) {
        // TODO: API call to add or remove the track
        // TODO: Add "user-library-modify" to the permission while getting token
    }
}
