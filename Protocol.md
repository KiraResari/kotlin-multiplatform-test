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

  * But I think the tutorial still gets around to that

  * I tried starting the app again just before the start of the "Menus" section, and it got me this error:

    * ````
      e: E:\projects\kotlin-multiplatform-test\desktop\src\jvmMain\kotlin\Main.kt: (26, 17): Cannot find a parameter with this name: onCloseRequest
      ````

    * That happens here:

      * ````kotlin
                windowList.forEachIndexed { i, window ->
                    Window(
                        onCloseRequest = {
                            windowList.removeAt(i)
                        },
                        state = windowList[i].windowState,
                        title = windowList[i].windowName
                    )
                }
        ````

    * I cross-checked that location against the one from the final project version, but it looks just the same, and yet it doesn't work for me

    * It fails to recognize `onClodeRequest`, `state` and `title`

    * Comparing it, in the final version `Window` is defined as:

      * ````
        @Composable
        public fun Window(
            onCloseRequest: () -> Unit,
            state: WindowState,
            visible: Boolean,
            title: String,
            icon: Painter?,
            undecorated: Boolean,
            transparent: Boolean,
            resizable: Boolean,
            enabled: Boolean,
            focusable: Boolean,
            alwaysOnTop: Boolean,
            onPreviewKeyEvent: (KeyEvent) -> Boolean,
            onKeyEvent: (KeyEvent) -> Boolean,
            content: @Composable() (FrameWindowScope.() -> Unit)
        ): Unit
          Gradle: org.jetbrains.compose.ui:ui-desktop:1.0.1 (ui-desktop-1.0.1.jar)
        ````

    * ...while in my project, it is defined as:

      * ````
        @Composable
        public fun Window(
            visible: Boolean,
            onPreviewKeyEvent: (KeyEvent) -> Boolean,
            onKeyEvent: (KeyEvent) -> Boolean,
            create: () -> ComposeWindow,
            dispose: (ComposeWindow) -> Unit,
            update: (ComposeWindow) -> Unit,
            content: @Composable() (FrameWindowScope.() -> Unit)
        ): Unit
          Gradle: org.jetbrains.compose.ui:ui-desktop:1.0.1 (ui-desktop-1.0.1.jar)
        ````

    * Weird how it is different, even though the Unit appears to be the same (`ui-desktop-1.0.1.jar`) 

    * However, I do also note that in the final version of the project, I do have a little corner notification saying "**Project update recommended**: Android Gradle Project can be upgraded"

      * So maybe I did that once on my project, and that is the issue
      * I wonder where that would be defined...
        * Looks like it's in the `Dependencies.kt`
      * Looks like I did use a newer version in my project
      * I now downgraded it back from `"7.3.1"` to `"7.0.4"`
        * Sadly, that did not change the error
      * I now tried copying over the entirety of the Dependencies
        * But it still has the same problem
      * I tried copying over the entire `build.gradle.kts` from the final version
        * But that did not work either
      * Wait, let me see if it just magically goes away if I proceed with the tutorial, because I note that there's also a red _ beneath the closing ")"

    * But if that's the case, then the tutorial is really confusing at this point, because if I see a line reading "If you look at the menu bar", then of course I am going to start the app

  * And even that doesn't really work, because the next instruction the tutorial has for me is "Before the **Surface** function, add the code for a `MenuBar` as follows:"

    * ...but there *is* no "Surface" function anywhere in the class **Main.kt** in the desktop module
    * Or rather, there was, but the previous instruction "Replace the `Window` function with:..." caused me to delete it, because the code I replaced it with didn't have a body
    * ...did he intend me to only replace the header and keep the body? That would probably also explain the problems that I futilely tried to solve this last hour
    * Yes, if I put the Body back in again, everything works once more
    * Seriously, though... =>,<=

  * The next problem happens at the line "To create a **dmg** installer for the Mac, you need to run the **package** Gradle task. You can run it from Android Studio:"

    * There, I get this problem:

      * ````
        Packaging native distributions requires JDK runtime version >= 15
        Actual version: '11'
        Java home: C:\Programs\Android Studio\jre
        ````

      * True enough, the Java Version in that directory is only 11.0.13

    * Seriously! =>,<=

    * I now tried setting the Java Version to the latest in Android Studio, which happens to be 19.0.1

    * Now the build works

  * Anyway, I now managed to build and install it, and I note an issue right away

    * It appears to be 129 MB big, which is roughly 128 MB too big for a program that does basically nothing

  * However, it _does_ run as a standalone app after being installed, which I figure is at least something 

* Anyway, that's it for the Desktop app tutorial step, and the Android App is also still working, which is kinda nice, and also a bit unexpected

  * At this point, I expected more horrible complications

* But it took me all day again, which is not very encouraging

* Thus far, my experience with learning Kotlin Multiplatform can very accurately be summarized in this 6-second video clip: https://youtube.com/clip/UgkxkWekhkk-X6dYQYrNdm72Echw9UxYPZ8m 



# 22-Dec-2022

* Now continuing with this
* Last time I succeeded (with some pain) to build a desktop frontend, and to make it so that most of the same frontend components function both on desktop as well as on Android
* I was going to continue with this next chapter, but unfortunately, it looks like the rest of this tutorial-chain is behind a paywall
  * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/6-connect-to-platform-specific-api
  * That is unfortunate, because it would have covered some interesting points, such as testing, dependency injection and data concurrency
  * The cost would be 37.99€, which is roughly 32.99€ than I would be willing to pay for a tutorial that left me stressed-out and frustrated as many times as this one, so no thanks
  * Oh well, never mind then, I'll try other ways of figuring those out
* I could now try to already start with my Kotlin Multiplatform project, but I had my mind set on learning more basics in advance today, and I do have the keywords, so here's what I still want to learn in a test project before starting on a real project:
  * Testing
  * Dependency Injection
  * Data Persistence
* So, first: Testing
  * Maybe this will be a good tutorial for that?
    * https://touchlab.co/understanding-and-configuring-your-kmm-test-suite/
  * Actually, I am running into problems there again, and since the project is a whole mess by now, I think I'd rather start my own project now where I know what I'm doing and why, and will figure out the issues one by one as I come across them
* With that, I am at the end of this test project
* All in all, I'd say that I am not really convinced of Kotlin Multiplatform, but that might also be due to the nature of the tutorials, so maybe if I strike out on my own with what I learned so far, I'll fare better
  * Though naturally, I am mentally already preparing for pain and trouble
* But let's see how I fare with an actual project



# Pros & Cons

## Pros

* You can use Kotlin Compose to create frontends that will work on both Android and Desktop, including iOS Desktop
  * For iOS Mobile, you need frontend code written in Swift though (as far as I can tell)

## Cons

* Long build times
  * 10-minute waits on project sync-ups
  * 2-minute wait on daily start-up
  * Regular wait times during project builds and gradle refreshes

* I'm having considerable trouble even just with the Tutorials, which makes me not at all optimisitic about building an actual production app with this
* Setting up a project seems overly complicated
  * Regularly getting project configuration issues, such as modules not being recognized as such, and it apparently is impossible to tell the IDE that a folder is a module

* Large file sizes (129 MB for a program that barely does anything)



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