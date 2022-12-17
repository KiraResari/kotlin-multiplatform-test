# 17-Dec-2022

* This project is about familiarizing myself with the basics of Kotlin Multiplatform before I try to set up my main winter project for this year

* I am following the print media article "Multipaltformprojekte umsetzen mit KMM" in the "Herbst 3/2022" edition of "We Are Developers!" for this

* It says I need Android Studio for this, so let's see if that's freely available

  * Looks like it is: https://developer.android.com/studio
  * I now managed to successfully install and launch it, so that's already a good start

* The next step tells me to create a new Project from the Template "Kotlin Multiplatform App"

  * Regrettably, that is not available
  * Reading back over the prerequisites, I notice that I still have to install the KMM-Plugin in Android Studio
  * I now found and installed a plugin named "Kotlin Multiplatform Mobile", which I figure might be the right one
  * Looks good, now that I've installed that, I can select the "Kotlin Multiplatform App" project template while creating a new project
  * I note that the initial project setup is taking a long time. I hope that is just a one-time thing
    * Started: 17:57
    * Finished: 18:07
    * Duration: 10 minutes
    * I *really* hope that this is not a regular thing
  * Anyway, with that I think the project setup is complete

* The article is about creating an Android/iOS app

  * However, I actually don't have any interest in creating an iOS app at this point, nor do I have the hardware required to compile it
  * Instead, I want to create an Android/Windows app
  * The article does not tell how to create a Windows app, so I'll just follow it through for the Android parts, ignore the iOS parts, and then try to figure out how to make a Windows app out of it thereafter
  * I don't know how or if that is possible, but some reading I did in advance suggests that it *should* be possible

* I am now reading through what I'm gonna consider the preamble of the article

  * While doing so, I notice that my project setup appears to differ slightly from the one mentioned in the article
    * For one, the file `Platform.kt` in `commonMain` contains an `interface Platform` for me, while the article talks about an `expect class Platform()` instead
      * Not sure if this is going to be a problem, but we'll see
      * I note that `expect` seems to be something like `abstract`, and that apparently it needs to be actualized with the `actual` keyword in the actual implementation
        * Or not. Rather, it seems something like a static function for interfaces, which is kinda interesting if you think about it

* I am now at the first step, where I am trying to add a new function `getDeviceName()` to the `interface Platform`

  * I did that, and now it is complaining to me that a number of other classes are not implementing that method, which makes sense

  * I was easily able to add it to the Android class `AndroidPlatform`, but...

  * ...the iOS classes are another matter entirely, and if I use the Quick Fix option, it just creates a lone Kotlin file in the iOS project, which is clearly garbage

  * So I now tried removing the iOS projects (and there were three of them) entirely, but that only caused another triplicate error to appear as now it can't find the function `getPlatform` anymore either 

  * So, what else do I need to do in order to fully purge iOS from my project?

  * The error messages  all follow this pattern:

    * ````
      Expected function 'getPlatform' has no actual declaration in module Kotlin_Multiplatform_Test_App.shared.iosArm64Main for Native
      ````

    * So, where are those modules defined?

    * I was now able to find them by right-clicking on the project in the outliner, then selecting "Load/Unload Modules", and then navigating to beneath the "shared" folder where they were located

    * That fixed the issue

* After a little straightforward coding, I have now reached the point where the article tells me to start the app using the simulator integrated in Android Studio

  * I tried doing that by  clicking on the "Run Preview" icon in the DefaultPreview, but that only got me this error message:

    * ````
      com.intellij.execution.ExecutionException: Cannot find runner for DefaultPreview
      	at com.intellij.execution.runners.ExecutionEnvironmentBuilder$Companion.create(ExecutionEnvironmentBuilder.kt:48)
      [...]
      ````

  * Maybe this will help?

    * https://developer.android.com/studio/run/emulator
    * Okay, so it would seem that I already have a virtual device pre-configured, which I remember is something I checked during the installation
    * However, for some strange reason, the play button is greyed out when that device is selected, which differs from what is described and displayed in this article
    * I was now able to get it to work using `File` -> `Sync Project with Gradle files`

  * Yes, and now it works! Awesome!

* This is as far as I'm getting with this today, and a good place to stop for the day too