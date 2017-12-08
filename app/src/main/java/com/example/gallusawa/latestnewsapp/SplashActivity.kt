package com.example.gallusawa.latestnewsapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by gallusawa on 12/6/17.
 */
class SplashActivity : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}