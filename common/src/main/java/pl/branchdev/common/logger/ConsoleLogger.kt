package pl.branchdev.common.logger

import android.util.Log

class ConsoleLogger : LoggerProvider {
    override fun logInfo(title: String, value: String?) {
        Log.i(title, value ?: "")
    }

    override fun logError(title: String, value: String?) {
        Log.e(title, value ?: "")
    }

}