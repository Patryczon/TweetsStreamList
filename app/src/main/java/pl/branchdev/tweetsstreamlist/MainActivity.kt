package pl.branchdev.tweetsstreamlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject
import pl.branchdev.tweetsrepository.TwitterRepository

class MainActivity : AppCompatActivity() {

    val repository: TwitterRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository.statusesStreamObservable().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.i("tweet", it.id.toString())
            }, {
                Log.e("error", it.message)
            }, {})
    }
}
