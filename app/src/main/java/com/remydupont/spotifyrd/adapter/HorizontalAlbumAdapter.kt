package com.remydupont.spotifyrd.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.models.AlbumItem
import com.squareup.picasso.Picasso

/**
 * HorizontalAdapter
 *
 * Created by remydupont on 09/06/2018.
 */
class HorizontalAlbumAdapter(var items: List<AlbumItem>): RecyclerView.Adapter<HorizontalAlbumAdapter.AlbumViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(parent.inflate(R.layout.item_dashboard_album))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }




    inner class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var albumImage: ImageView = itemView.findViewById(R.id.albumCover)
        private var artist: TextView = itemView.findViewById(R.id.albumArtist)
        private var albumName: TextView = itemView.findViewById(R.id.albumName)

        fun bind(albumItem: AlbumItem) {
            albumItem.artists?.let {
                artist.text = it[0].name ?: ""
            }
            albumName.text = albumItem.name

            albumItem.images?.let {
                if (it.isNotEmpty()) {
                    Picasso.get()
                            .load(it[0].url)
                            .error(R.drawable.ic_audiotrack)
                            .placeholder(R.drawable.ic_audiotrack)
                            .into(albumImage)
                }
            }
        }
    }
}