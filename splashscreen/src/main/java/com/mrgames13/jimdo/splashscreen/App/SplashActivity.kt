package com.mrgames13.jimdo.splashscreen.App

import android.app.Activity
import android.content.res.Configuration
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.mrgames13.jimdo.splashscreen.R

class SplashActivity : AppCompatActivity() {

    // Variables as objects
    private lateinit var textureView: TextureView
    private lateinit var player: MediaPlayer
    private lateinit var app_icon: ImageView
    private lateinit var app_name: TextView
    private lateinit var powered_by: TextView
    private lateinit var handler: Handler
    private lateinit var fade_in: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize calling intent
        val i = intent

        // Initialize handler
        handler = Handler()

        // Initialize MediaPlayer
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val video_uri = Uri.parse("android.resource://" + packageName + "/" + if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) i.getIntExtra(SplashScreenBuilder.VIDEO_ID, 0) else i.getIntExtra(SplashScreenBuilder.VIDEO_ID_DARK, 0))
        player = MediaPlayer.create(this, video_uri)

        // Initialize components
        val container = findViewById<ConstraintLayout>(R.id.container)
        if (i.hasExtra(SplashScreenBuilder.SKIP_ON_TAP) && i.getBooleanExtra(SplashScreenBuilder.SKIP_ON_TAP, true)) {
            container.setOnClickListener {
                player.stop()
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

        // Initialize TextureView
        textureView = findViewById(R.id.app_icon_animation)
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                player.setSurface(Surface(surface))
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
        }
        app_icon = findViewById(R.id.app_icon)
        app_icon.setImageResource(i.getIntExtra(SplashScreenBuilder.IMAGE_ID, 0))
        app_name = findViewById(R.id.app_title)
        if (i.hasExtra(SplashScreenBuilder.TITLE) && !i.getStringExtra(SplashScreenBuilder.TITLE)!!.isEmpty()) app_name.text = i.getStringExtra(SplashScreenBuilder.TITLE)
        powered_by = findViewById(R.id.app_powered)
        if (i.hasExtra(SplashScreenBuilder.SUBTITILE) && !i.getStringExtra(SplashScreenBuilder.SUBTITILE)!!.isEmpty()) powered_by.text = i.getStringExtra(SplashScreenBuilder.SUBTITILE)

        // Initialize fade-in textureView
        fade_in = AnimationUtils.loadAnimation(this@SplashActivity, android.R.anim.fade_in)

        textureView.bringToFront()
        player.setOnPreparedListener { mediaPlayer ->
            player.seekTo(0)
            player.start()
            mediaPlayer.setOnInfoListener { _, what, extra ->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) app_icon.visibility = View.VISIBLE
                false
            }
        }
        player.setOnCompletionListener {
            textureView.visibility = View.GONE
            player.stop()
            if (i.hasExtra(SplashScreenBuilder.SKIP_IMAGE) && i.getBooleanExtra(SplashScreenBuilder.SKIP_IMAGE, false)) {
                // If image skipping was set, finish the activity
                setResult(Activity.RESULT_OK)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            } else {
                // Fade in the text slowly
                fade_in.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        handler.postDelayed({
                            setResult(Activity.RESULT_OK)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                            finish()
                        }, (if (i.hasExtra(SplashScreenBuilder.TEXT_FADE_IN_DURATION)) i.getIntExtra(SplashScreenBuilder.TEXT_FADE_IN_DURATION, 1000) else 1000).toLong())
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
                app_name.startAnimation(fade_in)
                powered_by.startAnimation(fade_in)
                app_name.visibility = View.VISIBLE
                powered_by.visibility = View.VISIBLE
            }
        }
        textureView.requestFocus()
    }
}