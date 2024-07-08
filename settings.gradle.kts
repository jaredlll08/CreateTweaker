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
        maven("https://repo.sleeping.town/") { name = "minivan"  }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "CreateTweaker"
include("common")
include("fabric")
include("forge")
includeBuild("crossroad")