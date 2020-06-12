package pl.branchdev.tweetslists.timeSpan

interface TimeSpanBatchHandler {
    fun stopCounting()
    fun addCounter(counter: TimeCounter)
    fun removeCounter(counter: TimeCounter)
}