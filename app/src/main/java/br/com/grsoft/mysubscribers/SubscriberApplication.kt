package br.com.grsoft.mysubscribers

import android.app.Application
import timber.log.Timber.DebugTree
import timber.log.Timber.plant

class SubscriberApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
    }
}