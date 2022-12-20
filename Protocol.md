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

* Now I'll want to remove the iOS files all over again

  * Also, waiting another ten minutes or so for the project to sync up =>,<=
  * I tried removing the iOS modules, which caused Android Studio to crash with an error
  * Now I re-opened it, but it looks weird, different than before
    * Different bad, probably, because now I can't see the build files anymore
    * The really strange thing is that when I check in git, nothing was changed in the file system
      * Hm, it's probably some of the ignored files that were changed then

  * Will deleting the `.idea` Folder give me a fresh start?
  * Ah, okay, no, I just needed to switch the view of the outliner from "Android" to "Project" 
  * Okay, so Now I'm trying to continue with this step of the tutorial, after several hours of floundering around with useless comfiguration issues
    * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/3-developing-ui-android-jetpack-compose

  * Now I have trouble running this on the Android emulator 
    * If I click the "run" button on the AndroidApp, it just builds for a second now, but does not launch on the emulator
    * Before, It was at least saying "waiting for target device to come online", but then it stopped doing that too
    * Meanwhile, the emulator is just a picture of an Android Smahon with a black screen
    * I now closed the emulator and tried it again, and now it says "Waiting for all target devices to come online" again
      * I wish I had some sort of indication about what's happening in the background here

    * I now did something else for 15 minutes while waiting for the emulator to start, but nothing happened, I'm still only getting a black screen
    * And now I'm back at the state where the build finishes in 1 second, and nothing happens
  
  * I also tried switching back to the version that I had yesterday and worked, but it didn't
  
    * That seems to indicate that the problem is not about anything project-related, but rather the emulator
    * Okay, so I tried Duplicating the Android Virtual Device and deleting the old one, but it won't let me do that saying that the device is still running, even though I had stopped it
      * I now quit and then re-started Android Studio, and then it let me delete it 
    * After that, I did run it using the new virtual device, and there it did work
  
  * Right, now back to what I was actually trying to do, namely following the tutorial 
  
    * I now got to `Build and run the app on a device or emulator. Here’s what you’ll see:`, but despite having followed all the steps to get there, it doesn't work
  
    * Instead, it doesn't find classes that are clearly there:
  
      * ````
        Unresolved reference: MainView
        ````
  
    * I think the example has the imports wrong or something, because `import com.raywenderlich.findtime.android.theme.AppTheme` doesn't work, but just `import AppTheme` works
  
    * Aannd, there's also typos in the tutorial
  
    * Anyway, I now got it to work, and what I see looks reasonably good
  
  * And now I managed to make it to the end of this part of the tutorial without insurmountable problems
  
* I think this is as far as I'm getting with this today

* It's a bit frustrating. At the start of the day I was under the impression of having the Android App figured out and the goal of figuring out the desktop app for that today, and now, by the end of the day, all I managed to get done is copy-paste another android app from a tutorial, with no progress on the desktop app whatsoever

  * Oh well, at least this more complex android app features a bunch of interesting functions and techniques, and since chapter 5 of this tutorial is specifically about building a desktop app, I hope that I will get around to that tomorrow




# 20-Dec-2022

* Now continuing with this

* Last turn, I managed to follow another tutorial through for creating a more complex Android app

