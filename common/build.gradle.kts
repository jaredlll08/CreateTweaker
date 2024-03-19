import com.blamejared.createtweaker.gradle.Remapper
import com.blamejared.createtweaker.gradle.Versions
import java.nio.file.Path

plugins {
    java
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
    id("com.blamejared.createtweaker.default")
    id("agency.highlysuspect.crossroad") version "0.3"
}

minecraft {
    version(Versions.MINECRAFT)
}

val remapDir: Path = project.layout.buildDirectory.asFile.map { it.toPath() }.get().resolve("createtweaker_remaps");
val creates = files(crossroad.merge(
        Remapper.remap(project, Versions.MINECRAFT, dependencies.create("com.simibubi.create:create-fabric-${Versions.MINECRAFT}:${Versions.CREATE_FABRIC}") {
            isTransitive = false
        }, remapDir.resolve("createfabric_int2moj.jar")),
        dependencies.create("com.simibubi.create:create-${Versions.MINECRAFT}:${Versions.CREATE_FORGE}:all") {
            isTransitive = false
        }))

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
    val crt = "com.blamejared.crafttweaker:CraftTweaker-common-${Versions.MINECRAFT}:${Versions.CRAFTTWEAKER}"
    compileOnly(crt)
    annotationProcessor("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors:${Versions.CRAFTTWEAKER_ANNOTATION_PROCESSOR}")
    annotationProcessor(crt)

    // Thank you Quat, very cool!
    compileOnly(creates)
}
