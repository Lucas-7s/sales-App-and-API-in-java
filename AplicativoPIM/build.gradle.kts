// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://repo.mysql.com/maven2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0")
    }
}