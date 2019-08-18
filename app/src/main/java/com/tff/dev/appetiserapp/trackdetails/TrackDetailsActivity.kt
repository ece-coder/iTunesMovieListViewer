package com.tff.dev.appetiserapp.trackdetails

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tff.dev.appetiserapp.R
import com.tff.dev.appetiserapp.databinding.ActivityTrackDetailsBinding
import com.tff.dev.appetiserapp.util.Helper


/**
 * Displays the details of the iTunes Movie that was clicked
 *
 */

class TrackDetailsActivity : AppCompatActivity() {

    private var trackDetailsViewModel: TrackDetailsViewModel? = null

    private lateinit var binding: ActivityTrackDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trackDetailsViewModel = TrackDetailsViewModel.create(this)
        trackDetailsViewModel!!.setData(Helper.trackDetails)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_track_details)
        binding?.let {
            it.viewmodel = trackDetailsViewModel
            it.setLifecycleOwner(this)
        }
        val actionBar = supportActionBar
        actionBar!!.title = "Movie Details"
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }
}
