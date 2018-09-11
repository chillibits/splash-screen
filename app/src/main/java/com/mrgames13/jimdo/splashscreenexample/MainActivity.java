package com.mrgames13.jimdo.splashscreenexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mrgames13.jimdo.splashscreen.App.SplashScreenBuilder;

public class MainActivity extends AppCompatActivity {

    //Constants

    //Variables as Objects

    //Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SplashScreenBuilder.getInstance(this)
                .setVideo(R.raw.splash_animation)
                .setImage(R.drawable.app_icon)
                .enableSkipOnTap(false)
                .show();

        Button restart_app = findViewById(R.id.restart_app);
        restart_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Restart app
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}
