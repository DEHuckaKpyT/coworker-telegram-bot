package com.dehucka.handlers

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.chatId
import com.dehucka.library.bot.inject
import com.dehucka.library.exception.CustomException
import com.dehucka.services.project.ProjectService
import com.dehucka.services.tracked.TrackedHourService
import com.dehucka.services.user.TelegramUserService
import com.dehucka.templates.*
import com.elbekd.bot.types.ParseMode.MarkdownV2
import java.util.*


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
fun BotHandling.trackCommand() {

    val projectService by inject<ProjectService>()
    val trackedHourService by inject<TrackedHourService>()
    val telegramUserService by inject<TelegramUserService>()

    command("/track", nextStep = "choose_project") {
        sendMessage(chatId, text = track, parseMode = MarkdownV2)
    }
    step("choose_project", nextStep = "input_hours") {
        val project = projectService.get(text!!) ?: throw CustomException(projectNotFound)

        sendMessage(chatId, timeForProject with project)
        toNextStep(chatId, project.id.value)
    }
    step<UUID>("input_hours") { projectId ->
        val hours = text!!.toDoubleOrNull() ?: throw CustomException(inputHoursError)
        val telegramUser = telegramUserService.first(chatId)

        trackedHourService.create(telegramUser.id.value, projectId, hours)

        sendMessage(chatId, successfulInput with mapOf("hours" to hours))
    }

}