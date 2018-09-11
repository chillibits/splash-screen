# Android SplashScreen
[![](https://jitpack.io/v/mrgames13/SplashScreen.svg)](https://jitpack.io/#mrgames13/SplashScreen)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SplashScreen-blue.svg?style=flat)](https://android-arsenal.com/details/1/7112)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

Android library for getting a nice and simple SlashScreen into your Android app.

![Animated demo](https://mrgames-server.de/files/github/SplashScreen/animated_demo.gif)

# Installation

Up to now, the library is only available in JitPack. Please add this code to your build.gradle file on project level:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
To load the library into your project use this code in the build.gradle file within the app module:
```gradle
  implementation 'com.github.mrgames13:SplashScreen:1.0.0'
```

# Usage
To use the SplashScreen, paste this code to the beginning of the `onCreate` method of the launcher activity of your app. For better performance, we recommend to do this before `setContentView()`.
```
SplashScreenBuilder.getInstance(this)
                .setVideo(R.raw.splash_animation)
                .setImage(R.drawable.app_icon)
                .show();
```

You can customize the appearance of the SplashScreen using following arguments when building the Activity with `SplashScreenBuilder`:

Method | Description
-------|------------
setVideo(int res_id) | Sets the animation video of the SplashScreen. You have to pass this argument, otherwise the app will get an error.
setImage(int res_id) | Sets the image of the SplashScreen, which is displayed when the animation has ended. You have to pass this argument, otherwise the app will get an error.
setTextFadeInDuration(int millis) | You can call this method to set the duration of the fade in animation of the title and the subtitle.
setTitle(String title) | This method replaces the name of your app, which is the default title of the SplashScreen, with a custom string.
setSubtitle(String title) | This method replaces the default subtitle, with a custom string.


Thank you for using the SplashScreen!

© M&R Games 2018 (Designed and developed by Marc Auberer in 2018)
