plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

group = "com.github.wtw-software"

android {
    namespace = "no.wtw.android.architectureutils"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
            excludes += listOf(
                "META-INF/license.txt",
                "META-INF/notice.txt",
                "META-INF/DEPENDENCIES.txt",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/DEPENDENCIES",
                "META-INF/dependencies.txt",
                "META-INF/LGPL2.1",
                "LICENSE.txt",
                "LICENSE",
            )
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    api(libs.androidx.recyclerview)
    api(libs.androidx.lifecycle.extensions)
    api(libs.android.viewbinding)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.auth.api.phone)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.wtw-software"
            artifactId = "androidarchitectureutils"
            version = System.getenv("VERSION") ?: "SNAPSHOT"
            afterEvaluate { from(components["release"]) }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/wtw-software/AndroidArchitectureUtils")
            credentials(PasswordCredentials::class)
        }
    }
}
