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



# 19-Dec-2022

* Now continuing with this
* My next goal is to get this simple hello world app running on Desktop as well

  * I am getting "Compose" as a key word from a number of places that I searched here
  * This is supposedly a sample project that features desktop support
  
    * https://github.com/Kotlin/kmm-production-sample/
  * In addition, this looks like a good place to start reading up on this:

    * https://simply-how.com/getting-started-with-compose-for-desktop
  * I now realize that Kotlin Compose is a Framework different from Kotlin Multiplatform, and while I'm not sure if they can be used in conjunction, I don't think so
  * I'll create a different project for evaluating this: `kotlin-compose-test`
  
* Okay, doing so, I think I've now got a basic understanding of how it works, and an idea of how to integrate it with Kotlin Multiplatform

  * Basically, I think that Kotlin Compose is best understood as a frontend, so if I understand it correctly, I should be able to use it as a subproject in Kotlin Multiplatform, using the same `shared` Project as the `androidApp`

  * Let's see if I can pull that off in practice

  * First off, I found a `Compose Multiplatform IDE Support` for Android Studio, which sounds like a good start, so I installed that

    * It still won't let me just add a Compose module...

  * Here's something that I'd say deals with this:

    * https://stackoverflow.com/questions/71041735/kotlin-multiplatform-compose-desktop-web-mobile

  * That links me back here:

    * https://github.com/JetBrains/compose-jb

  * And that again makes it seem like it's not possible to combine Compose with KMM

  * After a bit of back and forth, I have now landed here:

    * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/1-introduction

    * This one features this _very_ helpful paragraph, which helps clarify quite a few things:

      * > You use KMP to write code using Kotlin for multiple platforms. Kotlin Multiplatform Mobile (KMM) is a way to  use Kotlin specifically for cross-platform mobile development. When  you’re talking about developing just for mobile devices, you’re talking  about KMM — but if you’re talking about all platforms, including desktop or the web, you’re talking about KMP.

    * This is probably also good to **keep in mind**:

      * > While KMP provides Kotlin as the  programming language, it doesn’t provide a UI. If you want to create a  UI for Android, you can write it in native code or use the newer Jetpack Compose UI framework. For iOS, you can use  UIKit or the newer SwiftUI  framework using Swift. For the desktop, you can use Desktop Compose or  Java Swing. In other words, you have a choice for how you write your UI. Many see this as an advantage — the UI will always be native, so it  won’t suffer from the slow bridge communication that web-based  frameworks have.

  * Some steps into the tutorial, I am now coming across this error:

    * ````
      Caused by: org.gradle.api.internal.tasks.properties.PropertyEvaluationException: Error while evaluating property 'namespace' of task ':shared:packageDebugResources'
      ````

    * I think this might be because the tutorial instructed me to do some funky stuff with the gradle build files

    * I think that happens in the unit test project, which I don't really need right now, so I'll remove it

    * I now removed it, but the error still remains the same when I try to run it

      * Mmh, maybe it's because I have a `-` in the namespace? I seem to recall that Android for some reason did not like that

      * Nope, even with a `_` instead it still fails

      * The full error is:

        * ````
          org.gradle.api.internal.tasks.properties.PropertyEvaluationException: Error while evaluating property 'namespace' of task ':shared:packageDebugResources'
          org.gradle.api.internal.provider.AbstractProperty$PropertyQueryException: Failed to calculate the value of task ':shared:packageDebugResources' property 'namespace'.
          org.gradle.api.internal.provider.AbstractProperty$PropertyQueryException: Failed to query the value of property 'namespace'.
          com.android.builder.errors.EvalIssueException: Manifest file does not exist: E:\projects\kotlin-multiplatform-test\shared\src\androidMain\AndroidManifest.xml
          org.gradle.api.internal.tasks.properties.PropertyEvaluationException: Error while evaluating property 'namespace' of task ':shared:processDebugManifest'
          org.gradle.api.internal.provider.AbstractProperty$PropertyQueryException: Failed to calculate the value of task ':shared:processDebugManifest' property 'namespace'.
          org.gradle.api.internal.provider.AbstractProperty$PropertyQueryException: Failed to query the value of property 'namespace'.
          com.android.builder.errors.EvalIssueException: Manifest file does not exist: E:\projects\kotlin-multiplatform-test\shared\src\androidMain\AndroidManifest.xml
          org.gradle.api.internal.tasks.properties.PropertyEvaluationException: Error while evaluating property 'namespace' of task ':shared:generateDebugBuildConfig'
          org.gradle.api.internal.provider.AbstractProperty$PropertyQueryException: Failed to calculate the value of task ':shared:generateDebugBuildConfig' property 'namespace'.
          org.gradle.api.internal.provider.AbstractProperty$PropertyQueryException: Failed to query the value of property 'namespace'.
          com.android.builder.errors.EvalIssueException: Manifest file does not exist: E:\projects\kotlin-multiplatform-test\shared\src\androidMain\AndroidManifest.xml
          ````

        * This part looks like somethin worth investigating:

          * ````
            Manifest file does not exist: E:\projects\kotlin-multiplatform-test\shared\src\androidMain\AndroidManifest.xml
            ````

          * Damn right it doesn't, but why not, and why should it?

          * What's that file anyway?

          * Didn't the tutorial mention something like that earlier?

          * Not really, but maybe I can copycat what I need from the source code for this tutorial

            * https://github.com/kodecocodes/kmpf-materials

          * Nope, still not working

          * Screw it all! Let me just download the code for part 3 and take it from there =>,<=

            * Not happy about that at all though =>,<=




# Pros & Cons

## Cons

* I'm having considerable trouble even just with the Tutorials, which makes me not at all optimisitic about building an actual production app with this



# ⚓