package fr.liksi.plugins

import fr.liksi.confloom.client.GreatApiClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.apiController() {
    val greatApiClient = GreatApiClient("http://localhost:8090")

    routing {
        get("/api") {
            call.application.environment.log.info("Receiving request, calling great API")

            val doorName = greatApiClient.getDoorsStat(3)
                .minByOrNull { it.nbOpening }?.doorName
                ?: "RUN !!!"

            call.application.environment.log.info("Returning")
            call.respond(doorName)
        }
    }
}