* Today, I'm following that same tutorial for creating a desktop version of that same app, which relies on the same resources

  * Here's the link for that:

    * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/5-developing-ui-compose-multiplatform

  * I note that while starting up the IDE with the project takes about 2 minutes, which is 8 minutes less than the initial load time, but still roughly 90 seconds more than what I feel an IDE should require to start up in this day and age

  * Anyway, I am already running into slight trouble again, namely at the point where the Tutorial tells me "right-click on the **kotlin** folder and chose **New ▸ Kotlin Class/File**. Type **Main** and choose file."

    * However, the option `New ▸ Kotlin Class/File` doesn't come up

    * I also note that the "desktop" folder lacks the little square that is denoting what I believe Android Studio recognizes as modules

    * I tried opening the `Project Structure > Modules`, but that only says "Nothing to Show"

    * Meanwhile, if I go to "Load Modules", it does display "Desktop" as a loaded module, and with the little square that is notably still missing in the outliner

    * I tried `Build > Make Project`, but that didn't change anything either

    * Seriously, WTF =>,<=

    * I also note that the file name of the `desktop/build.gradle.kts` is in red, but the IDE does not tell me what this is supposed to signify, and the project doesn't have any errors either

    * However, when I tried building that file directly now I got _all sorts_ of errors:

      * ````
        build.gradle.kts:1:12: error: unresolved reference: jetbrains
        import org.jetbrains.compose.compose
                   ^
        build.gradle.kts:2:12: error: unresolved reference: jetbrains
        import org.jetbrains.compose.desktop.application.dsl.TargetFormat
                   ^
        build.gradle.kts:4:1: error: unresolved reference: plugins
        plugins {
        ^
        build.gradle.kts:5:5: error: expression 'kotlin' cannot be invoked as a function. The function 'invoke()' is not found
            kotlin(multiplatform)
            ^
        build.gradle.kts:5:5: error: unresolved reference. None of the following candidates is applicable because of receiver type mismatch: 
        public val <T : Any> Class<TypeVariable(T)>.kotlin: KClass<TypeVariable(T)> defined in kotlin.jvm
            kotlin(multiplatform)
            ^
        build.gradle.kts:5:12: error: unresolved reference: multiplatform
            kotlin(multiplatform)
                   ^
        build.gradle.kts:6:5: error: unresolved reference: id
            id(composePlugin) version Versions.desktop_compose_plugin
            ^
        build.gradle.kts:6:8: error: unresolved reference: composePlugin
            id(composePlugin) version Versions.desktop_compose_plugin
               ^
        build.gradle.kts:6:31: error: unresolved reference: Versions
            id(composePlugin) version Versions.desktop_compose_plugin
                                      ^
        build.gradle.kts:9:1: error: unresolved reference: group
        group = "com.raywenderlich.findtime"
        ^
        build.gradle.kts:10:1: error: unresolved reference: version
        version = "1.0.0"
        ^
        build.gradle.kts:12:1: error: expression 'kotlin' cannot be invoked as a function. The function 'invoke()' is not found
        kotlin {
        ^
        build.gradle.kts:12:1: error: unresolved reference. None of the following candidates is applicable because of receiver type mismatch: 
        public val <T : Any> Class<TypeVariable(T)>.kotlin: KClass<TypeVariable(T)> defined in kotlin.jvm
        kotlin {
        ^
        build.gradle.kts:13:5: error: unresolved reference: jvm
            jvm {
            ^
        build.gradle.kts:14:9: error: unresolved reference: withJava
                withJava()
                ^
        build.gradle.kts:15:9: error: unresolved reference: compilations
                compilations.all {
                ^
        build.gradle.kts:16:13: error: unresolved reference: kotlinOptions
                    kotlinOptions.jvmTarget = "11"
                    ^
        build.gradle.kts:16:27: error: variable expected
                    kotlinOptions.jvmTarget = "11"
                                  ^
        build.gradle.kts:19:5: error: unresolved reference: sourceSets
            sourceSets {
            ^
        build.gradle.kts:20:24: error: unresolved reference: getting
                val jvmMain by getting {
                               ^
        build.gradle.kts:21:20: error: unresolved reference: srcDirs
                    kotlin.srcDirs("src/jvmMain/kotlin")
                           ^
        build.gradle.kts:22:13: error: unresolved reference: dependencies
                    dependencies {
                    ^
        build.gradle.kts:23:17: error: unresolved reference: implementation
                        implementation(compose.desktop.currentOs)
                        ^
        build.gradle.kts:23:32: error: unresolved reference: compose
                        implementation(compose.desktop.currentOs)
                                       ^
        build.gradle.kts:24:17: error: unresolved reference: api
                        api(compose.runtime)
                        ^
        build.gradle.kts:24:21: error: unresolved reference: compose
                        api(compose.runtime)
                            ^
        build.gradle.kts:25:17: error: unresolved reference: api
                        api(compose.foundation)
                        ^
        build.gradle.kts:25:21: error: unresolved reference: compose
                        api(compose.foundation)
                            ^
        build.gradle.kts:26:17: error: unresolved reference: api
                        api(compose.material)
                        ^
        build.gradle.kts:26:21: error: unresolved reference: compose
                        api(compose.material)
                            ^
        build.gradle.kts:27:17: error: unresolved reference: api
                        api(compose.ui)
                        ^
        build.gradle.kts:27:21: error: unresolved reference: compose
                        api(compose.ui)
                            ^
        build.gradle.kts:28:17: error: unresolved reference: api
                        api(compose.materialIconsExtended)
                        ^
        build.gradle.kts:28:21: error: unresolved reference: compose
                        api(compose.materialIconsExtended)
                            ^
        build.gradle.kts:30:17: error: unresolved reference: implementation
                        implementation(Deps.napier)
                        ^
        build.gradle.kts:30:32: error: unresolved reference: Deps
                        implementation(Deps.napier)
                                       ^
        build.gradle.kts:31:17: error: unresolved reference: implementation
                        implementation(Deps.Coroutines.common)
                        ^
        build.gradle.kts:31:32: error: unresolved reference: Deps
                        implementation(Deps.Coroutines.common)
                                       ^
        build.gradle.kts:33:17: error: unresolved reference: implementation
                        implementation(project(":shared"))
                        ^
        build.gradle.kts:33:32: error: unresolved reference: project
                        implementation(project(":shared"))
                                       ^
        build.gradle.kts:41:1: error: unresolved reference: compose
        compose.desktop {
        ^
        build.gradle.kts:42:5: error: unresolved reference: application
            application {
            ^
        build.gradle.kts:43:9: error: unresolved reference: mainClass
                mainClass = "MainKt"
                ^
        build.gradle.kts:44:9: error: unresolved reference: nativeDistributions
                nativeDistributions {
                ^
        build.gradle.kts:45:13: error: unresolved reference: targetFormats
                    targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                    ^
        build.gradle.kts:45:27: error: unresolved reference: TargetFormat
                    targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                                  ^
        build.gradle.kts:45:45: error: unresolved reference: TargetFormat
                    targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                                                    ^
        build.gradle.kts:45:63: error: unresolved reference: TargetFormat
                    targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                                                                      ^
        build.gradle.kts:46:13: error: unresolved reference: packageName
                    packageName = "FindTime"
                    ^
        build.gradle.kts:47:13: error: unresolved reference: macOS
                    macOS {
                    ^
        build.gradle.kts:48:17: error: unresolved reference: bundleID
                        bundleID = "com.raywenderlich.findtime"
                        ^
        ````

    * Okay, what in the name of Dragon is wrong with that file?

    * Let me check the final file for that in the sample repository...

    * I now copied over the code from the final file in the sample repository, but it still doesn't work...

    * Maybe running these files directly is simply not intended

  * Anyway, I feel I have little choice but to continue and hope it somehow works out

  * So, we were stuck on the part where I couldn't add the file as a kotlin file because the IDE does not give me that option because "desktop" is not recognized as a module

    * So I guess I'll just add it as a normal file and see when I run into trouble

  * Okay, so I got until "Run the desktop app:"

    * Not that I expected it to work, because the project still has many errors, but the error that actually happened now was another completely unexpected one:

      * ````
        Could not set process working directory to 'E:\projects\kotlin-multiplatform-test\desktop': could not set current directory (errno 2)
        ````

    * After trying a while to figure it out, I finally spotted the cause for this, and most likely also the previous error:

      * The root level folder that was supposed to be named "desktop" had a typo in it, and was named "dektop" instead
      * Fixing that also allowed it to be recognized as a module

    * After that, the app unexpectedly started up as explained in the tutorial, despite the project still having a number of problems

      * Or more specifically, it has one problem repeated a number of times:

        * ````
          Unresolved reference: onDismissType
          ````

      *  I figured out that this was because the `Types.kt` in my project was missing the package declaration `package com.raywenderlich.compose.ui`

  * Now moving on with the tutorial

  * Further problems ensued which were not addressed in the tutorial:

    * ````
      e: E:\projects\kotlin-multiplatform-test\shared-ui\src\commonMain\kotlin\com\raywenderlich\compose\ui\AddTimezoneDialog.kt: (26, 12): Expected function 'AddTimeDialogWrapper' has no actual declaration in module <shared-ui> for JVM
      ````

    * Great, now it complains:

      * ````
        Expected function 'AddTimeDialogWrapper' has no actual declaration in module <shared-ui> for JVM
        Actual function 'AddTimeDialogWrapper' has no corresponding expected declaration
        ````

    * Could that be related to that earlier thing where it didn't allow me to add the file as a kotlin file?

    * Yup, looks like it

    * By now I'm starting to figure out what the difference is between `Add File` `Add Kotlin File/Class > File` and `Add Kotlin File/Class > Class`

      * `Add File` simply adds an empty file
      * `Add Kotlin File/Class > File` adds a package declaration at the top
      * `Add Kotlin File/Class > Class` additionally also adds an empty class

    * And because I had to add it using `Add File`, the declaration `package com.raywenderlich.compose.ui` was missing at the top

    * I added it, and now it works

  * Now the app generally works, even if some of the window sizes are still wrong



# Pros & Cons

## Cons

* 10-minute waits on project sync-ups
  * 2-minute wait on daily start-up

* I'm having considerable trouble even just with the Tutorials, which makes me not at all optimisitic about building an actual production app with this
* Setting up a project seems overly complicated
  * Regularly getting project configuration issues, such as modules not being recognized as such, and it apparently is impossible to tell the IDE that a folder is a module




# Knowledgebase

## Bugfixes

### Module is not recognized

**Symptoms**

* A folder that is supposed to be a module is not recognized as such

**Cause**

* Possibly a typo
* For a folder to be recognized as a module, the `settings.gradle.kts` has to have an `include` statement for that folder
* If either the module folder or the include statement  have a typo, then the module will not be recognized
* Example:
  * Folder: `desktop`
  * Include statement: `include(":dektop")`⚡

**Fix**

* Make sure the module folder name and the module import statement are the same and have no typos



# ⚓