package fr.liksi.confloom.client

import com.typesafe.config.Config
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

class GreatApiClient(private val baseUrl: String) {
    private val httpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            connectTimeoutMillis = 1_000_000
            requestTimeoutMillis = 1_000_000
        }
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url(baseUrl)
        }
    }

    suspend fun getDoorsStat(delay: Int): List<ApiDoorStat> {
        return httpClient.get("/get-doors-stats") {
            parameter("delay", delay)
        }.body()
    }
}
@Serializable
data class ApiDoorStat(
    val doorName: String,
    val nbOpening: Int
)