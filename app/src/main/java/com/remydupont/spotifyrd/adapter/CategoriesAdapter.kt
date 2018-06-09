package com.remydupont.spotifyrd.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.models.Category
import com.remydupont.spotifyrd.models.FeaturedItem
import com.squareup.picasso.Picasso

/**
 * HorizontalAdapter
 *
 * Created by remydupont on 09/06/2018.
 */
class CategoriesAdapter(var items: List<Category>): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(parent.inflate(R.layout.item_category))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }




    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var categoryBackground: ImageView = itemView.findViewById(R.id.categoryBackground)
        private var categoryName: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            categoryName.text = category.name

            category.icons?.let {
                if (it.isNotEmpty()) {
                    Picasso.get()
                            .load(it[0].url)
                            .error(R.drawable.ic_audiotrack)
                            .placeholder(R.drawable.ic_audiotrack)
                            .into(categoryBackground)
                }
            }
        }
    }
}