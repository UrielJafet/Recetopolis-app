plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.recetopolis"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.recetopolis"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.9.8")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // StateFlow + lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

    // Material Icons
    implementation("androidx.compose.material:material-icons-extended")
    // ==================== HILT (Dependency Injection) ====================
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // ==================== ROOM (Local Database) ====================
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // ==================== DATASTORE (Session) ====================
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // ==================== COROUTINES ====================
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // ==================== COIL (Images) ====================
    implementation("io.coil-kt:coil-compose:2.6.0")

    // ==================== RETROFIT (para API real después) ====================
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ==================== JSON ====================
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.airbnb.android:lottie-compose:6.4.0")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}