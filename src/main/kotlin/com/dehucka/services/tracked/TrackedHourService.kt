package com.dehucka.services.tracked

import com.dehucka.models.TrackedHour
import java.util.*


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
interface TrackedHourService {
    suspend fun create(telegramUserId: UUID, projectId: UUID, hours: Double): TrackedHour
}