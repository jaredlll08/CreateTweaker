package com.blamejared.createtweaker.gradle

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier

public class Requests private constructor() {
    private val client = HttpClient.newHttpClient()
    private val downloadedFiles: MutableSet<Path> = HashSet()
    private val GSON = GsonBuilder().create()
    operator fun <T> get(uri: String, bodyHandler: Supplier<BodyHandler<T>?>): T {
        return try {
            val request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(uri))
                    .setHeader("User-Agent", "CreateTweaker(https://github.com/jaredlll08/CreateTweaker)")
                    .build()
            val send = client.send(request, bodyHandler.get())
            send.body()
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    fun getJson(uri: String): JsonObject {
        return GSON.fromJson(get(uri, Supplier {
            HttpResponse.BodyHandlers.ofString()
        }), JsonObject::class.java)
    }

    fun getFile(uri: String, file: Path): Path {
        return try {
            System.out.printf("[%s] downloading%n", file)
            val downloaded = downloadedFiles.contains(file)
            if (downloaded || Files.exists(file)) {
                if (!downloaded) {
                    downloadedFiles.add(file)
                }
                System.out.printf("[%s] using cached%n", file)
                return file
            }
            if (file.parent != null) {
                Files.createDirectories(file.parent)
            }
            val path = INSTANCE.get(uri) { HttpResponse.BodyHandlers.ofFile(file) }
            downloadedFiles.add(file)
            System.out.printf("[%s] downloaded%n", file)
            path
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    companion object {
        val INSTANCE = Requests()
    }
}
