package com.example.gallusawa.latestnewsapp.data.source.remote

import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.source.IDataSource

/**
 * Created by gallusawa on 12/7/17.
 */
interface IRemoteDataSource : IDataSource {

    fun getSources(callback: Any)

    fun getArticles(source: String, callback: IDataSource.LoadDataCallback<Article>)
}