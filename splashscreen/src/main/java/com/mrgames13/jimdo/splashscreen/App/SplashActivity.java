package com.mrgames13.jimdo.splashscreen.App;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrgames13.jimdo.splashscreen.HelpClasses.MutedVideoView;
import com.mrgames13.jimdo.splashscreen.HelpClasses.SimpleAnimationListener;
import com.mrgames13.jimdo.splashscreen.R;

public class SplashActivity extends AppCompatActivity {

    // Constants

    // Variables as objects
    private RelativeLayout container;
    private MutedVideoView animation;
    private ImageView app_icon;
    private TextView app_name;
    private TextView powered_by;
    private Handler handler;
    private Animation fade_in;

    // Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize calling intent
        final Intent i = getIntent();

        // Initialize handler
        handler = new Handler();

        // Initialize components
        container = findViewById(R.id.container);
        if(i.hasExtra(SplashScreenBuilder.SKIP_ON_TAP) && i.getBooleanExtra(SplashScreenBuilder.SKIP_ON_TAP, true)) {
            container.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    animation.stopPlayback();
                    setResult(RESULT_CANCELED);
                    finish();
                    return true;
                }
            });
        }
        animation = findViewById(R.id.app_icon_animation);
        app_icon = findViewById(R.id.app_icon);
        app_icon.setImageResource(i.getIntExtra(SplashScreenBuilder.IMAGE_ID, 0));
        app_name = findViewById(R.id.app_title);
        if(i.hasExtra(SplashScreenBuilder.TITLE) && !i.getStringExtra(SplashScreenBuilder.TITLE).isEmpty()) app_name.setText(i.getStringExtra(SplashScreenBuilder.TITLE));
        powered_by = findViewById(R.id.app_powered);
        if(i.hasExtra(SplashScreenBuilder.SUBTITILE) && !i.getStringExtra(SplashScreenBuilder.SUBTITILE).isEmpty()) powered_by.setText(i.getStringExtra(SplashScreenBuilder.SUBTITILE));

        // Initialize fade-in animation
        fade_in = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.fade_in);

        // Initialize VideoView
        final Uri video_uri = Uri.parse("android.resource://" + getPackageName() + "/" + i.getIntExtra(SplashScreenBuilder.VIDEO_ID, 0));
        animation.setVideoURI(video_uri);
        animation.setDrawingCacheEnabled(true);
        animation.setZOrderOnTop(true);
        animation.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                animation.seekTo(0);
                animation.start();
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                        if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) app_icon.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
            }
        });
        animation.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                animation.setVisibility(View.GONE);
                animation.stopPlayback();
                // Fade in the text slowly
                fade_in.setAnimationListener(new SimpleAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setResult(RESULT_OK);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }, i.hasExtra(SplashScreenBuilder.TEXT_FADE_IN_DURATION) ? i.getIntExtra(SplashScreenBuilder.TEXT_FADE_IN_DURATION, 1000) : 1000);
                    }
                });
                app_name.startAnimation(fade_in);
                powered_by.startAnimation(fade_in);
                app_name.setVisibility(View.VISIBLE);
                powered_by.setVisibility(View.VISIBLE);

            }
        });
        animation.requestFocus();
    }
}
