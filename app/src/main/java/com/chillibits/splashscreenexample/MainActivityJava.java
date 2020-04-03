/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.splashscreenexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chillibits.splashscreen.SplashScreenBuilder;

public class MainActivityJava extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        // Setup relaunch button
        Button relaunchButton = findViewById(R.id.relaunchApp);
        relaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSplashScreen();
            }
        });

        // Start splash screen
        startSplashScreen();
    }

    private void startSplashScreen() {
        SplashScreenBuilder.getInstance(this)
            .setVideo(R.raw.splash_animation)
            .setVideoDark(R.raw.splash_animation_dark)
            .setImage(R.drawable.app_icon)
            .setTitle(R.string.app_name)
            .setSubtitle(R.string.powered_by)
            .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SplashScreenBuilder.SPLASH_SCREEN_FINISHED) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "SplashScreen finished", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "SplashScreen finished, but canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}