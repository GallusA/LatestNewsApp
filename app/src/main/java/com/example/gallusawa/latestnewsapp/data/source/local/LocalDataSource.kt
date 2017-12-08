package com.example.gallusawa.latestnewsapp.data.source.local

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.BaseColumns
import com.example.gallusawa.latestnewsapp.data.model.Article
import com.example.gallusawa.latestnewsapp.data.model.Source
import com.example.gallusawa.latestnewsapp.data.model.UrlsToLogos
import com.example.gallusawa.latestnewsapp.data.source.IDataSource.LoadDataCallback
import java.util.ArrayList

/**
 * Created by gallusawa on 12/7/17.
 */
class LocalDataSource private constructor(private val contentResolver: ContentResolver) : ILocalDataSource {
    override fun getSource(sourceId: String, callback: LoadDataCallback<Source>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        checkNotNull(contentResolver)
    }

    fun getSource(sourceId: String, callback: LoadDataCallback) {}

    override fun getAllSources(callback: LoadDataCallback<Source>) {
        val sources = ArrayList<Source>()
        val c = contentResolver.query(NewsContract.SourcesEntry.CONTENT_URI, null, null, null, null)
        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                val id = c.getString(c.getColumnIndexOrThrow(NewsContract.SourcesEntry.SOURCE_ID))
                val name = c.getString(c.getColumnIndexOrThrow(NewsContract.SourcesEntry.SOURCE_NAME))
                val desc = c.getString(c.getColumnIndexOrThrow(NewsContract.SourcesEntry.SOURCE_DESC))
                val logo = c.getString(c.getColumnIndexOrThrow(NewsContract.SourcesEntry.SOURCE_LOGO))
                val task = Source(id, name, desc, UrlsToLogos(logo))
                sources.add(task)
            }
        }
        c?.close()
        if (sources.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onDataLoaded(sources)
        }
    }

    override fun refreshSource(source: Source) {
        checkNotNull(source)
        val values = NewsValues.from(source)
        val selection = NewsContract.SourcesEntry.SOURCE_ID + " = ?"
        val selectionArgs = arrayOf<String>(source.id!!)
        contentResolver.update(NewsContract.SourcesEntry.CONTENT_URI, values, selection, selectionArgs)
    }

    override fun saveSource(source: Source) {
        checkNotNull(source)
        val values = NewsValues.from(source)
        contentResolver.insert(NewsContract.SourcesEntry.CONTENT_URI, values)
    }

    override fun saveSources(sources: List<Source>) {
        checkNotNull(sources)
        val values = arrayOfNulls<ContentValues>(sources.size)
        for (i in sources.indices) {
            values[i] = NewsValues.from(sources[i])
        }
        contentResolver.bulkInsert(NewsContract.SourcesEntry.CONTENT_URI, values)
    }

    override fun deleteAllSources() {
        contentResolver.delete(NewsContract.SourcesEntry.CONTENT_URI, null, null)
    }

    override fun deleteSource(sourceId: String) {
        checkNotNull(sourceId)
        val selection = BaseColumns._ID + " = ?"
        val selectionArgs = arrayOf(sourceId)
        contentResolver.delete(NewsContract.SourcesEntry.CONTENT_URI, selection, selectionArgs)
    }

    override fun getArticles(sourceId: String, callback: LoadDataCallback<Article>) {
        val articles = ArrayList<Article>()
        val c = contentResolver.query(NewsContract.ArticlesEntry.CONTENT_URI, null,
                NewsContract.ArticlesEntry.ART_SOURCE + " = ? ",
                arrayOf(sourceId), null)
        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                val author = c.getString(c.getColumnIndexOrThrow(NewsContract.ArticlesEntry.ART_AUTHOR))
                val title = c.getString(c.getColumnIndexOrThrow(NewsContract.ArticlesEntry.ART_TITLE))
                val desc = c.getString(c.getColumnIndexOrThrow(NewsContract.ArticlesEntry.ART_DESC))
                val date = c.getString(c.getColumnIndexOrThrow(NewsContract.ArticlesEntry.ART_DATE))
                val image = c.getString(c.getColumnIndexOrThrow(NewsContract.ArticlesEntry.ART_IMAGE))
                val url = c.getString(c.getColumnIndexOrThrow(NewsContract.ArticlesEntry.ART_URL))
                val article = Article(author, image, title, desc, url, date)
                articles.add(article)
            }
        }
        c?.close()
        if (articles.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        } else {
            callback.onDataLoaded(articles)
        }
    }

    override fun getAllArticles() {}

    override fun refreshArticle(sourceId: String, article: Article) {
        checkNotNull(article)
        val values = NewsValues.from(sourceId, article)
        val selection = BaseColumns._ID + " = ?"
        // val selection = NewsContract.ArticlesEntry._ID + " = ?"
        val selectionArgs = arrayOf<String>(article.sourceId!!)
        contentResolver.update(NewsContract.ArticlesEntry.CONTENT_URI, values, selection, selectionArgs)
    }

    override fun saveArticles(sourceId: String, article: Article) {
        checkNotNull(article)
        val values = NewsValues.from(sourceId, article)
        contentResolver.insert(NewsContract.ArticlesEntry.CONTENT_URI, values)
    }

    override fun saveArticles(sourceId: String, articles: List<Article>) {
        if (!checkNotNull(articles).isEmpty()) {
            deleteArticles(sourceId)
            val values = arrayOfNulls<ContentValues>(articles.size)
            for (i in articles.indices) {
                values[i] = NewsValues.from(sourceId, articles[i])
            }
            contentResolver.bulkInsert(NewsContract.ArticlesEntry.CONTENT_URI, values)
        }

    }

    override fun deleteAllArticles() {
        contentResolver.delete(NewsContract.ArticlesEntry.CONTENT_URI, null, null)
    }

    override fun deleteArticles(sourceId: String) {
        checkNotNull(sourceId)
        val selection = NewsContract.ArticlesEntry.ART_SOURCE + " = ?"
        val selectionArgs = arrayOf(sourceId)
        contentResolver.delete(NewsContract.ArticlesEntry.CONTENT_URI, selection, selectionArgs)
    }

    companion object {


        private val LOG_TAG = LocalDataSource::class.java.simpleName

        private var INSTANCE: LocalDataSource? = null

        fun getInstance(contentResolver: ContentResolver): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(contentResolver)
            }
            return INSTANCE as LocalDataSource
        }
    }


}
