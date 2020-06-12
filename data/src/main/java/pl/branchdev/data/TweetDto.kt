package pl.branchdev.data

data class TweetDto(
    val created_at: String?,
    val id: Long?,
    val id_str: Long?,
    val text: String?,
    val display_text_range: List<Int>?,
    val source: String?,
    val truncated: Boolean?,
    val in_reply_to_status_id: Long?,
    val in_reply_to_status_id_str: Long?,
    val in_reply_to_user_id: Long?,
    val in_reply_to_user_id_str: Long?,
    val in_reply_to_screen_name: String?,
    val user: User?,
    val contributors: String?,
    val is_quote_status: Boolean?,
    val quote_count: Int?,
    val reply_count: Int?,
    val retweet_count: Int?,
    val favorite_count: Int?,
    val favorited: Boolean?,
    val retweeted: Boolean?,
    val possibly_sensitive: Boolean?,
    val filter_level: String?,
    val lang: String?,
    val timestamp_ms: Long?
)