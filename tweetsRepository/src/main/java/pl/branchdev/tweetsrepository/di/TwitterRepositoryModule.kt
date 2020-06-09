package pl.branchdev.tweetsrepository.di

import org.koin.dsl.module
import pl.branchdev.tweetsrepository.BuildConfig
import pl.branchdev.tweetsrepository.api.ApiTwitterRepository
import pl.branchdev.tweetsrepository.mock.MockTwitterRepository

val twitterRepositoryModule = module {
    single {
        if (BuildConfig.FLAVOR.contains("mock"))
            MockTwitterRepository()
        else
            ApiTwitterRepository(get(), get())
    }
}
