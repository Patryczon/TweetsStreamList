object ApplicationId {
    val id = "pl.branchdev.tweetsstream"
}

object Modules {
    val app = ":app"
    val data = ":data"
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val gradle = "3.5.3"
    val compileSdk = 28
    val minSdk = 23
    val targetSdk = 28
    val appcompat = "1.1.0"
    val recyclerview = "1.0.0"
    val kotlin = "1.3.50"
    val rxjava = "3.0.4"
    val rxkotlin = "2.3.0"
    val retrofit = "2.9.0"
    val loggingInterceptor = "3.12.1"
    val koin = "2.1.5"
    val junit = "4.12"
    val mockitoKotlin = "2.1.0"
    val mockitoInline = "2.24.5"
    val constraint = "1.1.3"
    val gson = "2.8.6"
    val rxandroid = "3.0.0"
    val ktx = "1.3.0"
    val okHttp = "4.7.2"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val rxandroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxandroid}"
    val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"
    val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val koinScope = "org.koin:koin-androidx-scope${Versions.koin}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

}

object SupportLibraries {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
}

object TestLibraries {
    val junit = "junit:junit:${Versions.junit}"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
}