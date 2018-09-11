package com.mrgames13.jimdo.splashscreen.App;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class SplashScreenBuilder {

    //Constants
    protected static final String VIDEO_ID = "VideoID";
    protected static final String IMAGE_ID = "ImageID";
    protected static final String TEXT_FADE_IN_DURATION = "TextFadeInDuration";
    protected static final String TITLE = "Title";
    protected static final String SUBTITILE = "Subtitle";
    protected static final String SKIP_ON_TAP = "SkipOnTap";

    //Variables as objects
    private Context context;
    private Intent i;

    //Variables

    private SplashScreenBuilder(Context context) {
        this.context = context;
        this.i = new Intent(context, SplashActivity.class);
        // Set default values
        this.i.putExtra(SKIP_ON_TAP, true);
    }

    public static SplashScreenBuilder getInstance(Context context) {
        return new SplashScreenBuilder(context);
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
        i.putExtra(TITLE, context.getString(res_id));
        return this;
    }

    public SplashScreenBuilder setSubtitle(@NonNull String subtitle) {
        i.putExtra(SUBTITILE, subtitle);
        return this;
    }

    public SplashScreenBuilder setSubtitle(@NonNull int res_id) {
        i.putExtra(SUBTITILE, context.getString(res_id));
        return this;
    }

    public SplashScreenBuilder enableSkipOnTap(boolean skipOnTap) {
        i.putExtra(SKIP_ON_TAP, skipOnTap);
        return this;
    }

    public void show() {
        if(!i.hasExtra(SplashScreenBuilder.VIDEO_ID) || !i.hasExtra(SplashScreenBuilder.IMAGE_ID)) throw new RuntimeException("You have to pass the video-id AND the image-id to open up the spash screen. Plase use the methods setVideo() and setImage().");
        context.startActivity(i);
    }
}
