package pl.branchdev.tweetsrepository.api

data class ApiConfigurationData(
    val baseUrl: String,
    val consumerKey: String,
    val consumerKeySecret: String,
    val token: String,
    val tokenSecret: String
)