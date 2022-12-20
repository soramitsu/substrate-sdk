buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://nexus.iroha.tech/repository/maven-soramitsu/")
        jcenter()
    }

    val kotlinVersion = project.properties["kotlin_version"].toString()
    val agpVersion = project.properties["agp_version"].toString()
    val rustGradleVersion = project.properties["rust_gradle_version"].toString()

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.android.tools.build:gradle:$agpVersion")
        classpath("org.mozilla.rust-android-gradle:plugin:$rustGradleVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://nexus.iroha.tech/repository/maven-soramitsu/")
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}