package com.tri_tail.kotlin_multiplatform_test_app

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun byeDevice(): String = "Bye from " + platform.getDeviceName();
}