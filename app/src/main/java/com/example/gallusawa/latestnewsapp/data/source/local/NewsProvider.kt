package com.example.gallusawa.latestnewsapp.data.source.local

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by gallusawa on 12/7/17.
 */


class NewsProvider : ContentProvider() {

    private var openHelper: NewsDBHelper? = null

    private fun getSourceById(uri: Uri, projection: Array<String>?, sortOrder: String?): Cursor {
        val selectionId = String.valueOf(NewsContract.SourcesEntry.getIdFromUri(uri))
        val selectionArgs = arrayOf<String>(selectionId)
        return sSourceByIdQueryBuilder.query(openHelper!!.readableDatabase,
                projection,
                sourceByIdSelection,
                selectionArgs, null, null,
                sortOrder
        )
    }

    private fun getArticleById(uri: Uri, projection: Array<String>?, sortOrder: String?): Cursor {
        val selectionId = String.valueOf(NewsContract.ArticlesEntry.getIdFromUri(uri))
        val selectionArgs = arrayOf<String>(selectionId)
        return sAtricleByIdQueryBuilder.query(openHelper!!.readableDatabase,
                projection,
                articleByIdSelection,
                selectionArgs, null, null,
                sortOrder
        )
    }

    override fun onCreate(): Boolean {
        openHelper = NewsDBHelper(context)
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = openHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        val rowsDeleted: Int
        when (match) {
            SOURCES -> rowsDeleted = db.delete(
                    NewsContract.SourcesEntry.TABLE_NAME, selection, selectionArgs)
            ARTICLES -> rowsDeleted = db.delete(
                    NewsContract.ArticlesEntry.TABLE_NAME, selection, selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rowsDeleted
    }

    override fun getType(uri: Uri): String? {
        // Use the Uri Matcher to determine what kind of URI this is.
        val match = sUriMatcher.match(uri)
        when (match) {
            SOURCES -> return NewsContract.SourcesEntry.CONTENT_TYPE
            SOURCES_WITH_ID -> return NewsContract.SourcesEntry.CONTENT_ITEM_TYPE
            ARTICLES -> return NewsContract.ArticlesEntry.CONTENT_TYPE
            ARTICLES_WITH_ID -> return NewsContract.ArticlesEntry.CONTENT_ITEM_TYPE
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = openHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        val returnUri: Uri
        when (match) {
            SOURCES -> {
                val _id = db.insert(NewsContract.SourcesEntry.TABLE_NAME, null, values)
                if (_id > 0)
                    returnUri = NewsContract.SourcesEntry.buildSourceUri(_id)
                else
                    throw android.database.SQLException("Failed to insert row into " + uri)
            }
            ARTICLES -> {
                val _id = db.insert(NewsContract.ArticlesEntry.TABLE_NAME, null, values)
                if (_id > 0)
                    returnUri = NewsContract.ArticlesEntry.buildArticleUri(_id)
                else
                    throw android.database.SQLException("Failed to insert row into " + uri)
            }
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        context!!.contentResolver.notifyChange(uri, null)
        return returnUri
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val retCursor: Cursor
        when (sUriMatcher.match(uri)) {
            SOURCES -> {
                retCursor = openHelper!!.readableDatabase.query(
                        NewsContract.SourcesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder
                )
            }
            SOURCES_WITH_ID -> {
                retCursor = getSourceById(uri, projection, sortOrder)
            }
            ARTICLES -> {
                retCursor = openHelper!!.readableDatabase.query(
                        NewsContract.ArticlesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder
                )
            }
            ARTICLES_WITH_ID -> {
                retCursor = getArticleById(uri, projection, sortOrder)
            }
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        retCursor.setNotificationUri(context!!.contentResolver, uri)
        return retCursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val db = openHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        val rowsUpdated: Int
        when (match) {
            SOURCES -> rowsUpdated = db.update(NewsContract.SourcesEntry.TABLE_NAME, values, selection,
                    selectionArgs)
            ARTICLES -> rowsUpdated = db.update(NewsContract.ArticlesEntry.TABLE_NAME, values, selection,
                    selectionArgs)
            else -> throw UnsupportedOperationException("Unknown uri: " + uri)
        }
        if (rowsUpdated != 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rowsUpdated
    }

    override fun bulkInsert(uri: Uri, values: Array<ContentValues>): Int {
        val db = openHelper!!.writableDatabase
        val match = sUriMatcher.match(uri)
        var returnCount = 0
        when (match) {
            SOURCES -> {
                db.beginTransaction()
                try {
                    for (value in values) {
                        val _id = db.insert(NewsContract.SourcesEntry.TABLE_NAME, null, value)
                        if (_id != -1) {
                            returnCount++
                        }
                    }
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
                context!!.contentResolver.notifyChange(uri, null)
                return returnCount
            }
            ARTICLES -> {
                db.beginTransaction()
                try {
                    for (value in values) {
                        val _id = db.insert(NewsContract.ArticlesEntry.TABLE_NAME, null, value)
                        if (_id != -1) {
                            returnCount++
                        }
                    }
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
                context!!.contentResolver.notifyChange(uri, null)
                return returnCount
            }
            else -> return super.bulkInsert(uri, values)
        }
    }

    companion object {

        // The URI Matcher used by this content provider.
        private val sUriMatcher = buildUriMatcher()

        internal val SOURCES = 1000
        internal val SOURCES_WITH_ID = 1001
        internal val ARTICLES = 1002
        internal val ARTICLES_WITH_ID = 1003

        private val sSourceByIdQueryBuilder: SQLiteQueryBuilder
        private val sAtricleByIdQueryBuilder: SQLiteQueryBuilder

        init {
            sSourceByIdQueryBuilder = SQLiteQueryBuilder()
            sSourceByIdQueryBuilder.tables = NewsContract.SourcesEntry.TABLE_NAME
            sAtricleByIdQueryBuilder = SQLiteQueryBuilder()
            sAtricleByIdQueryBuilder.tables = NewsContract.ArticlesEntry.TABLE_NAME
        }

        internal fun buildUriMatcher(): UriMatcher {
            val matcher = UriMatcher(UriMatcher.NO_MATCH)
            val authority = NewsContract.CONTENT_AUTHORITY
            // For each type of URI you want to add, create a corresponding code.
            matcher.addURI(authority, NewsContract.PATH_SOURCES, SOURCES)
            matcher.addURI(authority, NewsContract.PATH_SOURCES + "/#", SOURCES_WITH_ID)
            matcher.addURI(authority, NewsContract.PATH_ARTICLES, ARTICLES)
            matcher.addURI(authority, NewsContract.PATH_ARTICLES + "/#", ARTICLES_WITH_ID)
            return matcher
        }

        private val sourceByIdSelection =
                NewsContract.SourcesEntry.TABLE_NAME + "." + BaseColumns._ID+ " = ? "

        private val articleByIdSelection =
                NewsContract.ArticlesEntry.TABLE_NAME + "." + BaseColumns._ID + " = ? "
    }
}
