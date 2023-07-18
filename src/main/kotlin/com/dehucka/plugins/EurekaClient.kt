package com.dehucka.plugins

import com.dehucka.library.plugins.eureka.EurekaClient
import io.ktor.server.application.*


/**
 * Created on 14.01.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
fun Application.configureEurekaClient() {
    install(EurekaClient)
}

