package com.remydupont.spotifyrd.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.models.Category
import com.remydupont.spotifyrd.models.Track
import com.squareup.picasso.Picasso

/**
 * HorizontalAdapter
 *
 * Created by remydupont on 09/06/2018.
 */
class TracksAdapter(var items: List<Track>): RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        return TracksViewHolder(parent.inflate(R.layout.item_track))
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    /**
     * View Holder
     */
    inner class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var trackNumber: TextView = itemView.findViewById(R.id.trackNumber)
        private var trackTitle: TextView = itemView.findViewById(R.id.trackTitle)

        fun bind(track: Track) {
            trackTitle.text = track.name
            trackNumber.text = track.track_number.toString()
        }
    }
}