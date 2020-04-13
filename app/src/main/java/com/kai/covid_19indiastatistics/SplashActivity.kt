package com.kai.covid_19indiastatistics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rbddevs.splashy.Splashy


class SplashActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Splashy(this)
            .setLogo(R.drawable.virus_hd)
            .setTitle( "COVID-19 INDIA" )
            .setTitleColor( R.color.white )
            .setBackgroundResource(R.color.colorPrimary)
            .setFullScreen(true)
            .setTime(2000)
            .show()

        Splashy.onComplete( object : Splashy.OnComplete {
            override fun onComplete() {
                startMainActivity()
            }
        })
    }

    fun startMainActivity()
    {
        val intent = Intent( this, MainActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}