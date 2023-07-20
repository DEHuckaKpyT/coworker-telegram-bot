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
    command("/start", answerHandler = "get_user_name") { (pathParam, lineParam) ->
        sendMessage(chatId = chatId, text = "Введите своё имя")
    }

    messageHandler("get_user_name", answerHandler = "get_user_surname") {
        sendMessage(chatId, "Привет, $text!")
//        throw RuntimeException("тестовая ошибка")
        sendMessage(chatId, "А введи, пожалуйста, свою фамилию :)")
    }

    messageHandler("get_user_surname") {
        sendMessage(chatId, "А мне нравится Фамилия '$text')")
    }
}

fun BotHandling.otherHandler() {

    handler("other_handler") {

        sendMessage(chatId = chatId, text = "hand")
    }
}