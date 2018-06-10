package com.remydupont.spotifyrd.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.adapter.SearchAdapter
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.extension.longToast
import com.remydupont.spotifyrd.extension.textWatcher
import com.remydupont.spotifyrd.models.HeaderItem
import com.remydupont.spotifyrd.models.SearchResponse
import com.remydupont.spotifyrd.models.ViewType
import com.remydupont.spotifyrd.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

/**
 * SearchFragment
 *
 * Created by remydupont on 09/06/2018.
 */
class SearchFragment: BaseFragment() {

    private var adapter: SearchAdapter? = null

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecyclerVIew.layoutManager = LinearLayoutManager(activity)

        searchView.textWatcher {
            afterTextChanged { s ->

                val query: String = URLEncoder.encode(s.toString(), "UTF-8")
                NetworkManager.instance?.service?.search(query, "artist,album,playlist", 3)
                        ?.enqueue(object : Callback<SearchResponse> {
                            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>?) {
                                response?.body()?.let {
                                    updateResults(it)
                                }
                            }

                            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {
                                longToast("Feature Failure")
                            }
                        } )
            }
        }
    }

    private fun updateResults(searchResponse: SearchResponse) {
        val data: MutableList<ViewType> = ArrayList()

        searchResponse.artists?.items?.let {
            if (it.isNotEmpty()) {
                data.add(HeaderItem("Artists"))
                data.addAll(it)
            }
        }

        searchResponse.albums?.items?.let {
            if (it.isNotEmpty()) {
                data.add(HeaderItem("Albums"))
                data.addAll(it)
            }
        }

        searchResponse.playLists?.items?.let {
            if (it.isNotEmpty()) {
                data.add(HeaderItem("Artists"))
                data.addAll(it)
            }
        }

        if (adapter == null) {
            adapter = SearchAdapter(data)
            searchRecyclerVIew.adapter = adapter
        } else {
            adapter?.updateItems(data)
        }
    }

}