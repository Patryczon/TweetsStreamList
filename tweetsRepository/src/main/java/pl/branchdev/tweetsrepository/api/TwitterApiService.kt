package pl.branchdev.tweetsrepository.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface TwitterApiService {
    @Streaming
    @POST(ApiEndpointsConfiguration.statusesStreamUrl)
    fun statuses(@Query("track") track: String): Observable<ResponseBody>
}