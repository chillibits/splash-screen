/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.splashscreen

import android.app.Activity
import android.content.Intent

class SplashScreenBuilder private constructor(
    // Variables as objects
    private val activity: Activity) {
    private val i: Intent = Intent(activity, SplashActivity::class.java)

    init {
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
        i.putExtra(SUBTITLE, subtitle)
        return this
    }

    fun setSubtitle(res_id: Int): SplashScreenBuilder {
        i.putExtra(SUBTITLE, activity.getString(res_id))
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
        internal const val VIDEO_ID = "VideoID"
        internal const val VIDEO_ID_DARK = "VideoIDDark"
        internal const val IMAGE_ID = "ImageID"
        internal const val TEXT_FADE_IN_DURATION = "TextFadeInDuration"
        internal const val TITLE = "Title"
        internal const val SUBTITLE = "Subtitle"
        internal const val SKIP_ON_TAP = "SkipOnTap"
        internal const val SKIP_IMAGE = "SkipImage"

        fun getInstance(activity: Activity): SplashScreenBuilder {
            return SplashScreenBuilder(activity)
        }
    }
}
