

plugins {
    //id("java-library")
    //alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.dokka)
}

/*
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

 */

tasks.dokkaHtmlMultiModule{
    moduleName.set("Weather SDK. Shatava OI")
}