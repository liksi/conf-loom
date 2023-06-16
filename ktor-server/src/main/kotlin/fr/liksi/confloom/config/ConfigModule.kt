package fr.liksi.config

import com.typesafe.config.ConfigFactory
import org.koin.dsl.module

val configModule = module {
    single { ConfigFactory.load() }
}