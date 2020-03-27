# Android SplashScreen
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f6af609b3cb4bf486942681972588e2)](https://app.codacy.com/gh/ChilliBits/splash-screen?utm_source=github.com&utm_medium=referral&utm_content=ChilliBits/splash-screen&utm_campaign=Badge_Grade_Dashboard)
[![](https://jitpack.io/v/ChilliBits/splash-screen.svg)](https://jitpack.io/#ChilliBits/splash-screen)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SplashScreen-blue.svg?style=flat)](https://android-arsenal.com/details/1/7112)
[![API](https://img.shields.io/badge/API-14%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=14)

Android library for getting a nice and simple SlashScreen into your Android app.

![Animated demo](https://chillibits.com/github-media/SplashScreen/animated_demo.gif)

## Installation

Up to now, the library is only available on JitPack. Please add this code to your build.gradle file on project level:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
To load the library into your project, use this code in the build.gradle file within the app module:
```gradle
  implementation 'com.github.ChilliBits:splash-screen:1.1.3'
```

## Usage
To use the SplashScreen, paste this code to the beginning of the `onCreate` method of the launcher activity of your app. For better performance, we recommend to do this before `setContentView()`.

```android
SplashScreenBuilder.getInstance(this)
    .setVideo(R.raw.splash_animation)
    .setVideoDark(R.raw.splash_animation_dark)
    .setImage(R.drawable.app_icon)
    .show();
```

If you want to receive a result from the SplashScreen, you can override onActivityResult as following:
### Kotlin
```android
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == SplashScreenBuilder.SPLASH_SCREEN_FINISHED) {
        if (resultCode == Activity.RESULT_OK) {
            //SplashScreen finished without manual canceling
        } else if (resultCode == Activity.RESULT_CANCELED) {
            //SplashScreen finished through manual canceling
        }
    }
}
```

If you want to have a look onto a implemented example in Kotlin, view the [MainActivity.kt](https://github.com/ChilliBits/splash-screen/blob/master/app/src/main/java/com/chillibits/splashscreenexample/MainActivity.kt) file.

### Java
```android
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SplashScreenBuilder.SPLASH_SCREEN_FINISHED) {
        if (resultCode == RESULT_OK) {
            //SplashScreen finished without manual canceling
        } else if(resultCode == RESULT_CANCELED) {
            //SplashScreen finished through manual canceling
        }
    }
}
```

If you want to have a look onto a implemented example in Java, view the [MainActivity.java](https://github.com/ChilliBits/splash-screen/blob/master/app/src/main/java/com/chillibits/splashscreenexample/MainActivityJava.java) file.

You can customize the appearance of the SplashScreen using following arguments when building the Activity with `SplashScreenBuilder`:

| Method                            | Description                                                                                                                                               |
|-----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| setVideo(int res_id)              | Sets the animation video of the SplashScreen. You have to pass this argument, otherwise the app will get an error.                                        |
| setVideoDark(int res_id)          | Set the animation video of the Splash Screen for the dark theme.                                                                                          |
| setImage(int res_id)              | Sets the image of the SplashScreen, which is displayed when the animation has ended. You have to pass this argument, otherwise the app will get an error. |
| setTextFadeInDuration(int millis) | You can call this method to set the duration of the fade in animation of the title and the subtitle.                                                      |
| setTitle(String title)            | This method replaces the name of your app, which is the default title of the SplashScreen, with a custom string.                                          |
| setSubtitle(String title)         | This method replaces the default subtitle, with a custom string.                                                                                          |
| skipImage(boolean skip)           | This opens the ability to skip the image after the video.                                                                                                 |

Thank you for using the SplashScreen!

© ChilliBits 2018-2020 (Designed and developed by Marc Auberer)