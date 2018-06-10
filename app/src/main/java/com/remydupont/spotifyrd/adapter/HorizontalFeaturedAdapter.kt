package com.remydupont.spotifyrd.adapter

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
class HorizontalFeaturedAdapter(var items: List<PlayList>): RecyclerView.Adapter<HorizontalFeaturedAdapter.FeaturedViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        return FeaturedViewHolder(parent.inflate(R.layout.item_dashboard_featured))
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }




    /**
     * View Holder
     */
    inner class FeaturedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var playlistImage: ImageView = itemView.findViewById(R.id.playlistCover)
        private var playlistName: TextView = itemView.findViewById(R.id.playlistName)

        fun bind(featuredItem: PlayList) {
            playlistName.text = featuredItem.name

            featuredItem.images?.let {
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