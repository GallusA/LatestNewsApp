package com.example.gallusawa.latestnewsapp.sources

/**
 * Created by gallusawa on 12/7/17.
 */
class SourcesPresenter(repository: RepositoryDataSource,
                       view: SourcesContract.View) : SourcesContract.Presenter {

    private val view: SourcesContract.View
    private val repository: RepositoryDataSource

    init {
        this.repository = checkNotNull(repository, "repository cannot be null")
        this.view = checkNotNull(view, "View cannot be null!")
    }

    fun loadSources() {
        loadSourcesFromRepository(view.isNetworkAvailable())
    }

    private fun loadSourcesFromRepository(isNetworkAvailable: Boolean) {
        view.setRefreshing(true)
        repository.getSources(object : IRemoteDataSource.LoadDataCallback<Source>() {
            fun onDataLoaded(list: List<Source>) {
                if (list.isEmpty()) {
                    view.showNoSourcesData()
                }
                view.showSources(list)
                view.setRefreshing(false)
            }

            fun onDataNotAvailable() {
                if (!view.isActive()) {
                    return
                }
                view.showLoadingSourcesError()
            }
        }, isNetworkAvailable)

    }


}
