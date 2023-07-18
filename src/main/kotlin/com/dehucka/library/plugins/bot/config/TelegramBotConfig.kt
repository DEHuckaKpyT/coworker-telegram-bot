package com.dehucka.library.plugins.bot.config

import com.dehucka.library.bot.BotHandling
import com.elbekd.bot.Bot
import io.ktor.server.config.*


/**
 * Created on 17.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
data class TelegramBotConfig(private val config: ApplicationConfig) {
    var enabled = config.propertyOrNull("enabled")?.getString()?.toBooleanStrict() ?: true
    var token = config.propertyOrNull("token")?.getString()
    var username = config.propertyOrNull("username")?.getString()
    var configureBot: Bot.() -> Unit = {}
    var handling: BotHandling.() -> Unit = {}

    fun configureBot(block: Bot.() -> Unit) {
        configureBot = block
    }

    fun handling(block: BotHandling.() -> Unit) {
        handling = block
    }
}
