package com.dehucka.plugins

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.SomeService
import com.dehucka.library.bot.inject
import com.dehucka.library.plugins.bot.TelegramBot
import com.elbekd.bot.model.toChatId
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

    command("/start") { (message, text) ->
        sendMessage(chatId = message.chat.id.toChatId(), text = "Введите имя")
    }

    command("/start", step = 1) { (message, text) ->
        sendMessage(chatId = message.chat.id.toChatId(), text = "Здравствуйте, $text!")
        sendMessage(message.chat.id.toChatId(), "Очень рад Вас видеть!")
    }
}

fun BotHandling.otherHandler() {
    val someService = inject<SomeService>()

    handle("other_after_button_press") { callback ->
        sendMessage(chatId = callback.from.id.toChatId(), text = "${callback.data}")
    }
}