package com.gaurav.testrg.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gaurav.testrg.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
        /*Handler().postDelayed(Runnable {


        }, 1000)*/
    }
}