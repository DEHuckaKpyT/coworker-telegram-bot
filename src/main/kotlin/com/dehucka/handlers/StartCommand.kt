package com.dehucka.handlers

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.chatId
import com.dehucka.library.bot.inject
import com.dehucka.services.user.TelegramUserService
import com.dehucka.templates.registered
import com.dehucka.templates.startAlreadyExists
import com.dehucka.templates.startNew


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
fun BotHandling.startCommand() {

    val telegramUserService by inject<TelegramUserService>()

    command("/start", enableCustomSteps = true) {
        telegramUserService.firstOrNull(chatId)?.run {
            sendMessage(chatId, text = startAlreadyExists with mapOf("name" to fullName))
            finalizeChain(chatId)
        } ?: let {
            sendMessage(chatId, text = startNew)
            nextStep(chatId, "get_full_name")
        }
    }

    step("get_full_name") {
        sendMessage(chatId, text = registered with mapOf("name" to text))

        telegramUserService.create(chatId, fullName = text!!)
    }
}