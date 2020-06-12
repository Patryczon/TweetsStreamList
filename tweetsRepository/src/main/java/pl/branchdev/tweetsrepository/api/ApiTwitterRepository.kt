package pl.branchdev.tweetsrepository.api

import com.google.gson.Gson
import io.reactivex.Observable
import okio.BufferedSource
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.data.TweetDto
import pl.branchdev.tweetsrepository.TwitterRepository
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ApiTwitterRepository(
    private val apiService: TwitterApiService,
    private val gson: Gson,
    private val schedulerProvider: SchedulerProvider
) :
    TwitterRepository {
    override fun statusesStreamObservable(searchQuery: String): Observable<TweetDto> =
        apiService.statuses(searchQuery).subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.computation())
            .flatMap { mapResponseBodyToStringObservable(it.source()) }
            .retryWhen { throwable ->
                throwable.flatMap {
                    if (shouldReconnect(it))
                        return@flatMap Observable.just(Object())
                    else
                        return@flatMap Observable.error<Throwable> { it }
                }
            }.map { gson.fromJson(it, TweetDto::class.java) }

    private fun shouldReconnect(error: Throwable) =
        error is SocketTimeoutException || error is HttpException


    private fun mapResponseBodyToStringObservable(source: BufferedSource): Observable<String> {
        return Observable.create {
            try {
                while (!source.exhausted()) {
                    it.onNext(source.readUtf8Line().toString())
                }
            } catch (e: IOException) {
                e.printStackTrace()
                it.onError(e)
            }
            it.onComplete()
        }
    }
}