package com.example.gallusawa.latestnewsapp.sources

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import java.util.ArrayList

/**
 * Created by gallusawa on 12/7/17.
 */
class SourcesActivity : AppCompatActivity(), SourcesContract.View {

    private var presenter: SourcesContract.Presenter? = null
    private var adapter: SourcesRecyclerViewAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    val isActive: Boolean
        get() = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        initToolbar()
        initRecycler()
        initSwipeToRefresh()
        initPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter!!.loadSources()
    }

    // Create the presenter
    private fun initPresenter() {
        presenter = SourcesPresenter(
                Injection.provideRepository(applicationContext), this)
    }

    fun showSources(sources: List<Source>) {
        if (adapter != null) {
            adapter!!.setSourceList(sources)
        }
    }

    fun setRefreshing(refreshing: Boolean) {
        if (swipeRefreshLayout == null) {
            return
        }
        swipeRefreshLayout!!.post { swipeRefreshLayout!!.isRefreshing = refreshing }
    }

    fun showLoadingSourcesError() {
        setRefreshing(false)
        Toast.makeText(this, R.string.error_load_data, Toast.LENGTH_SHORT).show()
    }

    fun showNoSourcesData() {
        setRefreshing(false)
        Toast.makeText(this, R.string.empty_list, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            toolbar.title = title
        }
    }

    private fun initRecycler() {
        val recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        val itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = itemAnimator
        adapter = SourcesRecyclerViewAdapter(object : SourcesRecyclerViewAdapter.ItemClickListener() {
            fun onItemClick(source: Source) {
                showNewsActivity(source.getId())
            }
        }, ArrayList<Source>())
        recyclerView.adapter = adapter
    }

    private fun showNewsActivity(sourceId: String) {
        val intent = Intent(this, NewsActivity::class.java)
        intent.putExtra(NewsActivity.EXTRA_SOURCE_ID, sourceId)
        startActivity(intent)
    }

    private fun initSwipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener { presenter!!.loadSources() }
        swipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }
}
