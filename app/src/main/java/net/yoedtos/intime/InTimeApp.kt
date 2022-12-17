package net.yoedtos.intime

import android.app.Application
import android.content.Context
import android.util.Log

private val LOG_TAG = InTimeApp::class.simpleName

class InTimeApp: Application() {

    companion object {
        var instance: InTimeApp? = null
            private set
    }

    override fun onCreate() {
        Log.d(LOG_TAG, "On Create")
        super.onCreate()
        instance = this
    }

    override fun onLowMemory() {
        Log.d(LOG_TAG, "On Low Memory")
        super.onLowMemory()
    }


    override fun onTerminate() {
        Log.d(LOG_TAG, "On Terminate")
        super.onTerminate()
    }

    fun getContext(): Context {
        return this.applicationContext
    }
}