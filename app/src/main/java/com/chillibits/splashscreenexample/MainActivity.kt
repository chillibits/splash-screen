/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.splashscreenexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.splashscreen.SplashScreenBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup SplashScreen
        SplashScreenBuilder.getInstance(this)
            .setVideo(R.raw.splash_animation)
            .setVideoDark(R.raw.splash_animation_dark)
            .setImage(R.drawable.app_icon)
            .setTitle(R.string.app_name)
            .setSubtitle(R.string.powered_by)
            .show()

        // Setup relaunch button
        val relaunchButton = findViewById<Button>(R.id.relaunch_app)
        relaunchButton.setOnClickListener {
            // Relaunch app
            val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
            i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            overridePendingTransition(0, 0)
            finish()
        }
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