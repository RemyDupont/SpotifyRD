package com.remydupont.spotifyrd.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.adapter.SearchAdapter
import com.remydupont.spotifyrd.extension.fetch
import com.remydupont.spotifyrd.extension.inflate
import com.remydupont.spotifyrd.extension.textWatcher
import com.remydupont.spotifyrd.models.HeaderItem
import com.remydupont.spotifyrd.models.SearchResponse
import com.remydupont.spotifyrd.models.ViewType
import com.remydupont.spotifyrd.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_search.*
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


    /**
     * System Callbacks
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRecyclerVIew.layoutManager = LinearLayoutManager(activity)

        searchView.textWatcher {
            afterTextChanged { s ->
                val query: String = URLEncoder.encode(s.toString(), "UTF-8")
                NetworkManager.instance?.service?.search(query)?.fetch {
                    onResponse { _, response ->
                        response?.body()?.let {
                            updateResults(it)
                        }
                    }
                }
            }
        }

        searchView.requestFocus()

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as?  InputMethodManager?
        imm?.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onPause() {

        searchView.clearFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
        imm?.hideSoftInputFromWindow(searchView.windowToken, 0)
        super.onPause()
    }


    /**
     * Private Functions
     */
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