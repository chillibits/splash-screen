/*
 * Copyright © Marc Auberer 2021. All rights reserved
 */

package com.chillibits.splashscreenexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.splashscreen.SplashScreenBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup relaunch button
        relaunchApp.setOnClickListener { startSplashScreen() }

        // Start splash screen
        startSplashScreen()
    }

    private fun startSplashScreen() {
        SplashScreenBuilder.getInstance(this)
            .setVideo(R.raw.splash_animation)
            .setVideoDark(R.raw.splash_animation_dark)
            .setImage(R.drawable.app_icon)
            .setTitle(R.string.app_name)
            .setSubtitle(R.string.powered_by)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SplashScreenBuilder.SPLASH_SCREEN_FINISHED) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "SplashScreen finished", Toast.LENGTH_SHORT).show()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "SplashScreen finished, but canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}