package com.remydupont.spotifyrd.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.adapter.CategoriesAdapter
import com.remydupont.spotifyrd.extension.fetch
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * DiscoverFragment
 *
 * Created by remydupont on 09/06/2018.
 */
class DiscoverFragment: BaseFragment() {

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
        }
    }


    /**
     * System Callbacks
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_discover)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NetworkManager.instance?.service?.getCategories()?.fetch {
            onResponse { _, response ->
                response?.body()?.categories?.items?.let {
                    categoriesRecyclerView.apply {
                        layoutManager = GridLayoutManager(activity, 2)
                        adapter = CategoriesAdapter(it)
                    }
                }
            }
        }
    }

}