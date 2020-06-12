package pl.branchdev.tweetsrepository.mock

import pl.branchdev.data.TweetDto
import io.reactivex.Observable
import pl.branchdev.tweetsrepository.TwitterRepository

class MockTwitterRepository : TwitterRepository {
    override fun statusesStreamObservable(searchQuery: String): Observable<TweetDto> {
        val tweetDto = TweetDto(
            null,
            1,
            null,
            "1",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null, null
        )
        return Observable.just(
            tweetDto
        )
    }
}