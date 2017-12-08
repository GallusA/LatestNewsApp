package com.example.gallusawa.latestnewsapp.data.source

import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.model.Source
import com.example.gallusawa.latestnewsapp.data.source.local.LocalDataSource
import com.example.gallusawa.latestnewsapp.data.source.remote.IRemoteDataSource
import com.example.gallusawa.latestnewsapp.data.source.remote.RemoteDataSource
import java.util.ArrayList

/**
 * Created by gallusawa on 12/7/17.
 */
class Repository private constructor(remoteDataSource: RemoteDataSource,
                                     localDataSource: LocalDataSource) : RepositoryDataSource {

    private val remoteDataSource: RemoteDataSource
    private val localDataSource: LocalDataSource

    init {

        this.remoteDataSource = checkNotNull<RemoteDataSource>(remoteDataSource)
        this.localDataSource = checkNotNull<LocalDataSource>(localDataSource)
    }

    override fun getArticles(sourceID: String,
                             callback: IDataSource.LoadDataCallback<Article>,
                             isNetworkAvailable: Boolean) {

        checkNotNull<IDataSource.LoadDataCallback<Article>>(callback)
        if (isNetworkAvailable) {
            getArticlesFromRemoteDataSource(sourceID, callback)
        } else {
            getArticlesFromLocalDataSource(sourceID, callback)
        }
    }

    override fun saveArticles(articles: List<Article>) {}

    override fun deleteAllArticles() {}

    override fun getSources(callback: IDataSource.LoadDataCallback<Source>,
                            isNetworkAvailable: Boolean) {
        checkNotNull<IDataSource.LoadDataCallback<Source>>(callback)
        if (isNetworkAvailable) {
            getSourcesFromRemoteDataSource(callback)
        } else {
            getSourcesFromLocalDataSource(callback)
        }
    }

    override fun saveSources(sources: List<Source>) {
        localDataSource.saveSources(sources)
    }


    override fun deleteAllSources() {
        localDataSource.deleteAllSources()
    }

    private fun getArticlesFromLocalDataSource(sourceId: String,
                                               callback: IDataSource.LoadDataCallback<Article>) {
        checkNotNull<IDataSource.LoadDataCallback<Article>>(callback)
        localDataSource.getArticles(sourceId, callback)

    }

    private fun getArticlesFromRemoteDataSource(sourceId: String,
                                                callback: IDataSource.LoadDataCallback<Article>) {

        remoteDataSource.getArticles(sourceId, object : IDataSource.LoadDataCallback<Article> {
            override fun onDataLoaded(articles: ArrayList<Article>) {
                refreshArticleLocalDataSource(sourceId, articles)
                callback.onDataLoaded(ArrayList<Article>(articles))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun getSourcesFromLocalDataSource(
            callback: IDataSource.LoadDataCallback<Source>) {

        checkNotNull<IDataSource.LoadDataCallback<Source>>(callback)
        localDataSource.getAllSources(callback)
    }

    private fun getSourcesFromRemoteDataSource(
            callback: IRemoteDataSource.LoadDataCallback<Source>) {

        remoteDataSource.getSources(object : IRemoteDataSource.LoadDataCallback<Source>() {
            fun onDataLoaded(sources: List<Source>) {
                refreshSourceLocalDataSource(sources)
                callback.onDataLoaded(ArrayList<Source>(sources))
            }

            fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshSourceLocalDataSource(sources: List<Source>) {
        localDataSource.deleteAllSources()
        localDataSource.saveSources(sources)
    }

    private fun refreshArticleLocalDataSource(sourceId: String, articles: ArrayList<Article>) {
        localDataSource.deleteAllArticles()
        localDataSource.saveArticles(sourceId, articles)
    }

    companion object {

        private var INSTANCE: Repository? = null


        fun getInstance(remoteDataSource: RemoteDataSource,
                        localDataSource: LocalDataSource): Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository(remoteDataSource, localDataSource)
            }
            return INSTANCE as Repository
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
