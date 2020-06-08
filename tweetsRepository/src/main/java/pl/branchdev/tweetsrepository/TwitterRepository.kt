package pl.branchdev.tweetsrepository

import TweetDto
import io.reactivex.rxjava3.core.Observable

interface TwitterRepository {
    fun statusesStreamObservable(): Observable<TweetDto>
}