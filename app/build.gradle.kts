plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // plugin parcelable
    id("kotlin-parcelize")
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
            buildConfigField ("String", "GITHUB_API_TOKEN", "\"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\"")
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
        debug {
            buildConfigField ("String", "GITHUB_API_TOKEN", "\"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\"")
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
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
    implementation(libs.cronet.embedded)
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

    // viewModel & liveData dependencies
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)

    // fragment dependencies
    implementation(libs.androidx.fragment.ktx)
}