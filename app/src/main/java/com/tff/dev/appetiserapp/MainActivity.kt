package com.tff.dev.appetiserapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tff.dev.appetiserapp.ituneslist.ItunesListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ItunesListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
