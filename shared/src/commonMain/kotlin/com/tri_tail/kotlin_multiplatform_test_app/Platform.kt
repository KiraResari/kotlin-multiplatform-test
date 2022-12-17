package com.tri_tail.kotlin_multiplatform_test_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform