package pl.branchdev.tweetsrepository.mock

import TweetDto
import io.reactivex.rxjava3.core.Observable
import pl.branchdev.tweetsrepository.TwitterRepository

class MockTwitterRepository : TwitterRepository {
    override fun statusesStreamObservable(): Observable<TweetDto> {
        TODO("Not yet implemented")
    }
}