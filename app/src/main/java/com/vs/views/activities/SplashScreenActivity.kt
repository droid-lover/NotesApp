package com.vs.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.vs.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

/**
 *  Created by Sachin
 */

class SplashScreenActivity : AppCompatActivity() {

    //Disposable bag
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initializeViews()
    }

    private fun initializeViews() {
        compositeDisposable.add(Observable.timer(2500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    tvAppName.visibility = View.VISIBLE
                    tvAppName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
                    startNextActivity()
                })

    }

    private fun startNextActivity() {
        compositeDisposable.add(Observable.interval(2500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
                    overridePendingTransition(R.anim.activity_in,R.anim.activity_out)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
