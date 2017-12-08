package com.example.gallusawa.latestnewsapp.data.source.remote

import android.util.Log
import com.example.gallusawa.latestnewsapp.BuildConfig
import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.model.ArticleResponse
import com.example.gallusawa.latestnewsapp.data.model.SourceResponse
import com.example.gallusawa.latestnewsapp.data.source.IDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by gallusawa on 12/7/17.
 */
class RemoteDataSource private constructor(private val service: ApiService) : IRemoteDataSource {

    fun getSources(callback: Any) {
        val articleResponseCall = service.getSource("en")
        articleResponseCall.enqueue(object : Callback<SourceResponse> {
            override fun onResponse(call: Call<SourceResponse>, response: Response<SourceResponse>) {
                if ("ok" == response.body()!!.getStatus()) {
                    if (!response.body()!!.getSources().isEmpty()) {
                        callback.onDataLoaded(response.body()!!.getSources())
                    } else {
                        Log.e(LOG_TAG, "Oops, something went wrong!")
                    }
                } else {
                    Log.e(LOG_TAG, "Oops, something went wrong!")
                }
            }

            override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                Log.e(LOG_TAG, "Error:" + t.message)
            }
        })

    }

    fun getArticles(source: String, callback: IDataSource.LoadDataCallback<Article>) {
        val articleResponseCall = service.getArticle(BuildConfig.API_KEY, source, "top")
        articleResponseCall.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.body() != null) {
                    if ("ok" == response.body()!!.getStatus()) {
                        if (!response.body()!!.getArticles().isEmpty()) {
                            callback.onDataLoaded(response.body()!!.getArticles())
                        } else {
                            callback.onDataNotAvailable()
                            Log.e(LOG_TAG, "Oops, something went wrong!")
                        }
                    } else {
                        callback.onDataNotAvailable()
                        Log.e(LOG_TAG, "Oops, something went wrong!")
                    }
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(LOG_TAG, "Error:" + t.message)
                callback.onDataNotAvailable()
            }
        })

    }

    companion object {

        private val LOG_TAG = RemoteDataSource::class.java.simpleName

        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = RemoteDataSource(service)
            }
            return INSTANCE
        }
    }
}
