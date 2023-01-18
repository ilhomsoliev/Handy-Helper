
plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.ORG_KOTLIN_ANDROID)
    id(GradlePlugin.KAPT)
}

android {
    namespace = "com.ikcollab.local"
}

dependencies {
    implementation(project(mapOf("path" to ":data:model")))

    // Room
    implementation(Dependencies.android.room.ktx)
    implementation(Dependencies.android.room.runtime)
    kapt(Dependencies.android.room.compiler)
    implementation(Dependencies.android.room.paging)

    // Hilt
    implementation(Dependencies.android.hilt.android)
    kapt(Dependencies.android.hilt.androidCompiler)
    kapt(Dependencies.android.hilt.compiler)
    implementation(Dependencies.android.hilt.navigation)
}