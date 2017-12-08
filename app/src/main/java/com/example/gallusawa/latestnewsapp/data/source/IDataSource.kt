package com.example.gallusawa.latestnewsapp.data.source

import com.example.gallusawa.latestnewsapp.data.model.Article
import java.util.ArrayList

/**
 * Created by gallusawa on 12/7/17.
 */
interface IDataSource {

    interface LoadDataCallback<T> {

        fun onDataLoaded(list: ArrayList<Article>)

        fun onDataNotAvailable()
    }
}