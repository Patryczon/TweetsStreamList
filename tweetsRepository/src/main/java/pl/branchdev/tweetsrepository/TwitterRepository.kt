package pl.branchdev.tweetsrepository

import TweetDto
import io.reactivex.Observable

interface TwitterRepository {
    fun statusesStreamObservable(searchQuery: String): Observable<TweetDto>
}