import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material3)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor)
}

android {
    namespace = "dev.kiyo9w.kmpboilerplate"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.kiyo9w.kmpboilerplate"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            val environment = (findProperty("app.environment") as String?) ?: "debug"
            buildConfigField("String", "APP_ENVIRONMENT", "\"$environment\"")
        }
        getByName("release") {
            isMinifyEnabled = false
            val environment = (findProperty("app.environment") as String?) ?: "prod"
            buildConfigField("String", "APP_ENVIRONMENT", "\"$environment\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}