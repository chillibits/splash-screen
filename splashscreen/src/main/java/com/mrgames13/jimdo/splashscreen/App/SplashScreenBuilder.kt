package com.mrgames13.jimdo.splashscreen.App

import android.app.Activity
import android.content.Intent

class SplashScreenBuilder private constructor(
    // Variables as objects
    private val activity: Activity) {
    private val i: Intent

    init {
        this.i = Intent(activity, SplashActivity::class.java)
        // Set default values
        this.i.putExtra(SKIP_ON_TAP, true)
    }

    fun setVideo(res_id: Int): SplashScreenBuilder {
        i.putExtra(VIDEO_ID, res_id)
        return this
    }

    fun setVideoDark(res_id: Int): SplashScreenBuilder {
        i.putExtra(VIDEO_ID_DARK, res_id)
        return this
    }

    fun setImage(res_id: Int): SplashScreenBuilder {
        i.putExtra(IMAGE_ID, res_id)
        return this
    }

    fun setTextFadeInDuration(millis: Int): SplashScreenBuilder {
        i.putExtra(TEXT_FADE_IN_DURATION, millis)
        return this
    }

    fun setTitle(title: String): SplashScreenBuilder {
        i.putExtra(TITLE, title)
        return this
    }

    fun setTitle(res_id: Int): SplashScreenBuilder {
        i.putExtra(TITLE, activity.getString(res_id))
        return this
    }

    fun setSubtitle(subtitle: String): SplashScreenBuilder {
        i.putExtra(SUBTITILE, subtitle)
        return this
    }

    fun setSubtitle(res_id: Int): SplashScreenBuilder {
        i.putExtra(SUBTITILE, activity.getString(res_id))
        return this
    }

    fun enableSkipOnTap(skipOnTap: Boolean): SplashScreenBuilder {
        i.putExtra(SKIP_ON_TAP, skipOnTap)
        return this
    }

    fun skipImage(skipImage: Boolean): SplashScreenBuilder {
        i.putExtra(SKIP_IMAGE, skipImage)
        return this
    }

    fun show() {
        if (!i.hasExtra(VIDEO_ID) || !i.hasExtra(IMAGE_ID)) throw RuntimeException("You have to pass the video-id AND the image-id to open up the spash screen. Plase use the methods setVideo() and setImage().")
        if (!i.hasExtra(VIDEO_ID_DARK)) i.putExtra(VIDEO_ID_DARK, i.getIntExtra(VIDEO_ID, 0))
        activity.startActivityForResult(i, SPLASH_SCREEN_FINISHED)
    }

    companion object {
        // Constants
        val SPLASH_SCREEN_FINISHED = 10001
        internal val VIDEO_ID = "VideoID"
        internal val VIDEO_ID_DARK = "VideoIDDark"
        internal val IMAGE_ID = "ImageID"
        internal val TEXT_FADE_IN_DURATION = "TextFadeInDuration"
        internal val TITLE = "Title"
        internal val SUBTITILE = "Subtitle"
        internal val SKIP_ON_TAP = "SkipOnTap"
        internal val SKIP_IMAGE = "SkipImage"

        fun getInstance(activity: Activity): SplashScreenBuilder {
            return SplashScreenBuilder(activity)
        }
    }
}
