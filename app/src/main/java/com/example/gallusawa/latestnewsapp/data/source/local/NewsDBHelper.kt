package com.example.gallusawa.latestnewsapp.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by gallusawa on 12/7/17.
 */
class NewsDBHelper(private val mContext: Context) : SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_SOURCES_TABLE = "CREATE TABLE " +
            NewsContract.SourcesEntry.TABLE_NAME + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NewsContract.SourcesEntry.SOURCE_ID + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.SourcesEntry.SOURCE_NAME + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.SourcesEntry.SOURCE_DESC + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.SourcesEntry.SOURCE_CATEGORY + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.SourcesEntry.SOURCE_URL + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.SourcesEntry.SOURCE_LOGO + " TEXT NOT NULL DEFAULT '', " +
            " UNIQUE (" + NewsContract.SourcesEntry.SOURCE_ID + ") ON CONFLICT REPLACE);"

    private val SQL_CREATE_ARTICLES_TABLE = "CREATE TABLE " +
            NewsContract.ArticlesEntry.TABLE_NAME + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NewsContract.ArticlesEntry.ART_SOURCE + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.ArticlesEntry.ART_AUTHOR + " TEXT DEFAULT '', " +
            NewsContract.ArticlesEntry.ART_IMAGE + " TEXT DEFAULT '', " +
            NewsContract.ArticlesEntry.ART_TITLE + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.ArticlesEntry.ART_DESC + " TEXT DEFAULT '', " +
            NewsContract.ArticlesEntry.ART_URL + " TEXT NOT NULL DEFAULT '', " +
            NewsContract.ArticlesEntry.ART_DATE + " TEXT NOT NULL DEFAULT '', " +
            " UNIQUE (" + NewsContract.ArticlesEntry.ART_URL + ") ON CONFLICT REPLACE);"

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_SOURCES_TABLE)
        sqLiteDatabase.execSQL(SQL_CREATE_ARTICLES_TABLE)

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        val DATABASE_NAME = "news.db"

        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
    }
}
