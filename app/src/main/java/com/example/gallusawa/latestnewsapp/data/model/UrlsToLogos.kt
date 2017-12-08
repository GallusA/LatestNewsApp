package com.example.gallusawa.latestnewsapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gallusawa on 12/7/17.
 */
class UrlsToLogos {

    @SerializedName("small")
    var small: String? = null
    @SerializedName("medium")
    var medium: String? = null
    @SerializedName("large")
    var large: String? = null

    constructor() {}

    constructor(medium: String) {
        this.medium = medium
    }
}
