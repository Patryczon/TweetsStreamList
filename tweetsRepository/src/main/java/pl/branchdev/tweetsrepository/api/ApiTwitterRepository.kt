package pl.branchdev.tweetsrepository.api

import TweetDto
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import okio.BufferedSource
import pl.branchdev.tweetsrepository.TwitterRepository
import java.io.IOException

class ApiTwitterRepository(val apiService: TwitterApiService, val gson: Gson) : TwitterRepository {
    override fun statusesStreamObservable(): Observable<TweetDto> =
        apiService.statuses().flatMap { mapResponseBodyToStringObervable(it.source()) }
            .map { gson.fromJson(it, TweetDto::class.java) }

    private fun mapResponseBodyToStringObervable(source: BufferedSource): Observable<String> {
        return Observable.create {
            try {
                while (!source.exhausted()) {
                    it.onNext(source.readUtf8Line())
                }
            } catch (e: IOException) {
                e.printStackTrace()
                it.onError(e)
            }
            it.onComplete()
        }
    }
}