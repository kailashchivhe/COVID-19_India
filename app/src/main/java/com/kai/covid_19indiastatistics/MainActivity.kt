package com.kai.covid_19indiastatistics

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kai.covid_19indiastatistics.view.DistrictFragment
import com.kai.covid_19indiastatistics.view.MainFragment

class MainActivity : AppCompatActivity() {

    private var mIntentFilter = IntentFilter()
    private val mBroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when( intent?.action ){
                OPEN_DISTRICT_FRAGMENT->{
                    setDistrictFragment( intent.extras )
                }
            }
        }
    }

    companion object{
        const val OPEN_DISTRICT_FRAGMENT = "com.kai.covid_19indiastatistics.set_district_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mIntentFilter.addAction(OPEN_DISTRICT_FRAGMENT)
        supportActionBar?.setHomeAsUpIndicator(  R.drawable.action_bar_icon )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        setMainFragment()
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(mBroadcastReceiver,mIntentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(mBroadcastReceiver)
    }

    private fun setMainFragment()
    {
        var fragment = supportFragmentManager.findFragmentByTag( "MainFragment" )
        if( fragment == null )
        {
            fragment = MainFragment()
            supportFragmentManager.beginTransaction().add( R.id.fragment_container, fragment, "MainFragment" ).commit()
        }
    }

    private fun setDistrictFragment( bundle: Bundle? )
    {
        val fragment = DistrictFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .setCustomAnimations( R.anim.slide_in_right,R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right )
            .replace( R.id.fragment_container, fragment, "DistrictFragment" )
            .addToBackStack( "DistrictFragment" )
            .commit()
    }
}
