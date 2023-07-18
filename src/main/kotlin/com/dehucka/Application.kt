package com.dehucka

import com.dehucka.library.applyConfig
import com.dehucka.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking


suspend fun main(args: Array<String>): Unit {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        runBlocking {
            config = applyConfig()
        }

        connector {
            host = config.property("ktor.deployment.host").getString()
            port = config.property("ktor.deployment.port").getString().toInt()
        }
    }).start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureEurekaClient()
    configureDatabase()
    configureTelegramBot()

    enableShutdownUrl()
}
