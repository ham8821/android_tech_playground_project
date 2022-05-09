import Versions.coil
import Versions.compose
import Versions.kotlin
import Versions.lifecycle
import Versions.navigation
import Versions.test

object Versions{
    val minSdk = 28
    val targetSdk = 31
    val compileSdk = 31

    val kotlin = "1.5.20"
    val dagger  = "2.38"
    val retrofit = "2.9.0"
    val logger = "3.12.0"
    val fragment = "1.3.6"
    val coroutine = "1.3.5"
    val lifecycle = "2.2.0"
    val navigation = "2.4.1"
    val room = "2.4.1"

    val espresso_version = "3.4.0"
    val junit = "4.13.2"
    val ext = "1.1.3"
    val arch_core = "2.1.0"
    val coroutines_test = "1.3.6"
    val mockito_core = "4.3.1"
    val test = "1.4.0"
    val compose = "1.1.1"
    val compose_constraintlayout = "1.0.0-beta02"
    val coil = "1.4.0"
}

object Deps {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin"

    // Dagger
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    // Retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.logger}"
    val gson = "com.google.code.gson:gson:${Versions.retrofit}"
    val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Ui
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    val core = "androidx.core:core-ktx:1.6.0"
    val appcompat = "androidx.appcompat:appcompat:1.3.1"
    val material = "com.google.android.material:material:1.4.0"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    val joda = "joda-time:joda-time:2.10.6"

    // Coroutine
    val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"

    // Lifecycle
    val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle"
    val common = "androidx.lifecycle:lifecycle-common:$lifecycle"
    val runtime = "androidx.lifecycle:lifecycle-runtime:$lifecycle"
    val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle"

    // Navigation
    val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:$navigation"
    val navigation_ui = "androidx.navigation:navigation-ui-ktx:$navigation"
    val navigation_compose = "androidx.navigation:navigation-compose:$navigation"

    // Compose
    val compose_foundataion = "androidx.compose.foundation:foundation:$compose"
    val compose_material = "androidx.compose.material:material:$compose"
    val compose_tooling = "androidx.compose.ui:ui-tooling:$compose"
    val compose_coil = "io.coil-kt:coil-compose:$coil"

    val compose_constraintlayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.compose_constraintlayout}"
    // Room
    val room = "androidx.room:room-ktx:${Versions.room}"
    val compiler = "androidx.room:room-compiler:${Versions.room}"

    val testing = "androidx.room:room-testing:${Versions.room}"

    // Test
    val espresso = "androidx.test.espresso:espresso-contrib:${Versions.espresso_version}"
    val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"

    val junit = "junit:junit:${Versions.junit}"
    val ext = "androidx.test.ext:junit:${Versions.ext}"

    val arch_core = "androidx.arch.core:core-testing:${Versions.arch_core}"
    val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test}"
    val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"

    val rules = "androidx.test:rules:$test"
    val runner = "androidx.test:runner:$test"
}

