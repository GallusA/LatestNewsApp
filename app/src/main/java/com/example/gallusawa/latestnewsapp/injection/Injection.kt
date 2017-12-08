package com.example.gallusawa.latestnewsapp.injection

import android.content.Context

/**
 * Created by gallusawa on 12/7/17.
 */
object Injection {

    fun provideRepository(context: Context): Repository {
        checkNotNull(context)
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        return Repository.getInstance(RemoteDataSource.getInstance(apiService),
                LocalDataSource.getInstance(context.contentResolver))
    }
}
