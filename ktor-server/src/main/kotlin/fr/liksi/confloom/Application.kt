package fr.liksi.confloom

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import fr.liksi.confloom.plugins.configureSerialization
import fr.liksi.plugins.apiController
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME

fun main() {
    System.setProperty(IO_PARALLELISM_PROPERTY_NAME, "1")

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    apiController()
}