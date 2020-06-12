object ApplicationId {
    const val id = "pl.branchdev.tweetsstream"
}

object Modules {
    const val app = ":app"
    const val data = ":data"
    const val tweetsRepository = ":tweetsRepository"
    const val common = ":common"
    const val tweetsList = ":tweetsLists"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Versions {
    const val gradle = "3.5.3"
    const val compileSdk = 28
    const val minSdk = 23
    const val targetSdk = 28
    const val appcompat = "1.1.0"
    const val recyclerview = "1.0.0"
    const val kotlin = "1.3.72"
    const val rxjava = "2.2.19"
    const val retrofit = "2.9.0"
    const val koin = "2.1.5"
    const val junit = "4.12"
    const val mockitoKotlin = "2.1.0"
    const val mockitoInline = "2.24.5"
    const val constraint = "1.1.3"
    const val gson = "2.8.6"
    const val rxandroid = "2.1.1"
    const val ktx = "1.3.0"
    const val okHttp = "4.7.2"
    const val signPost = "1.1.0"
    const val signPostCore = "1.2.1.2"
    const val lifecycleExtensions = "1.1.1"
    const val reactiveNetwork = "3.0.8"
    const val rxBinding = "2.0.0"
    const val archCoreTesting = "2.1.0"
}

object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val koin = "org.koin:koin-core:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val signPost = "se.akerfeldt:okhttp-signpost:${Versions.signPost}"
    const val signPostCore = "oauth.signpost:signpost-core:${Versions.signPostCore}"
    const val lifecycleExtensions =
        "android.arch.lifecycle:extensions:${Versions.lifecycleExtensions}"
    const val reactiveNetwork =
        "com.github.pwittchen:reactivenetwork-rx2:${Versions.reactiveNetwork}"
    const val rxBinding = "com.jakewharton.rxbinding2:rxbinding:${Versions.rxBinding}"
}

object SupportLibraries {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val archCoreTesting = "android.arch.core:core-testing:${Versions.archCoreTesting}"
}