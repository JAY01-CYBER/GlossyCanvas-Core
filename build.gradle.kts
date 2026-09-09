/*
 * ╭────────────────────────────────────────────╮
 * │             Glossy Canvas Core             │
 * │--------------------------------------------│
 * │  Licensed under the GNU GPL v3.0           │
 * │  Crafted for expressive music experience   │
 * ╰────────────────────────────────────────────╯
 */

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }

    // अगर फ्यूचर में iOS या Desktop का प्लान हो, तो यहाँ उनके टारगेट ऐड कर सकते हो

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.encoding)
        }
        
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "com.j.glossycanvas.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
