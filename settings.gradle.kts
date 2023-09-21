pluginManagement {
    repositories {
        maven("https://maven.blamejared.com")
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "Sponge Snapshots"
        }
        //TODO When Crossroad has inner class support, uncomment and remove the git module
//        maven("https://repo.sleeping.town/") {}
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "CreateTweaker"
include("common")
include("fabric")
include("forge")
includeBuild("crossroad")