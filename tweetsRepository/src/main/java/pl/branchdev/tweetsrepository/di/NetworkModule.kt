package pl.branchdev.tweetsrepository.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import pl.branchdev.tweetsrepository.api.ApiConfigurationData
import pl.branchdev.tweetsrepository.api.TwitterApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor

fun networkModule(apiConfiguration: ApiConfigurationData) = module {
    single { get<Retrofit>().create(TwitterApiService::class.java) }
    single { buildRetrofit(get(), get(), apiConfiguration.baseUrl) }
    single { provideOkHttpClient(get(), get()) }
    single { provideGson() }
    single { provideHttpLoggingInterceptor() }
    single { provideConsumerInterceptor(apiConfiguration) }
}

private fun provideConsumerInterceptor(apiConfigurationData: ApiConfigurationData): SigningInterceptor =
    OkHttpOAuthConsumer(
        apiConfigurationData.consumerKey,
        apiConfigurationData.consumerKeySecret
    ).apply {
        setTokenWithSecret(
            apiConfigurationData.token,
            apiConfigurationData.tokenSecret
        )
    }.run { return SigningInterceptor(this) }

private fun provideGson(): Gson {
    return GsonBuilder()
        .setLenient()
        .create()
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}

private fun buildRetrofit(gson: Gson, okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()


private fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    signInterceptor: SigningInterceptor
): OkHttpClient {
    return OkHttpClient.Builder().apply {
//        addInterceptor(httpLoggingInterceptor)
        addInterceptor(signInterceptor)
        retryOnConnectionFailure(true)
    }.run { build() }
}