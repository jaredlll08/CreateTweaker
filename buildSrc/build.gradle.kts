plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.blamejared.com") {
        name = "BlameJared"
    }
    maven("https://maven.fabricmc.net")
}

dependencies {
    gradleApi()
    implementation(group = "com.blamejared", name = "gradle-mod-utils", version = "1.0.3")
    implementation(group = "net.darkhax.curseforgegradle", name = "CurseForgeGradle", version = "1.0.10")
    implementation(group = "com.modrinth.minotaur", name = "Minotaur", version = "2.+")
    implementation(group = "com.diluv.schoomp", name = "Schoomp", version = "1.2.6")
    implementation(group = "net.fabricmc", name = "tiny-remapper", version = "0.10.0")
    implementation(group = "net.fabricmc", name = "mapping-io", version = "0.5.1")
}

gradlePlugin {
    plugins {
        create("default") {
            id = "com.blamejared.createtweaker.default"
            implementationClass = "com.blamejared.createtweaker.gradle.DefaultPlugin"
        }
        create("loader") {
            id = "com.blamejared.createtweaker.loader"
            implementationClass = "com.blamejared.createtweaker.gradle.LoaderPlugin"
        }
    }
}
