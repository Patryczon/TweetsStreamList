package pl.branchdev.tweetsrepository

import TweetDto
import io.reactivex.Observable

interface TwitterRepository {
    fun statusesStreamObservable(): Observable<TweetDto>
}