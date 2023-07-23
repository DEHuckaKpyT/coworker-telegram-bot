package com.dehucka.library.bot

import com.dehucka.library.source.ChainSource
import com.dehucka.library.source.ChainSourceImpl
import com.dehucka.library.source.MessageSource
import com.dehucka.library.source.MessageSourceImpl
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
class BotHandling(
    application: Application,
    bot: Bot,
    username: String,
    messageSource: MessageSource = MessageSourceImpl(),
    chainSource: ChainSource = ChainSourceImpl()
) : TelegramBotChaining(application, bot, username, messageSource, chainSource) {

    fun command(
        command: String, nextStep: String? = null, action: suspend Message.(Pair<String?, String?>) -> Unit
    ) {
        actionByCommand[command] = {
            this.action(it)
            chainSource.save(chatId, nextStep)
        }
    }

    fun message(step: String, answerMessage: String? = null, action: suspend Message.() -> Unit) {
        actionByStep[step] = {
            this.action()
            chainSource.save(chatId, answerMessage)
        }
    }

    fun callback(callback: String, answerMessage: String? = null, action: suspend CallbackQuery.() -> Unit) {
        actionByCallback[callback] = {
            this.action()
            chainSource.save(chatId, answerMessage)
        }
    }
}
