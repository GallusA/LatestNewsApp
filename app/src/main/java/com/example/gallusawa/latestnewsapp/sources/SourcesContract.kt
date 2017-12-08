package com.example.gallusawa.latestnewsapp.sources

/**
 * Created by gallusawa on 12/7/17.
 */
interface SourcesContract {

    interface View : BaseView {

        val isNetworkAvailable: Boolean

        val isActive: Boolean

        fun showSources(sources: List<Source>)

        fun setRefreshing(refreshing: Boolean)

        fun showLoadingSourcesError()

        fun showNoSourcesData()
    }

    interface Presenter : BasePresenter {

        fun loadSources()
    }
}
