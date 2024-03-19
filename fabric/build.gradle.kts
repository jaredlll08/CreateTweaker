import com.blamejared.gradle.mod.utils.GMUtils
import com.blamejared.createtweaker.gradle.Properties
import com.blamejared.createtweaker.gradle.Versions
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.darkhax.curseforgegradle.Constants as CFG_Constants

plugins {
    id("fabric-loom") version "1.5-SNAPSHOT"
    id("com.blamejared.createtweaker.default")
    id("com.blamejared.createtweaker.loader")
    id("com.modrinth.minotaur")
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC}")
    implementation(project(":common"))
    val crt = "com.blamejared.crafttweaker:CraftTweaker-fabric-${Versions.MINECRAFT}:${Versions.CRAFTTWEAKER}"
    modImplementation(crt)
    modImplementation("com.faux.fauxcustomentitydata:FauxCustomEntityData-fabric-${Versions.MINECRAFT}:${Versions.FAUX_CUSTOM_ENTITY_DATA}")
    modImplementation("com.jozufozu.flywheel:flywheel-fabric-${Versions.MINECRAFT}:${Versions.FLYWHEEL_FABRIC}")
    modImplementation("com.simibubi.create:create-fabric-${Versions.MINECRAFT}:${Versions.CREATE_FABRIC}")
    modLocalRuntime("mezz.jei:jei-${Versions.MINECRAFT}-fabric:${Versions.JEI}")
//    annotationProcessor("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors:${Versions.CRAFTTWEAKER_ANNOTATION_PROCESSOR}")
//    annotationProcessor(crt)
}

loom {
    mixin {
        defaultRefmapName.set("${Properties.MODID}.refmap.json")
    }
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks.create<TaskPublishCurseForge>("publishCurseForge") {
    apiToken = GMUtils.locateProperty(project, "curseforgeApiToken")

    val mainFile = upload(Properties.CURSE_PROJECT_ID, file("${project.buildDir}/libs/${base.archivesName.get()}-$version.jar"))
    mainFile.changelogType = "markdown"
    mainFile.changelog = GMUtils.smallChangelog(project, Properties.GIT_REPO)
    mainFile.releaseType = CFG_Constants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java ${Versions.JAVA}")
    mainFile.addGameVersion(Versions.MINECRAFT)
    mainFile.addRequirement("crafttweaker")
    mainFile.addRequirement("create-fabric")
    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE}/files/${mainFile.curseFileId}")
    }
}

modrinth {
    token.set(GMUtils.locateProperty(project, "modrinth_token"))
    projectId.set(Properties.MODRINTH_PROJECT_ID)
    changelog.set(GMUtils.smallChangelog(project, Properties.GIT_REPO))
    versionName.set("Fabric-${Versions.MINECRAFT}-$version")
    versionType.set("release")
    uploadFile.set(tasks.remapJar.get())
    dependencies {
        required.project("crafttweaker")
        required.project("create-fabric")
    }
}
