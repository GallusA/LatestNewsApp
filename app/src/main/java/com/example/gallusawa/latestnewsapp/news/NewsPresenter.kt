package com.example.gallusawa.latestnewsapp.news

/**
 * Created by gallusawa on 12/7/17.
 */
class NewsPresenter(repository: RepositoryDataSource,
                    view: NewsContract.View) : NewsContract.Presenter {

    private val view: NewsContract.View
    private val repository: RepositoryDataSource

    init {
        this.repository = checkNotNull(repository, "repository cannot be null")
        this.view = checkNotNull(view, "View cannot be null!")
    }

    fun loadArticles(sourceId: String) {
        loadNewsFromRepository(sourceId, view.isNetworkAvailable())
    }

    private fun loadNewsFromRepository(source: String, isNetworkAvailable: Boolean) {
        view.setRefreshing(true)
        repository.getArticles(source, object : IRemoteDataSource.LoadDataCallback<Article>() {
            fun onDataLoaded(list: List<Article>) {
                if (list.isEmpty()) {
                    view.showNoSourcesData()
                }
                view.showArticles(list)
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
