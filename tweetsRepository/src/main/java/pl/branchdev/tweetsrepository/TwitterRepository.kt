package pl.branchdev.tweetsrepository

import pl.branchdev.data.TweetDto
import io.reactivex.Observable

interface TwitterRepository {
    fun statusesStreamObservable(searchQuery: String): Observable<TweetDto>
}