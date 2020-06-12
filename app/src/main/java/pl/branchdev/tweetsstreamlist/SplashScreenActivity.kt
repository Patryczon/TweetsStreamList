package pl.branchdev.tweetsstreamlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import pl.branchdev.TweetsActivity

class SplashScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, TweetsActivity::class.java))
    }
}