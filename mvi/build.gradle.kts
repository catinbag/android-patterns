plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "org.demo.patterns.mvi"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.demo.patterns.mvi"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.arch.core.testing)
}
