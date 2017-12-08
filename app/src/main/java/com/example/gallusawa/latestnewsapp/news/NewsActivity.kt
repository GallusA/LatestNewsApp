package com.example.gallusawa.latestnewsapp.news

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.example.gallusawa.latestnewsapp.R
import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.injection.Injection
import java.util.ArrayList

/**
 * Created by Igor Havrylyuk on 27.03.2017.
 */

class NewsActivity : AppCompatActivity(), NewsContract.View {
    private val REQUEST_CODE_ASK_PERMISSIONS = 123

    private var presenter: NewsContract.Presenter? = null
    private var adapter: NewsRecyclerViewAdapter? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var sourceId: String? = null

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
        if (intent != null) {
            sourceId = intent.getStringExtra(EXTRA_SOURCE_ID)
        }
        setContentView(R.layout.activity_content)
        initToolbar()
        initRecycler()
        initSwipeToRefresh()
        initPresenter()

        requestPermission()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(this@NewsActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_ASK_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Toast.makeText(this@NewsActivity, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
            } else {
                // Permission Denied
                Toast.makeText(this@NewsActivity, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter!!.loadArticles(this!!.sourceId!!)
    }

    // Create the presenter
    private fun initPresenter() {
        presenter = NewsPresenter(
                Injection.provideRepository(applicationContext), this)
    }

     fun showArticles(articles: List<Article>) {
        if (adapter != null) {
            adapter!!.addArticles(articles)
        }
    }

    override fun setRefreshing(refreshing: Boolean) {
        if (swipeRefreshLayout == null) {
            return
        }
        swipeRefreshLayout!!.post { swipeRefreshLayout!!.isRefreshing = refreshing }
    }

    override fun showLoadingSourcesError() {
        setRefreshing(false)
        Toast.makeText(this, R.string.error_load_data, Toast.LENGTH_SHORT).show()
    }

    override fun showNoSourcesData() {
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
        adapter = NewsRecyclerViewAdapter(object : NewsRecyclerViewAdapter.ItemClickListener() {
            fun onItemClick(article: Article) {
                showNewsInWebBrowser(article.url!!)
            }
        }, ArrayList<Article>())
        recyclerView.adapter = adapter
    }

    private fun showNewsInWebBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }


    private fun initSwipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener { presenter!!.loadArticles(this!!.sourceId!!) }
        swipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    companion object {

        val EXTRA_SOURCE_ID = "com.gallusawa.newsapp.EXTRA_SOURCE_ID"
    }
}
