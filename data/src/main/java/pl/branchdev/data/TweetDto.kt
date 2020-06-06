/* 
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class TweetDto (

    val created_at : String,
    val id : Int,
    val id_str : Int,
    val text : String,
    val display_text_range : List<Int>,
    val source : String,
    val truncated : Boolean,
    val in_reply_to_status_id : Int,
    val in_reply_to_status_id_str : Int,
    val in_reply_to_user_id : Int,
    val in_reply_to_user_id_str : Int,
    val in_reply_to_screen_name : String,
    val user : User,
    val geo : String,
    val coordinates : String,
    val place : String,
    val contributors : String,
    val is_quote_status : Boolean,
    val quote_count : Int,
    val reply_count : Int,
    val retweet_count : Int,
    val favorite_count : Int,
    val favorited : Boolean,
    val retweeted : Boolean,
    val possibly_sensitive : Boolean,
    val filter_level : String,
    val lang : String,
    val timestamp_ms : Int
)