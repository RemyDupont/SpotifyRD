package com.remydupont.spotifyrd.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.models.PlayList
import com.squareup.picasso.Picasso

/**
 * HorizontalAdapter
 *
 * Created by remydupont on 09/06/2018.
 */
class PlaylistAdapter(
        var context: Context,
        var items: List<PlayList>,
        var listener: PlaylistListener
): RecyclerView.Adapter<PlaylistAdapter.PlaylistHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistHolder {
        return PlaylistHolder(parent.inflate(R.layout.item_playlist))
    }

    override fun onBindViewHolder(holder: PlaylistHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    /**
     * View Holder
     */
    inner class PlaylistHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var playlistName: TextView = itemView.findViewById(R.id.playlistName)
        private var playlistImage: ImageView = itemView.findViewById(R.id.playlistImage)

        fun bind(playList: PlayList) {
            playlistName.text = playList.name

            Picasso.get()
                    .load(playList.images!![0].url)
                    .placeholder(R.drawable.ic_audiotrack)
                    .error(R.drawable.ic_audiotrack)
                    .into(playlistImage)

            itemView?.setOnClickListener {
                listener.onPlaylistSelected(playList)
            }
        }
    }


    /**
     * Interface
     */
    interface PlaylistListener {
        fun onPlaylistSelected(playList: PlayList)
    }
}