package com.example.gallusawa.latestnewsapp.data.source.local

import android.content.ContentValues
import android.os.Build
import android.support.annotation.RequiresApi
import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.model.Source

/**
 * Created by gallusawa on 12/7/17.
 */
object NewsValues {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun from(source: Source): ContentValues {
        val values = ContentValues()
        values.put(NewsContract.SourcesEntry.SOURCE_ID, source.id)
        values.put(NewsContract.SourcesEntry.SOURCE_NAME, source.name)
        values.put(NewsContract.SourcesEntry.SOURCE_DESC, source.description)
        values.put(NewsContract.SourcesEntry.SOURCE_CATEGORY, source.category)
        values.put(NewsContract.SourcesEntry.SOURCE_URL, source.url)
        values.put(NewsContract.SourcesEntry.SOURCE_LOGO, source.url)
        return values
    }

    fun from(sourceId: String, article: Article): ContentValues {
        val values = ContentValues()
        values.put(NewsContract.ArticlesEntry.ART_SOURCE, sourceId)
        values.put(NewsContract.ArticlesEntry.ART_AUTHOR, article.author)
        values.put(NewsContract.ArticlesEntry.ART_IMAGE, article.imageUrl)
        values.put(NewsContract.ArticlesEntry.ART_TITLE, article.title)
        values.put(NewsContract.ArticlesEntry.ART_DESC, article.decr)
        values.put(NewsContract.ArticlesEntry.ART_URL, article.url)
        values.put(NewsContract.ArticlesEntry.ART_DATE, article.publishedAt)
        return values
    }
}
