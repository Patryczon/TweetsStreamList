package pl.branchdev.tweetslists.timeSpan

class TimeSpanCounterBatchHandler : TimeSpanBatchHandler {
    private var timeCounters = mutableListOf<TimeCounter>()

    override fun stopCounting() {
        timeCounters.forEach { it.stopCount() }
        timeCounters.clear()
    }

    override fun addCounter(counter: TimeCounter) {
        timeCounters.add(counter)
        counter.startCount()
    }

    override fun removeCounter(counter: TimeCounter) {
        timeCounters.remove(counter)
    }

}