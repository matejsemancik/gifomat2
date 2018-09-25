package io.futured.gifomat2

import android.app.Application
import io.futured.gifomat2.di.koinModules
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin(this, koinModules())
    }
}

