package com.tri_tail.kotlin_multiplatform_test_app

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    override fun getDeviceName(): String = android.os.Build.MODEL
}

actual fun getPlatform(): Platform = AndroidPlatform()