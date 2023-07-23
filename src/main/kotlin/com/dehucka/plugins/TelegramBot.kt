package com.dehucka.plugins

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.callbackButton
import com.dehucka.library.bot.chatId
import com.dehucka.library.bot.inlineKeyboard
import com.dehucka.library.exception.CustomException
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
            chainCommand()
            chainWithSavingCommand()
            errorCommand()
            callCommand()
        }
    }
}

fun BotHandling.chainWithSavingCommand() {
    data class TestFullName(
        val lastName: String,
        val firstName: String,
        val middleName: String
    )

    command("/chain_with_saving", nextStep = "get_first_name") {
        sendMessage(chatId = chatId, text = "Введи своё имя")
    }

    step("get_first_name", nextStep = "get_last_name") {
        sendMessage(chatId, "Привет, $text!")
        sendMessage(chatId, "Введи фамилию")

        toNextStep(chatId, text!!)
    }

    step<String>("get_last_name", nextStep = "get_middle_name") { name ->
        sendMessage(chatId, "$name $text, введи своё отчество")

        toNextStep(chatId, text to name)
    }

    step<Pair<String, String>>("get_middle_name") { (lastname, firstname) ->
        val user = TestFullName(lastname, firstname, text!!)

        sendMessage(chatId, "Записан такой экземпляр: $user")
    }
}

fun BotHandling.chainCommand() {
    command("/simple_chain", nextStep = "get_user_name") {
        sendMessage(chatId = chatId, text = "Введите своё имя")
    }

    step("get_user_name", nextStep = "get_user_surname") {
        sendMessage(chatId, "Привет, $text!")
        sendMessage(chatId, "А введи, пожалуйста, свою фамилию :)")
    }

    step("get_user_surname") {
        sendMessage(chatId, "А мне нравится фамилия '$text')\nНо у меня нигде не сохранилось твоё имя(")
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
        sendMessage(chatId, "next message will error")
    }

    step("error_step") {
        throw CustomException("error on message '$text'")
    }
}

fun BotHandling.callCommand() {
    data class TestCallback(
        val field: String
    )

    command("/call") {
        val someInstance = TestCallback("some text")
        val bigInstance =
            TestCallback("long text long text long text long text long text long text long text long text long text long text long text long text long text long text long text")

        val keyboard = inlineKeyboard(
            callbackButton("Передача объекта", "with_callback", someInstance),
            callbackButton("Пустой callback", "without_callback"),
            callbackButton("Передача объекта с длиной callback'а больше 64 символов", "with_long_callback", bigInstance)
        )
        sendMessage(chatId, "test text with callback button", replyMarkup = keyboard)
    }

    callback<TestCallback>("with_callback") { testCallback ->
        sendMessage(chatId, "Был передан объект $testCallback")
    }

    callback("without_callback") {
        sendMessage(chatId, "Весь текст callback: $data")
    }

    callback<TestCallback>("with_long_callback") { testCallback ->
        sendMessage(chatId, "Был передан объект $testCallback")
    }
}