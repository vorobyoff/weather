const val kotlinVersion = "1.4.30"

object BuildPlugins {
    object Versions {
        const val buildToolVersion = "4.2.0-beta04"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val kapt = "kapt"
}

object AndroidSdk {
    const val min = 23
    const val compile = 30
    const val target = compile
    const val buildToolsVersion = "30.0.3"
}

object Libs {
    private object Versions {
        const val gms = "17.1.0"
        const val moshi = "1.11.0"
        const val coreKtx = "1.3.2"
        const val okhttp3 = "4.9.0"
        const val retrofit = "2.9.0"
        const val material = "1.3.0"
        const val appcompat = "1.2.0"
        const val navigation = "2.3.3"
        const val fragmentKtx = "1.3.0"
    }

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gms = "com.google.android.gms:play-services-location:${Versions.gms}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val okhttp3 = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
}