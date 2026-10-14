plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "no.wtw.android.architectureutils.example"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "no.wtw.android.architectureutils.example"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":library"))
    implementation(libs.androidx.appcompat)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
