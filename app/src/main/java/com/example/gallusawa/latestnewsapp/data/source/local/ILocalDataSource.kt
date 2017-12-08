package com.example.gallusawa.latestnewsapp.data.source.local

import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.model.Source
import com.example.gallusawa.latestnewsapp.data.source.IDataSource

/**
 * Created by gallusawa on 12/7/17.
 */
interface ILocalDataSource : IDataSource {

    fun getSource(sourceId: String,
                  callback: IDataSource.LoadDataCallback<Source>)

    fun getAllSources(callback: IDataSource.LoadDataCallback<Source>)

    fun refreshSource(source: Source)

    fun saveSource(source: Source)

    fun saveSources(source: List<Source>)

    fun deleteAllSources()

    fun deleteSource(sourceId: String)

    fun getArticles(sourceId: String,
                    callback: IDataSource.LoadDataCallback<Article>)

    fun getAllArticles()

    fun refreshArticle(sourceId: String, article: Article)

    fun saveArticles(sourceId: String, article: Article)

    fun saveArticles(sourceId: String, articles: List<Article>)

    fun deleteAllArticles()

    fun deleteArticles(sourceId: String)
}
