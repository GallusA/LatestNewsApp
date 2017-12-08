package com.example.gallusawa.latestnewsapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by gallusawa on 12/7/17.
 */


class SourceResponse {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("sources")
    var sources: List<Source>? = null
}
