package com.example.gallusawa.latestnewsapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by gallusawa on 12/7/17.
 */
class Article {

    @Expose
    var sourceId: String? = null
    @SerializedName("author")
    var author: String? = null
    @SerializedName("urlToImage")
    var imageUrl: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var decr: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("publishedAt")
    var publishedAt: String? = null

    constructor() {}

    constructor(author: String, imageUrl: String, title: String, decr: String, url: String, publishedAt: String) {
        this.author = author
        this.imageUrl = imageUrl
        this.title = title
        this.decr = decr
        this.url = url
        this.publishedAt = publishedAt
    }

}
