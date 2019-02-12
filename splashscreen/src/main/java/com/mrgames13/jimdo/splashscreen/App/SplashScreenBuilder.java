package com.mrgames13.jimdo.splashscreen.App;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

public class SplashScreenBuilder {

    //Constants
    public static final int SPLASH_SCREEN_FINISHED = 10001;
    protected static final String VIDEO_ID = "VideoID";
    protected static final String IMAGE_ID = "ImageID";
    protected static final String TEXT_FADE_IN_DURATION = "TextFadeInDuration";
    protected static final String TITLE = "Title";
    protected static final String SUBTITILE = "Subtitle";
    protected static final String SKIP_ON_TAP = "SkipOnTap";
    protected static final String SKIP_IMAGE = "SkipImage";

    //Variables as objects
    private Activity activity;
    private Intent i;

    //Variables

    private SplashScreenBuilder(Activity activity) {
        this.activity = activity;
        this.i = new Intent(activity, SplashActivity.class);
        // Set default values
        this.i.putExtra(SKIP_ON_TAP, true);
    }

    public static SplashScreenBuilder getInstance(Activity activity) {
        return new SplashScreenBuilder(activity);
    }

    public SplashScreenBuilder setVideo(@NonNull int res_id) {
        i.putExtra(VIDEO_ID, res_id);
        return this;
    }

    public SplashScreenBuilder setImage(@NonNull int res_id) {
        i.putExtra(IMAGE_ID, res_id);
        return this;
    }

    public SplashScreenBuilder setTextFadeInDuration(@NonNull int millis) {
        i.putExtra(TEXT_FADE_IN_DURATION, millis);
        return this;
    }

    public SplashScreenBuilder setTitle(@NonNull String title) {
        i.putExtra(TITLE, title);
        return this;
    }

    public SplashScreenBuilder setTitle(@NonNull int res_id) {
        i.putExtra(TITLE, activity.getString(res_id));
        return this;
    }

    public SplashScreenBuilder setSubtitle(@NonNull String subtitle) {
        i.putExtra(SUBTITILE, subtitle);
        return this;
    }

    public SplashScreenBuilder setSubtitle(@NonNull int res_id) {
        i.putExtra(SUBTITILE, activity.getString(res_id));
        return this;
    }

    public SplashScreenBuilder enableSkipOnTap(boolean skipOnTap) {
        i.putExtra(SKIP_ON_TAP, skipOnTap);
        return this;
    }

    public SplashScreenBuilder skipImage(boolean skipImage) {
        i.putExtra(SKIP_IMAGE, skipImage);
        return this;
    }

    public void show() {
        if(!i.hasExtra(SplashScreenBuilder.VIDEO_ID) || !i.hasExtra(SplashScreenBuilder.IMAGE_ID)) throw new RuntimeException("You have to pass the video-id AND the image-id to open up the spash screen. Plase use the methods setVideo() and setImage().");
        activity.startActivityForResult(i, SPLASH_SCREEN_FINISHED);
    }
}
