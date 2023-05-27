package de.dannyb.imnuri.networking

import de.dannyb.imnuri.model.Hymn
import de.dannyb.imnuri.model.Hymns
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HymnsApi {

    private val formatter = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }
    private val httpClient = HttpClient {
        install(ContentNegotiation) { json(formatter) }
        install(HttpCache)
        install(Logging)
    }

    suspend fun downloadHymns(): List<Hymn> {
        val hymns: Hymns = httpClient.get("http://www.adlabs.ro/imnuri/json-ro").body()
        println("got ${hymns.hymns.size} hymns from the internet")

        return hymns.hymns
    }

}