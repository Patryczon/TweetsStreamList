package pl.branchdev.tweetsrepository.api

import TweetDto
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okio.BufferedSource
import pl.branchdev.tweetsrepository.TwitterRepository
import java.io.IOException

class ApiTwitterRepository(private val apiService: TwitterApiService, private val gson: Gson) :
    TwitterRepository {
    override fun statusesStreamObservable(): Observable<TweetDto> =
        apiService.statuses().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .flatMap { mapResponseBodyToStringObservable(it.source()) }
            .map { gson.fromJson(it, TweetDto::class.java) }


    private fun mapResponseBodyToStringObservable(source: BufferedSource): Observable<String> {
        return Observable.create {
            try {
                while (!source.exhausted()) {
                    it.onNext(source.readUtf8Line())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            it.onComplete()
        }
    }
}