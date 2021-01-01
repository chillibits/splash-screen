/*
 * Copyright © Marc Auberer 2021. All rights reserved
 */

package com.chillibits.splashscreen

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize fade in animation
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        // Set labels
        if (intent.hasExtra(SplashScreenBuilder.TITLE) && intent.getStringExtra(SplashScreenBuilder.TITLE)!!.isNotEmpty())
            appTitle.text = intent.getStringExtra(SplashScreenBuilder.TITLE)
        if (intent.hasExtra(SplashScreenBuilder.SUBTITLE) && intent.getStringExtra(SplashScreenBuilder.SUBTITLE)!!.isNotEmpty())
            appSubtitle.text = intent.getStringExtra(SplashScreenBuilder.SUBTITLE)

        // Return immediately, if we're skipping the animation
        if(intent.getBooleanExtra(SplashScreenBuilder.SKIP_VIDEO, false)) return showImage(fadeIn, intent)

        // Initialize MediaPlayer
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val videoUri = Uri.parse("android.resource://$packageName/" + if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO)
            intent.getIntExtra(SplashScreenBuilder.VIDEO_ID, 0)
        else
            intent.getIntExtra(SplashScreenBuilder.VIDEO_ID_DARK, 0))
        val player = MediaPlayer.create(this, videoUri)

        // Initialize components
        if (intent.hasExtra(SplashScreenBuilder.SKIP_ON_TAP) && intent.getBooleanExtra(SplashScreenBuilder.SKIP_ON_TAP, true)) {
            container.setOnClickListener {
                player.stop()
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

        // Initialize TextureView
        animation.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                player.setSurface(Surface(surface))
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture) = false
            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }

        animation.bringToFront()
        player.setOnPreparedListener { mediaPlayer ->
            player.seekTo(0)
            player.start()
            mediaPlayer.setOnInfoListener { _, what, _ ->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) appIcon.visibility = View.VISIBLE
                false
            }
        }
        player.setOnCompletionListener {
            animation.visibility = View.GONE
            player.stop()
            if (intent.hasExtra(SplashScreenBuilder.SKIP_IMAGE) && intent.getBooleanExtra(SplashScreenBuilder.SKIP_IMAGE, false)) {
                // If image skipping was set, finish the activity
                setResult(Activity.RESULT_OK)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            } else {
                showImage(fadeIn, intent)
            }
        }
        animation.requestFocus()
    }

    private fun showImage(fadeIn: Animation, i: Intent) {
        appIcon.setImageResource(i.getIntExtra(SplashScreenBuilder.IMAGE_ID, 0))
        appIcon.visibility = View.VISIBLE
        // Fade in the text slowly
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                GlobalScope.launch {
                    delay(i.getIntExtra(SplashScreenBuilder.TEXT_FADE_IN_DURATION, 1000).toLong())
                    setResult(Activity.RESULT_OK)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
        appTitle.startAnimation(fadeIn)
        appSubtitle.startAnimation(fadeIn)
        appTitle.visibility = View.VISIBLE
        appSubtitle.visibility = View.VISIBLE
    }
}