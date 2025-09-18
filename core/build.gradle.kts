import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

fun getLocalProp(key: String): String? {
    val p = Properties()
    val f = rootProject.file("local.properties")
    if (f.exists()) p.load(f.inputStream())
    return p.getProperty(key) ?: System.getenv(key)
}

android {
    namespace = "com.ahmadrd.movieexplorer.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")


        val baseUrl = getLocalProp("API_BASE_URL_TMDB") ?: throw GradleException("TMDB_BASE_URL missing")
        buildConfigField("String", "BASE_URL", baseUrl)

        val apiKey = getLocalProp("TMDB_API_KEY") ?: throw GradleException("TMDB_API_KEY missing")
        buildConfigField("String", "API_KEY", apiKey)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)

    // UI
    api(libs.glide)
    api(libs.recyclerview)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.lottie)

    // Networking
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.logging.interceptor)

    // Database
    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.room.compiler)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}