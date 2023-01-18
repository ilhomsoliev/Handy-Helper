plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.ORG_KOTLIN_ANDROID)
    id(GradlePlugin.KAPT)
}

android {
    namespace = "com.ikcollab.repository"
}

dependencies {
    implementation(project(mapOf("path" to ":data:model")))
    implementation(project(mapOf("path" to ":data:local")))

    // Hilt
    implementation(Dependencies.android.hilt.android)
    kapt(Dependencies.android.hilt.androidCompiler)
    kapt(Dependencies.android.hilt.compiler)
    implementation(Dependencies.android.hilt.navigation)
}