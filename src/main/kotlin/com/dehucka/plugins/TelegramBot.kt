package com.dehucka.plugins

import com.dehucka.handlers.startCommand
import com.dehucka.handlers.trackCommand
import com.dehucka.library.plugins.bot.TelegramBot
import io.ktor.server.application.*


/**
 * Created on 16.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
fun Application.configureTelegramBot() {
    install(TelegramBot) {
        configureTemplating {
            defaultEncoding = "UTF-8"
        }

        handling {
            startCommand()
            trackCommand()
        }
    }
}