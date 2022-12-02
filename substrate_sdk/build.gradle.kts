import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("native.cocoapods")
    id("maven-publish")
    id("org.mozilla.rust-android-gradle.rust-android")
}

group = "jp.co.soramitsu"
version = "0.0.1"

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "jp.co.soramitsu"
            artifactId = "x_substrate_sdk"
            version = "0.0.43"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "scnRepo"
            url = uri(if (hasProperty("RELEASE_REPOSITORY_URL")) property("RELEASE_REPOSITORY_URL")!! else System.getenv()["RELEASE_REPOSITORY_URL"]!!)
            credentials {
                username = if (hasProperty("NEXUS_USERNAME")) (property("NEXUS_USERNAME") as String) else System.getenv()["NEXUS_USERNAME"]
                password = if (hasProperty("NEXUS_PASSWORD")) (property("NEXUS_PASSWORD") as String) else System.getenv()["NEXUS_PASSWORD"]
            }
        }
        maven {
            name = "scnRepoLocal"
            url = uri("${project.buildDir}/scnrepo")
        }
    }
}

val iosFrameworkName = "SubstrateSdk"
val xcFramework = XCFramework()
val iosConfigure: KotlinNativeTarget.() -> Unit = {
    binaries.framework {
        baseName = iosFrameworkName
        xcFramework.add(this)
    }
}

val coroutineVersion = "1.6.4"
val ktorVersion = "2.0.0"

kotlin {
    android()

    iosX64(iosConfigure)
    iosArm64(iosConfigure)
    iosSimulatorArm64(iosConfigure)

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = iosFrameworkName
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.serialization.json)

                implementation(libs.coroutines.core)
                implementation(libs.bundles.ktor)
                implementation(libs.bignum)
                implementation(libs.bytebuffer)
                implementation(libs.datetime)
                implementation(libs.krypto)
                implementation(libs.okio)
                implementation(libs.cryptohash)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.ktor.mock)
                implementation(libs.coroutines.test)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.ktor.okhttp)

                implementation(libs.polkajscale)
                implementation(libs.coroutines.android)
                implementation(libs.bundles.crypto.android)
                implementation(libs.lz4)
            }

            cargo {
                module = "src/androidMain/sr25519-java"
                libname = "sr25519java_substrate_sdk"
                targets = listOf("arm", "arm64", "x86", "x86_64")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.darwin)
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
    android {
        publishAllLibraryVariants()
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    ndkVersion = "21.3.6528147"
}

tasks.register<Copy>("copyiOSTestResources") {
    from("src/iosTest/resources")
    into("build/bin/iosX64/debugTest/resources")
}

tasks.findByName("iosX64Test")!!.dependsOn("copyiOSTestResources")
