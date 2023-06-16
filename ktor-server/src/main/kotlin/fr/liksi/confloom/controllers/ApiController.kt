package fr.liksi.plugins

import fr.liksi.client.GreatApiClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.apiController() {
    val greatApiClient: GreatApiClient by inject()

    routing {
        get("/api") {
            call.application.environment.log.info("Receiving request, calling great API")
            val result = greatApiClient.getDoorsStat(5)
            call.application.environment.log.info("Returning")
            call.respond()
        }
    }
}



