package pl.branchdev.common.logger

import android.util.Log

class ConsoleLogger : LoggerProvider {
    override fun logInfo(tittle: String, value: String?) {
        Log.i(tittle, value ?: "")
    }

    override fun logError(tittle: String, value: String?) {
        Log.e(tittle, value ?: "")
    }

}