package com.dehucka.plugins

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.chatId
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
        handling {
            startCommand()
            otherHandler()
        }
    }
}

fun BotHandling.startCommand() {
    command("start") { (pathParam, lineParam) ->
        sendMessage(chatId = chatId, text = "param from path = '$pathParam', param from line = '$lineParam'")

        nextMessage(chatId, "get_user_name")
    }
}

fun BotHandling.otherHandler() {

    handler("get_user_name") {

        sendMessage(chatId = chatId, text = "hand")
    }
}