package com.example.gallusawa.latestnewsapp.data.source.remote

import com.example.gallusawa.latestnewsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by gallusawa on 12/7/17.
 */


class ApiClient {
    companion object {

        private var sRetrofit: Retrofit? = null

        val client: Retrofit?
            get() {
                if (sRetrofit == null) {
                    synchronized(Retrofit::class.java) {
                        if (sRetrofit == null) {
                            val interceptor = HttpLoggingInterceptor()
                            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                            sRetrofit = Retrofit.Builder()
                                    .baseUrl(BuildConfig.BASE_API_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build()
                        }
                    }
                }
                return sRetrofit
            }
    }

}
