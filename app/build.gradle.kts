plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kapt)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion = AndroidSdk.buildToolsVersion

    defaultConfig {
        applicationId = "com.vorobyoff.weather"
        vectorDrawables.useSupportLibrary = true
        targetSdkVersion(AndroidSdk.target)
        minSdkVersion(AndroidSdk.min)
        versionName = "1.0"
        versionCode = 1
    }

    buildFeatures { viewBinding = true }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = JavaVersion.VERSION_1_8.toString() }

    tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile::class.java) {
        kotlinOptions.useIR = true
    }
}

dependencies {
    kapt(Libs.moshiCodegen)
    implementation(Libs.gms)
    implementation(Libs.moshi)
    implementation(Libs.stdlib)
    implementation(Libs.coreKtx)
    implementation(Libs.okhttp3)
    implementation(Libs.retrofit)
    implementation(Libs.material)
    implementation(Libs.appcompat)
    implementation(Libs.navigation)
    implementation(Libs.fragmentKtx)
    implementation(Libs.moshiConverter)
    implementation(Libs.navigationFragmentKtx)
}