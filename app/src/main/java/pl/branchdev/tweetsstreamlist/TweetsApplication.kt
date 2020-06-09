package pl.branchdev.tweetsstreamlist

import android.app.Application
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.branchdev.tweetsstreamlist.di.modules

class TweetsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // use the Android context given there
            androidContext(this@TweetsApplication)
            modules(modules)
        }
        RxJavaPlugins.setErrorHandler { e -> Log.e("rxJavaError", e.message) }
    }
}