package com.blamejared.createtweaker

import com.blamejared.createtweaker.gradle.Requests
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.fabricmc.mappingio.format.ProGuardReader
import net.fabricmc.mappingio.format.Tiny1Reader
import net.fabricmc.mappingio.format.Tiny2Writer
import net.fabricmc.mappingio.tree.MemoryMappingTree
import net.fabricmc.tinyremapper.OutputConsumerPath
import net.fabricmc.tinyremapper.TinyRemapper
import net.fabricmc.tinyremapper.TinyUtils
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import java.io.*
import java.net.http.HttpResponse
import java.nio.file.Path
import java.util.function.Function
import java.util.function.Supplier
import java.util.stream.StreamSupport
import kotlin.io.path.absolutePathString

object Remapper {

    @Throws(IOException::class)
    fun remap(project: Project, minecraftVersion: String, input: Dependency, output: Path): Path {
        return remap(minecraftVersion, project.configurations.detachedConfiguration(input).singleFile.toPath(), output);
    }

    @Throws(IOException::class)
    fun remap(minecraftVersion: String, input: Path, output: Path): Path {
        val tiny: String = Requests.INSTANCE.get("https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings/%s.tiny".formatted(minecraftVersion), Supplier { HttpResponse.BodyHandlers.ofString() })
        BufferedReader(StringReader(tiny)).use { tinyReader ->
            val memoryMappingTree = MemoryMappingTree()
            ProGuardReader.read(StringReader(getClientMappings(minecraftVersion)), "named", "official", memoryMappingTree)
            Tiny1Reader.read(tinyReader, memoryMappingTree)
            val writer = StringWriter()
            memoryMappingTree.accept(Tiny2Writer(writer, false))
            writer.close()
            val remapper = TinyRemapper.newRemapper()
                    .withMappings(TinyUtils.createTinyMappingProvider(BufferedReader(StringReader(writer.toString())), "intermediary", "named"))
                    .build()
            remapper.readInputs(input)
            OutputConsumerPath.Builder(output).build().use { consumer -> remapper.apply(consumer) }
            remapper.finish()
        }
        return output;
    }

    fun getClientMappings(minecraftVersion: String): String {

        val manifest: JsonObject = Requests.INSTANCE.getJson("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json")
        val mappingUrl = StreamSupport.stream<JsonElement>(manifest.getAsJsonArray("versions").spliterator(), false)
                .map<JsonObject> { obj: JsonElement -> obj.getAsJsonObject() }
                .filter { jsonObject: JsonObject -> jsonObject["id"].asString == minecraftVersion }
                .map<JsonElement> { jsonObject: JsonObject -> jsonObject["url"] }
                .map<String> { obj: JsonElement -> obj.asString }
                .map<JsonObject>(Function<String, JsonObject> { url: String -> Requests.INSTANCE.getJson(url) })
                .map<JsonObject> { jsonObject: JsonObject -> jsonObject.getAsJsonObject("downloads") }
                .map<JsonObject> { jsonObject: JsonObject -> jsonObject.getAsJsonObject("client_mappings") }
                .map<JsonElement> { jsonObject: JsonObject -> jsonObject["url"] }
                .map<String> { obj: JsonElement -> obj.asString }
                .findFirst()
        return mappingUrl.map<String>(Function<String, String> { s: String -> Requests.INSTANCE.get<String>(s, Supplier { HttpResponse.BodyHandlers.ofString() }) }).orElseThrow()
    }
}
