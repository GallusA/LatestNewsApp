package com.example.gallusawa.latestnewsapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gallusawa on 12/7/17.
 */
class Source {

    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("category")
    var category: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("urlsToLogos")
    var urlsToLogos: UrlsToLogos? = null
    @SerializedName("sortBysAvailable")
    var sortBysAvailable: List<String>? = null

    constructor() {}

    constructor(id: String, name: String, description: String, urlsToLogos: UrlsToLogos) {
        this.id = id
        this.name = name
        this.description = description
        this.urlsToLogos = urlsToLogos
    }

    constructor(id: String, name: String, urlsToLogos: UrlsToLogos) {
        this.id = id
        this.name = name
        this.urlsToLogos = urlsToLogos
    }

    /*sources": [
            -
    {
        "id": "abc-news-au",
            "name": "ABC News (AU)",
            "description": "Australia's most truste ... the latest business, sport, weather and more.",
            "url": "http://www.abc.net.au/news",
            "category": "general",
            "language": "en",
            "country": "au",
            -
                    "urlsToLogos": {
        "small": "http://i.newsapi.org/abc-news-au-s.png",
                "medium": "http://i.newsapi.org/abc-news-au-m.png",
                "large": "http://i.newsapi.org/abc-news-au-l.png"
    },
        -
                "sortBysAvailable": [
        "top"
]
    }
*/

}
