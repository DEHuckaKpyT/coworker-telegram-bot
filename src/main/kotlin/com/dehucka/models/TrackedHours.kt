package com.dehucka.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
object TrackedHours : UUIDTable("tracked_hour") {

    val telegramUserId = reference("telegram_user_id", TelegramUsers)
    val projectId = reference("project_id", Projects)
    val hours = double("hours")
}

class TrackedHour(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TrackedHour>(TrackedHours)

    var telegramUserId by TrackedHours.telegramUserId
    var projectId by TrackedHours.projectId
    var hours by TrackedHours.hours

    val telegramUser by TelegramUser referencedOn TrackedHours.telegramUserId
    val project by Project referencedOn TrackedHours.projectId
}