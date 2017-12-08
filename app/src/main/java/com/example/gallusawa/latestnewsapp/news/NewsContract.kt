package com.example.gallusawa.latestnewsapp.news

/**
 * Created by gallusawa on 12/7/17.
 */
interface NewsContract {

    interface View : BaseView {

        val isNetworkAvailable: Boolean

        val isActive: Boolean

        fun showArticles(sources: List<Article>)

        fun setRefreshing(refreshing: Boolean)

        fun showLoadingSourcesError()

        fun showNoSourcesData()
    }

    interface Presenter : BasePresenter {

        fun loadArticles(sourceId: String)
    }
}