package pl.branchdev.twitterrepository.di

import org.koin.android.BuildConfig
import org.koin.dsl.module

object TwitterRepositoryModule {
    val networkModule = module {
        single { buildRetrofit(get(), get(), "") }
        single { provideOkHttpClient(get()) }
        single { provideGson() }
        single { provideHttpLoggingInterceptor() }
    }

    private fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun buildRetrofit(gson: Gson, okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    private fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
    }
}
