package com.remydupont.spotifyrd.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.drawable
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.models.Track

/**
 * HorizontalAdapter
 *
 * Created by remydupont on 09/06/2018.
 */
class TracksAdapter(
        var context: Context,
        var items: List<Track>,
        var listener: TrackListListener
): RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {


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
        private var favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)

        private var isFavorite = false

        fun bind(track: Track) {
            trackTitle.text = track.name
            trackNumber.text = track.track_number.toString()

            favoriteIcon.setOnClickListener {
                isFavorite = !isFavorite
                val drawableRes = if (isFavorite) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.ic_favorite_empty
                }
                favoriteIcon.setImageDrawable(context.drawable(drawableRes))
                listener.onFavoriteClicked(track)
            }

            itemView?.setOnClickListener {
                listener.onTrackSelected(track)
            }
        }
    }


    /**
     * Interface
     */
    interface TrackListListener {
        fun onTrackSelected(track: Track)
        fun onFavoriteClicked(track: Track)
    }
}