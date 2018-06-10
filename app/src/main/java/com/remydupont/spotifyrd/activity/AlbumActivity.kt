package com.remydupont.spotifyrd.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.adapter.TracksAdapter
import com.remydupont.spotifyrd.extension.fetch
import com.remydupont.spotifyrd.extension.longToast
import com.remydupont.spotifyrd.helper.Constants
import com.remydupont.spotifyrd.models.Album
import com.remydupont.spotifyrd.network.NetworkManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.content_album.*
import java.util.*
import android.support.v7.widget.DividerItemDecoration
import com.remydupont.spotifyrd.extension.drawable


class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_album)
        toolbar_layout.title = ""
        toolbar.title = ""
        setSupportActionBar(toolbar)
        fab.setOnClickListener { _ ->
            playAlbum()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val id = intent?.extras?.getString(Constants.ARG_ALBUM_ID, Constants.EMPTY_STRING) ?: Constants.EMPTY_STRING
        if (id.isNotEmpty())
            getAlbum(id)

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
                adapter = TracksAdapter(it)
            }
        }
    }

    private fun playAlbum() {

    }
}
