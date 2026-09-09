
plugins {
    kotlin("multiplatform") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    id("com.android.library") version "8.5.0"
    id("maven-publish") 
}

kotlin {
    androidTarget {
        publishLibraryVariants("release") 
        
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Latest Coroutines & Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
                
                // Latest Ktor 3.0.0
                implementation("io.ktor:ktor-client-core:3.0.0")
                implementation("io.ktor:ktor-client-content-negotiation:3.0.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")
                implementation("io.ktor:ktor-client-encoding:3.0.0")
            }
        }
        
        val androidMain by getting {
            dependencies {
                // Android के लिए OkHttp इंजन
                implementation("io.ktor:ktor-client-okhttp:3.0.0")
            }
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
