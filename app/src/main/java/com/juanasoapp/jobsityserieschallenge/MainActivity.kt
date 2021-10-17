package com.juanasoapp.jobsityserieschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juanasoapp.jobsityserieschallenge.serieslist.SeriesListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, SeriesListFragment.newInstance()).commit()
        }
    }
}