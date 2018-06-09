package com.remydupont.spotifyrd.activity

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.remydupont.spotifyrd.fragment.HomeFragment
import com.remydupont.spotifyrd.listener.PlayerListener
import com.remydupont.spotifyrd.R
import com.remydupont.spotifyrd.fragment.DiscoverFragment
import com.remydupont.spotifyrd.fragment.SearchFragment
import com.spotify.sdk.android.player.Player
import com.spotify.sdk.android.player.Error
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), PlayerListener {


    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(bottom_sheet)
    }
    private var currentView: Int = 0

    /**
     * System Callbacks
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initPlayer()


        bottomNavigationView.selectedItemId = R.id.action_home

    }



    /**
     * Private Functions
     */


    private fun initView() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    if (currentView != R.id.action_home) {
                        replaceFragment(HomeFragment.newInstance())
                        currentView = R.id.action_home
                    }
                }
                R.id.action_discover -> {
                    if (currentView != R.id.action_discover) {
                        replaceFragment(DiscoverFragment.newInstance())
                        currentView = R.id.action_discover
                    }
                }
                R.id.action_search -> {
                    if (currentView != R.id.action_search) {
                        replaceFragment(SearchFragment.newInstance())
                        currentView = R.id.action_search
                    }
                }
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment, tag: String = "", addToBackStack: Boolean = false) {

        val ft = supportFragmentManager.beginTransaction()
        if (addToBackStack)
            ft.addToBackStack(tag)

        ft.replace(R.id.contentFrame, fragment, tag)
        ft.commit()
    }


    /**
     * Interface Implementation
     */
    override fun playTrack(track: String) {
        mPlayer?.playUri(object : Player.OperationCallback {
            override fun onSuccess() {
            }
            override fun onError(error: Error?) {
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_LONG).show()
            }
        }, track, 0, 0)

        if (bottom_sheet.visibility == View.GONE)
            bottom_sheet.visibility = View.VISIBLE
    }

    override fun pause() {
        mPlayer?.pause(object : Player.OperationCallback {
            override fun onSuccess() {

            }
            override fun onError(p0: Error?) {

            }
        })
    }



}
