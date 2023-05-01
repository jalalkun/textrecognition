plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("io.realm.kotlin")
}

android {
    namespace = "com.jalalkun.textrecognition"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.jalalkun.textrecognition"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = false
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
    flavorDimensions += "version"
    productFlavors {
        create("red") {
            dimension = "version"
            applicationIdSuffix = ".red"
            versionNameSuffix = "-red"
            buildConfigField("String", "VERSION", "\"RED\"")
            buildConfigField("String", "PICK_SOURCE", "\"ALL\"")
        }
        create("green") {
            dimension = "version"
            applicationIdSuffix = ".green"
            versionNameSuffix = "-green"
            buildConfigField("String", "VERSION", "\"GREEN\"")
            buildConfigField("String", "PICK_SOURCE", "\"ALL\"")
        }
        create("all"){
            dimension = "version"
            applicationIdSuffix = ".all"
            versionNameSuffix = "-all"
            buildConfigField("String", "PICK_SOURCE", "\"ALL\"")
            buildConfigField("String", "VERSION", "\"ELSE\"")
        }
        create("storage"){
            dimension = "version"
            applicationIdSuffix = ".storage"
            versionNameSuffix = "-storage"
            buildConfigField("String", "PICK_SOURCE", "\"STORAGE\"")
            buildConfigField("String", "VERSION", "\"ELSE\"")
        }
        create("camera"){
            dimension = "version"
            applicationIdSuffix = ".camera"
            versionNameSuffix = "-camera"
            buildConfigField("String", "PICK_SOURCE", "\"CAMERA\"")
            buildConfigField("String", "VERSION", "\"ELSE\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":nav"))
    implementation(project(":CaptureCamera"))
    implementation(project(":MyDb"))
    implementation(project(":helper"))
    implementation(project(":mymodel"))
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation(platform("androidx.compose:compose-bom:2023.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.camera:camera-core:1.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.insert-koin:koin-core:3.4.0")
    implementation("io.insert-koin:koin-android:3.4.0")
    implementation("io.insert-koin:koin-androidx-compose:3.4.4")

    implementation("com.google.mlkit:text-recognition:16.0.0-beta6")
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("com.google.guava:guava:31.1-jre")

    implementation("com.google.accompanist:accompanist-permissions:0.28.0")

    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("io.realm.kotlin:library-base:1.7.1")
    implementation("com.google.code.gson:gson:2.10.1")
}