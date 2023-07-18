package com.dehucka.plugins

//import org.koin.ksp.generated.defaultModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


/**
 * Created on 11.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(
//            defaultModule,
            convertersModule
        )
    }
}

val convertersModule = module {
}