plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
    }
}

android {
    namespace = "com.tri_tail.kotlin_multiplatform_test_app"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}