import Versions.coil
import Versions.compose
import Versions.kotlin
import Versions.lifecycle
import Versions.navigation
import Versions.test

object Versions{
    const val minSdk = 28
    const val targetSdk = 31
    const val compileSdk = 31

    const val kotlin = "1.5.20"
    const val dagger  = "2.38"
    const val retrofit = "2.9.0"
    const val logger = "3.12.0"
    const val fragment = "1.3.6"
    const val coroutine = "1.3.5"
    const val lifecycle = "2.2.0"
    const val navigation = "2.4.1"
    const val room = "2.4.1"

    const val espresso_version = "3.4.0"
    const val junit = "4.13.2"
    const val ext = "1.1.3"
    const val arch_core = "2.1.0"
    const val coroutines_test = "1.3.6"
    const val mockito_core = "4.3.1"
    const val test = "1.4.0"
    const val compose = "1.1.1"
    const val compose_constraintlayout = "1.0.0-beta02"
    const val coil = "1.4.0"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.logger}"
    const val gson = "com.google.code.gson:gson:${Versions.retrofit}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Ui
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val core = "androidx.core:core-ktx:1.6.0"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
    const val material = "com.google.android.material:material:1.4.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val joda = "joda-time:joda-time:2.10.6"

    // Coroutine
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"

    // Lifecycle
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle"
    const val common = "androidx.lifecycle:lifecycle-common:$lifecycle"
    const val runtime = "androidx.lifecycle:lifecycle-runtime:$lifecycle"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle"

    // Navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:$navigation"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:$navigation"
    const val navigation_compose = "androidx.navigation:navigation-compose:$navigation"

    // Compose
    const val compose_foundataion = "androidx.compose.foundation:foundation:$compose"
    const val compose_material = "androidx.compose.material:material:$compose"
    const val compose_tooling = "androidx.compose.ui:ui-tooling:$compose"
    const val compose_coil = "io.coil-kt:coil-compose:$coil"

    val compose_constraintlayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.compose_constraintlayout}"
    // Room
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"

    const val testing = "androidx.room:room-testing:${Versions.room}"

    // Test
    const val espresso = "androidx.test.espresso:espresso-contrib:${Versions.espresso_version}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"

    const val junit = "junit:junit:${Versions.junit}"
    const val ext = "androidx.test.ext:junit:${Versions.ext}"

    const val arch_core = "androidx.arch.core:core-testing:${Versions.arch_core}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"

    const val rules = "androidx.test:rules:$test"
    const val runner = "androidx.test:runner:$test"
}

