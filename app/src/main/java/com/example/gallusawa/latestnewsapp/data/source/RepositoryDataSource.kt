package com.example.gallusawa.latestnewsapp.data.source

import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.model.Source

/**
 * Created by gallusawa on 12/7/17.
 */
interface RepositoryDataSource {

    fun getArticles(source: String,
                    callback: IDataSource.LoadDataCallback<Article>,
                    isNetworkAvailable: Boolean)

    fun saveArticles(articles: List<Article>)

    fun deleteAllArticles()

    fun getSources(callback: IDataSource.LoadDataCallback<Source>,
                   isNetworkAvailable: Boolean)

    fun saveSources(sources: List<Source>)

    fun deleteAllSources()

}
