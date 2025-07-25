plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.51.1"
}

android {
    namespace = "com.health.diafit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.health.diafit"
        minSdk = 21
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
//    testOptions {
//        unitTests.isReturnDefaultValues = true
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation (libs.coil)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    implementation(libs.symbol.processing.api)
    ksp(libs.symbol.processing)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.lottie)

    // Mockito for mocking
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    // Mockito-Kotlin for more concise syntax
    testImplementation(libs.mockito.kotlin)

    // AndroidX Testing
    testImplementation(libs.androidx.core.testing)

    // Coroutines Test
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.androidx.viewpager2)
    implementation(libs.dotsindicator)

    implementation(libs.youtube.player.core)
}
