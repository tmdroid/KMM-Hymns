package de.dannyb.imnuri.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Hymn
import model.Hymns

class Api {

    private val formatter = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }
    private val httpClient = HttpClient {
        install(ContentNegotiation) { json(formatter) }
        install(Logging)
    }

    suspend fun downloadHymns(): List<Hymn> {
        val hymns: Hymns = httpClient.get("http://www.adlabs.ro/imnuri/json-ro").body()
        println("got ${hymns.hymns.size} hymns from the internet")

        return hymns.hymns
    }

}