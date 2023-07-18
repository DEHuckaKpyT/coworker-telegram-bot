package com.dehucka.handlers.command

import com.elbekd.bot.Bot
import com.elbekd.bot.model.toChatId


/**
 * Created on 17.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
suspend fun Bot.startCommand() {

    sendMessage(chatId = 1165327523L.toChatId(), text = "hi")
}