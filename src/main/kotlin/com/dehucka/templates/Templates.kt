package com.dehucka.templates

import com.dehucka.library.bot.BotHandling
import com.dehucka.library.bot.template


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
val BotHandling.startNew get() = template("start")
val BotHandling.startAlreadyExists get() = template("start-already-exists")
val BotHandling.registered get() = template("registered")
val BotHandling.track get() = template("track")
val BotHandling.projectNotFound get() = template("project-not-found")
val BotHandling.timeForProject get() = template("time-for-project")
val BotHandling.inputHoursError get() = template("input-hours-error")
val BotHandling.successfulInput get() = template("successful-input")