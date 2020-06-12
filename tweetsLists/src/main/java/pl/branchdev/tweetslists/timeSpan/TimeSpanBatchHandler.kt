package pl.branchdev.tweetsstreamlist.timeSpan

interface TimeSpanBatchHandler {
    fun stopCounting()
    fun addCounter(counter: TimeCounter)
    fun removeCounter(counter: TimeCounter)
}