/*
 * Copyright © Marc Auberer 2021. All rights reserved
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

    /*
    * Sets a video resource file to be placed in the middle of the splash screen.
    *
    * @param resId The resource id of the video
    * @return The resulting builder object
    * */
    fun setVideo(resId: Int): SplashScreenBuilder {
        i.putExtra(VIDEO_ID, resId)
        return this
    }

    /*
    * Sets a video resource file to be placed in the middle of the splash screen.
    * This is the video for the case, that the splash screen gets displayed in dark mode.
    *
    * @param resId The resource id of the video
    * @return The resulting builder object
    * */
    fun setVideoDark(resId: Int): SplashScreenBuilder {
        i.putExtra(VIDEO_ID_DARK, resId)
        return this
    }

    /*
    * Sets a image resource file to be placed in the middle of the splash screen after playing the video.
    *
    * @param resId The resource id of the image
    * @return The resulting builder object
    * */
    fun setImage(resId: Int): SplashScreenBuilder {
        i.putExtra(IMAGE_ID, resId)
        return this
    }

    /*
    * Sets a duration, how long the text should fade in after playing the video.
    *
    * @param millis Time of animation in milliseconds
    * @return The resulting builder object
    * */
    fun setTextFadeInDuration(millis: Int): SplashScreenBuilder {
        i.putExtra(TEXT_FADE_IN_DURATION, millis)
        return this
    }

    /*
    * Sets the title, which should be displayed after playing the video.
    *
    * @param resId The title in form of a string
    * @return The resulting builder object
    * */
    fun setTitle(title: String): SplashScreenBuilder {
        i.putExtra(TITLE, title)
        return this
    }

    /*
    * Sets the title, which should be displayed after playing the video.
    *
    * @param resId The resource id of the title string resource
    * @return The resulting builder object
    * */
    fun setTitle(resId: Int): SplashScreenBuilder {
        i.putExtra(TITLE, activity.getString(resId))
        return this
    }

    /*
    * Sets the subtitle, which should be displayed after playing the video.
    *
    * @param resId The subtitle in form of a string
    * @return The resulting builder object
    * */
    fun setSubtitle(subtitle: String): SplashScreenBuilder {
        i.putExtra(SUBTITLE, subtitle)
        return this
    }

    /*
    * Sets the subtitle, which should be displayed after playing the video.
    *
    * @param resId The resource id of the subtitle string resource
    * @return The resulting builder object
    * */
    fun setSubtitle(resId: Int): SplashScreenBuilder {
        i.putExtra(SUBTITLE, activity.getString(resId))
        return this
    }

    /*
    * Enables / disables the cancellation of the splash screen when the screen is touched during
    * the animation
    *
    * @param skipOnTap Enabled or not
    * @return The resulting builder object
    * */
    fun enableSkipOnTap(skipOnTap: Boolean): SplashScreenBuilder {
        i.putExtra(SKIP_ON_TAP, skipOnTap)
        return this
    }

    /*
    * Enables / disables the image. So, if we set this to true, the splash screen only plays
    * the video and then dismisses immediately
    *
    * @param skipImage Enabled or not
    * @return The resulting builder object
    * */
    fun skipImage(skipImage: Boolean): SplashScreenBuilder {
        i.putExtra(SKIP_IMAGE, skipImage)
        return this
    }

    /*
    * Enables / disables the animation. So, if we set this to true, the splash screen only shows
    * the splash image and then dismisses immediately
    *
    * @param skipVideo Enabled or not
    * @return The resulting builder object
    * */
    fun skipVideo(skipVideo: Boolean): SplashScreenBuilder {
        i.putExtra(SKIP_VIDEO, skipVideo)
        return this
    }

    /*
    * Shows the final splash screen.
    *
    * @throws RuntimeException
    * */
    fun show() {
        if (!i.hasExtra(VIDEO_ID) || !i.hasExtra(IMAGE_ID)) throw IncompleteConfigurationException("You have to pass the video-id AND the image-id to open up the splash screen. Please use the methods setVideo() and setImage().")
        if (!i.hasExtra(VIDEO_ID_DARK)) i.putExtra(VIDEO_ID_DARK, i.getIntExtra(VIDEO_ID, 0))
        activity.startActivityForResult(i, SPLASH_SCREEN_FINISHED)
    }

    companion object {
        // Constants
        const val SPLASH_SCREEN_FINISHED = 10001
        internal const val VIDEO_ID = "VideoID"
        internal const val VIDEO_ID_DARK = "VideoIDDark"
        internal const val IMAGE_ID = "ImageID"
        internal const val TEXT_FADE_IN_DURATION = "TextFadeInDuration"
        internal const val TITLE = "Title"
        internal const val SUBTITLE = "Subtitle"
        internal const val SKIP_ON_TAP = "SkipOnTap"
        internal const val SKIP_IMAGE = "SkipImage"
        internal const val SKIP_VIDEO = "SkipAnimation"

        @JvmStatic
        fun getInstance(activity: Activity) = SplashScreenBuilder(activity)
    }
}
