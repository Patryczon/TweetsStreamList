package pl.branchdev.common.logger

interface LoggerProvider {
    fun logInfo(tittle: String, value: String?)
    fun logError(tittle: String, value: String?)
}