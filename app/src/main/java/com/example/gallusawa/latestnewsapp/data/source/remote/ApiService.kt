package com.example.gallusawa.latestnewsapp.data.source.remote

import com.example.gallusawa.latestnewsapp.data.model.ArticleResponse
import com.example.gallusawa.latestnewsapp.data.model.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by gallusawa on 12/7/17.
 */
interface ApiService {

    @GET("articles")
    fun getArticle(
            @Query("apiKey") apiKey: String,
            @Query("source") sources: String,
            @Query("sortBy") sortBy: String): Call<ArticleResponse>  //top, latest, popular

    //Possible category options: business, entertainment, gaming, general, music, science-and-nature, sport, technology.
    //Possible language options: en, de, fr.
    //Possible country options: au, de, gb, in, it, us.
    @GET("sources")
    fun getSource(
            @Query("language") countryCode: String): Call<SourceResponse>

}
