package fr.liksi.client

import com.typesafe.config.Config
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

@Single
class GreatApiClient(private val config: Config) {
    private val httpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            connectTimeoutMillis = 1_000_000
            requestTimeoutMillis = 1_000_000
        }
        defaultRequest {
            host = config.getString("ext.great-api.host")
            port = config.getInt("ext.great-api.port")
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