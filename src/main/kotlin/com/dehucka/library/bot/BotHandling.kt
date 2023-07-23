package com.dehucka.library.bot

import com.dehucka.library.mapper
import com.dehucka.library.source.callback.CallbackContentSource
import com.dehucka.library.source.callback.CallbackContentSourceImpl
import com.dehucka.library.source.chain.ChainSource
import com.dehucka.library.source.chain.ChainSourceImpl
import com.dehucka.library.source.message.MessageSource
import com.dehucka.library.source.message.MessageSourceImpl
import com.dehucka.library.toUUID
import com.elbekd.bot.Bot
import com.elbekd.bot.types.CallbackQuery
import com.elbekd.bot.types.Message
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.server.application.*


/**
 * Created on 18.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
open class BotHandling(
    application: Application,
    bot: Bot,
    username: String,
    messageSource: MessageSource = MessageSourceImpl(),
    chainSource: ChainSource = ChainSourceImpl(),
    callbackContentSource: CallbackContentSource = CallbackContentSourceImpl()
) : TelegramBotChaining(application, bot, username, messageSource, chainSource, callbackContentSource) {

    inline fun command(
        command: String, nextStep: String? = null, crossinline action: suspend Message.(Pair<String?, String?>) -> Unit
    ) {
        actionByCommand[command] = {
            this.action(it)
            chainSource.save(chatId, nextStep)
        }
    }

    inline fun step(step: String, nextStep: String? = null, crossinline action: suspend Message.() -> Unit) {
        actionByStep[step] = {
            this.action()
            chainSource.save(chatId, nextStep)
        }
    }

    inline fun <reified T> step(
        step: String,
        nextStep: String? = null,
        crossinline action: suspend Message.(T) -> Unit
    ) {
        actionByStep[step] = { content ->
            content ?: throw RuntimeException("Ожидается экземпляр класса ${T::class.simpleName}, но в chainSource.content ничего не сохранено.")

            val instance = mapper.readValue<T>(content)
            this.action(instance)
            chainSource.save(chatId, nextStep)
        }
    }

    inline fun callback(
        callback: String,
        nextStep: String? = null,
        crossinline action: suspend CallbackQuery.() -> Unit
    ) {
        actionByCallback[callback] = {
            this.action()
            chainSource.save(chatId, nextStep)
        }
    }

    inline fun <reified T> callback(
        callback: String,
        nextStep: String? = null,
        crossinline action: suspend CallbackQuery.(T) -> Unit
    ) {
        actionByCallback[callback] = { content ->
            val instance = if (content.startsWith('{') || content.startsWith('[')) {
                mapper.readValue<T>(content)
            } else {
                callbackContentSource.get(content.toUUID()).let {
                    mapper.readValue<T>(it.content)
                }
            }

            this.action(instance)

            chainSource.save(chatId, nextStep)
        }
    }
}
