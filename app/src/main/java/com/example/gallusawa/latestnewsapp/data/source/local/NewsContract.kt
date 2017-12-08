package com.example.gallusawa.latestnewsapp.data.source.local

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import com.example.gallusawa.latestnewsapp.BuildConfig

/**
 * Created by gallusawa on 12/7/17.
 */
object NewsContract {

    val CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID//"com.havrylyuk.newsmvp";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    val BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)

    val PATH_SOURCES = "sources"
    val PATH_ARTICLES = "articles"

    class SourcesEntry : BaseColumns {
        companion object {

            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SOURCES).build()

            val CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOURCES
            val CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOURCES

            // Table name
            val TABLE_NAME = "sources"

            val SOURCE_ID = "source_id"
            val SOURCE_NAME = "name"
            val SOURCE_DESC = "description"
            val SOURCE_CATEGORY = "category"
            val SOURCE_URL = "url"
            val SOURCE_LOGO = "logo"

            fun buildSourceUri(id: Long): Uri {
                return ContentUris.withAppendedId(CONTENT_URI, id)
            }

            fun getIdFromUri(uri: Uri): Long {
                return java.lang.Long.parseLong(uri.pathSegments[1])
            }
        }
    }

    class ArticlesEntry : BaseColumns {
        companion object {

            val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLES).build()

            val CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES
            val CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES

            // Table name
            val TABLE_NAME = "articles"

            val ART_SOURCE = "source"
            val ART_AUTHOR = "author"
            val ART_IMAGE = "image_url"
            val ART_TITLE = "title"
            val ART_DESC = "description"
            val ART_URL = "url"
            val ART_DATE = "pub_date"

            fun buildArticleUri(id: Long): Uri {
                return ContentUris.withAppendedId(CONTENT_URI, id)
            }

            fun getIdFromUri(uri: Uri): Long {
                return java.lang.Long.parseLong(uri.pathSegments[1])
            }
        }
    }
}
