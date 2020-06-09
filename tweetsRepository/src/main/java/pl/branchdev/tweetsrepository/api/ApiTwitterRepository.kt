package pl.branchdev.tweetsrepository.api

import TweetDto
import com.google.gson.Gson
import io.reactivex.Observable
import okio.BufferedSource
import pl.branchdev.common.rx.SchedulerProvider
import pl.branchdev.tweetsrepository.TwitterRepository
import java.io.IOException

class ApiTwitterRepository(
    private val apiService: TwitterApiService,
    private val gson: Gson,
    private val schedulerProvider: SchedulerProvider
) :
    TwitterRepository {
    override fun statusesStreamObservable(): Observable<TweetDto> =
        apiService.statuses().subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.computation())
            .flatMap { mapResponseBodyToStringObservable(it.source()) }
            .map { gson.fromJson(it, TweetDto::class.java) }


    private fun mapResponseBodyToStringObservable(source: BufferedSource): Observable<String> {
        return Observable.create {
            try {
                while (!source.exhausted()) {
                    it.onNext(source.readUtf8Line().toString())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            it.onComplete()
        }
    }
}