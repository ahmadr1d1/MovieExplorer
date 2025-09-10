import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

fun getApi(propertyKey: String): String {
    val properties = Properties()
    val localProperties = rootProject.file("local.properties")
    if (localProperties.exists()) {
        properties.load(localProperties.inputStream())
    }
    return properties.getProperty(propertyKey, "")
}

android {
    namespace = "com.ahmadrd.movieexplorer.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", getApi("API_BASE_URL_TMDB"))
        buildConfigField("String", "API_KEY", getApi("TMDB_API_KEY"))
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