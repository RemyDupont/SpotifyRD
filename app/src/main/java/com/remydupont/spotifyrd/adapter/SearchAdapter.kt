package com.remydupont.spotifyrd.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.models.*
import com.remydupont.spotifyrd.models.ViewTypeConstants.TYPE_ALBUM
import com.remydupont.spotifyrd.models.ViewTypeConstants.TYPE_ARTIST
import com.remydupont.spotifyrd.models.ViewTypeConstants.TYPE_HEADER
import com.remydupont.spotifyrd.models.ViewTypeConstants.TYPE_PLAYLIST
import com.squareup.picasso.Picasso

/**
 *      SearchAdapter
 *
 * Created by remydupont on 09/06/2018.
 */
class SearchAdapter(data: List<ViewType>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ViewType> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_HEADER -> HeaderViewHolder(parent.inflate(R.layout.item_search_header))
            TYPE_ARTIST -> ArtistViewHolder(parent.inflate(R.layout.item_search_artist))
            TYPE_ALBUM -> AlbumViewHolder(parent.inflate(R.layout.item_search_album))
            TYPE_PLAYLIST -> PlaylistViewHolder(parent.inflate(R.layout.item_search_playlist))
            else -> onCreateViewHolder(parent, viewType)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? HeaderViewHolder)?.bind(items[position] as HeaderItem)
        (holder as? ArtistViewHolder)?.bind(items[position] as Artist)
        (holder as? AlbumViewHolder)?.bind(items[position] as Album)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    /**
     * Public Functions
     */
    fun updateItems(newItems: List<ViewType>) {
        items = newItems
        notifyDataSetChanged()
    }


    /**
     * View Holders
     */
    inner class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var title: TextView = itemView.findViewById(R.id.title)

        fun bind(headerItem: HeaderItem) {
            title.text = headerItem.title
        }
    }



    inner class ArtistViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var artistImage: ImageView = itemView.findViewById(R.id.artistImage)
        private var artistName: TextView = itemView.findViewById(R.id.artistName)
        private var artistFans: TextView = itemView.findViewById(R.id.fans)
        private var favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)

        fun bind(artist: Artist) {
            artistName.text = artist.name
            val fansNumber = artist.followers?.total ?: 0
            artistFans.text = "$fansNumber followers"

            artist.images?.let {
                if (it.isNotEmpty()) {
                    Picasso.get()
                            .load(it[0].url)
                            .error(R.drawable.ic_artist)
                            .placeholder(R.drawable.ic_artist)
                            .into(artistImage)
                }
            }
        }
    }

    inner class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var albumImage: ImageView = itemView.findViewById(R.id.albumImage)
        private var albumName: TextView = itemView.findViewById(R.id.albumName)
        private var artistName: TextView = itemView.findViewById(R.id.artistName)
        private var favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)

        fun bind(album: Album) {
            albumName.text = album.name
            album.artists?.let {
                if (it.isNotEmpty()) {
                    artistName.text = it[0].name
                }
            }

            album.images?.let {
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

    inner class PlaylistViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var playlistImage: ImageView = itemView.findViewById(R.id.playlistImage)
        private var playlistName: TextView = itemView.findViewById(R.id.playlistName)
        private var owner: TextView = itemView.findViewById(R.id.owner)
        private var favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)

        fun bind(playlist: PlayList) {
            playlistName.text = playlist.name
            owner.text = playlist.owner?.display_name

            playlist.images?.let {
                if (it.isNotEmpty()) {
                    Picasso.get()
                            .load(it[0].url)
                            .error(R.drawable.ic_audiotrack)
                            .placeholder(R.drawable.ic_audiotrack)
                            .into(playlistImage)
                }
            }
        }
    }
}