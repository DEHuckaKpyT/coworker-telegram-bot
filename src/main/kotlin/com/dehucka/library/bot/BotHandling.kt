package com.dehucka.library.bot

import com.elbekd.bot.Bot
import com.elbekd.bot.types.CallbackQuery
import com.elbekd.bot.types.Message
import io.ktor.server.application.*


/**
 * Created on 18.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
public class BotHandling(
    public val application: Application,
    private val bot: Bot
) {

    fun command(command: String, block: suspend Bot.(Pair<Message, String?>) -> Unit) {
        bot.onCommand("/start") {
            bot.block(it)
        }
    }

    fun command(command: String, step: Int, block: suspend Bot.(Pair<Message, String?>) -> Unit) {
        bot.onCommand("/start") {
            bot.block(it)
        }
    }

    fun handle(handle: String, block: suspend Bot.(CallbackQuery) -> Unit) {

    }
}

class SomeService()