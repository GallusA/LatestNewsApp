package com.example.gallusawa.latestnewsapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gallusawa on 12/7/17.
 */
 class ArticleResponse {

    @SerializedName("status")
    private var status: String? = null
    @SerializedName("sortBy")
    private var sortBy: String? = null
    @SerializedName("source")
    private var source: String? = null
    @SerializedName("articles")
    private var articles: List<Article>? = null

    fun ArticleResponse(){}

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getSortBy(): String? {
        return sortBy
    }

    fun setSortBy(sortBy: String) {
        this.sortBy = sortBy
    }

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun getArticles(): List<Article>? {
        return articles
    }

    fun setArticles(articles: List<Article>) {
        this.articles = articles
    }
}
