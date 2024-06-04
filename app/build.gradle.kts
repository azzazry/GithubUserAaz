plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.dicoding.githubuseraaz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.githubuseraaz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField ("string", "GITHUB_API_TOKEN", "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")
            buildConfigField("string", "BASE_URL", "https://api.github.com/")
        }
        debug {
            buildConfigField("string", "GITHUB_API_TOKEN", "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")
            buildConfigField("string", "BASE_URL", "https://api.github.com")
        }
    }

    // ViewBinding dan BuildConfig make those true
    buildFeatures {
        viewBinding = true
        buildConfig = true
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

    // default dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // networking dependencies
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // viewPager2 dependencies
    implementation(libs.androidx.viewpager2)

    // viewModel dependendencies
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
}