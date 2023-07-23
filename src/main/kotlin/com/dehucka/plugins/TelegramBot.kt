package com.dehucka.plugins

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.chatId
import com.dehucka.library.exception.CustomException
import com.dehucka.library.plugins.bot.TelegramBot
import com.elbekd.bot.types.InlineKeyboardButton
import com.elbekd.bot.types.InlineKeyboardMarkup
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
            errorCommand()
            callCommand()
            startCommand()
        }
    }
}

fun BotHandling.errorCommand() {
    command("/error") {
        throw RuntimeException("Любой текст")
    }

    command("/expected_error") {
        throw CustomException("Ожидаемая какая-то ошибка")
    }

    command("/step_error", nextStep = "error_step") {
        sendMessage(chatId, "next error")
    }

    message("error_step") {
        throw CustomException("error on message '$text'")
    }
}

fun BotHandling.callCommand() {
    command("/call") {
        val button =
            InlineKeyboardMarkup(listOf(listOf(InlineKeyboardButton("test callback", callbackData = "teeststestet"))))
        sendMessage(chatId, "test", replyMarkup = button)
    }
}

fun BotHandling.startCommand() {
    command("/start", nextStep = "get_user_name") { (pathParam, lineParam) ->
        sendMessage(chatId = chatId, text = "Введите своё имя")
    }

    message("get_user_name", answerMessage = "get_user_surname") {
        sendMessage(chatId, "Привет, $text!")
//        throw RuntimeException("тестовая ошибка")
        sendMessage(chatId, "А введи, пожалуйста, свою фамилию :)")
    }

    message("get_user_surname") {
        sendMessage(chatId, "А мне нравится Фамилия '$text')")
    }
}