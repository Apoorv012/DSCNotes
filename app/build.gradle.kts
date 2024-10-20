plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.apoorv.dscnotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apoorv.dscnotes"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.identity.android.legacy)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.navigation.common.ktx)
    testImplementation(libs.junit)
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    val lottieVersion = "3.4.0"
    implementation ("com.airbnb.android:lottie:$lottieVersion")

}