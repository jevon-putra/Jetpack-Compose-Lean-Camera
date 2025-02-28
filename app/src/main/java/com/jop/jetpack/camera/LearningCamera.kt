package com.jop.jetpack.camera

import android.app.Application
import com.jop.jetpack.camera.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class LearningCamera: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LearningCamera)
            androidLogger()
            modules(appModule())
        }
    }
}