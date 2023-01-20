plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.ORG_KOTLIN_ANDROID)
    id(GradlePlugin.KAPT)
}


android {
    namespace = "com.ikcollab.model"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}


dependencies {
    implementation(project(mapOf("path" to ":core")))

    // Room
    implementation(Dependencies.android.room.ktx)
    implementation(Dependencies.android.room.runtime)
    kapt(Dependencies.android.room.compiler)
    implementation(Dependencies.android.room.paging)
}