package com.dehucka.services.tracked

import com.dehucka.library.database.execute
import com.dehucka.models.Projects
import com.dehucka.models.TelegramUsers
import com.dehucka.models.TrackedHour
import org.jetbrains.exposed.dao.id.EntityID
import org.koin.core.annotation.Single
import java.util.*

@Single
class TrackedHourServiceImpl : TrackedHourService {

    override suspend fun create(telegramUserId: UUID, projectId: UUID, hours: Double): TrackedHour = execute {
        TrackedHour.new {
            this.telegramUserId = EntityID(telegramUserId, TelegramUsers)
            this.projectId = EntityID(projectId, Projects)
            this.hours = hours
        }
    }
}