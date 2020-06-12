package pl.branchdev.tweetsstreamlist

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.branchdev.common.logger.LoggerProvider
import pl.branchdev.tweetsstreamlist.di.modules

class TweetsApplication : Application() {
    private val logger: LoggerProvider by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TweetsApplication)
            modules(modules)
        }
        RxJavaPlugins.setErrorHandler { e -> logger.logError("rxJavaError", e.message) }
    }
}