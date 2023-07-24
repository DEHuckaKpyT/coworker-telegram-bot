package com.dehucka.library.bot

import com.dehucka.library.mapper
import com.dehucka.library.source.callback.CallbackContentSource
import com.dehucka.library.source.callback.CallbackContentSourceImpl
import com.dehucka.library.source.chain.ChainSource
import com.dehucka.library.source.chain.ChainSourceImpl
import com.dehucka.library.source.message.MessageSource
import com.dehucka.library.source.message.MessageSourceImpl
import com.dehucka.library.toUUID
import com.dehucka.plugins.TelegramBotTemplate
import com.elbekd.bot.Bot
import com.elbekd.bot.types.CallbackQuery
import com.elbekd.bot.types.Message
import com.fasterxml.jackson.module.kotlin.readValue
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.Version
import io.ktor.server.application.*
import java.io.StringWriter


/**
 * Created on 18.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
open class BotHandling(
    application: Application,
    template: TelegramBotTemplate,
    bot: Bot,
    username: String,
    messageSource: MessageSource = MessageSourceImpl(),
    chainSource: ChainSource = ChainSourceImpl(),
    callbackContentSource: CallbackContentSource = CallbackContentSourceImpl(),
    private val templateConfiguration: Configuration = Configuration(Version("2.3.32"))
) : TelegramBotChaining(application, template, bot, username, messageSource, chainSource, callbackContentSource) {

    inline fun command(
        command: String, nextStep: String? = null,
        enableCustomSteps: Boolean = false,
        crossinline action: suspend Message.(Pair<String?, String?>) -> Unit
    ) {
        actionByCommand[command] = {
            this.action(it)
            if (!enableCustomSteps) {
                chainSource.save(chatId, nextStep)
            }
        }
    }

    inline fun step(
        step: String,
        nextStep: String? = null,
        enableCustomSteps: Boolean = false,
        crossinline action: suspend Message.() -> Unit
    ) {
        actionByStep[step] = {
            this.action()

            if (!enableCustomSteps) {
                chainSource.save(chatId, nextStep)
            }
        }
    }

    inline fun <reified T> step(
        step: String,
        nextStep: String? = null,
        enableCustomSteps: Boolean = false,
        crossinline action: suspend Message.(T) -> Unit
    ) {
        actionByStep[step] = { content ->
            content
                ?: throw RuntimeException("Ожидается экземпляр класса ${T::class.simpleName}, но в chainSource.content ничего не сохранено.")

            val instance = mapper.readValue<T>(content)
            this.action(instance)

            if (!enableCustomSteps) {
                chainSource.save(chatId, nextStep)
            }
        }
    }

    inline fun callback(
        callback: String,
        nextStep: String? = null,
        enableCustomSteps: Boolean = false,
        crossinline action: suspend CallbackQuery.() -> Unit
    ) {
        actionByCallback[callback] = {
            this.action()
            if (!enableCustomSteps) {
                chainSource.save(chatId, nextStep)
            }
        }
    }

    inline fun <reified T> callback(
        callback: String,
        nextStep: String? = null,
        enableCustomSteps: Boolean = false,
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

            if (!enableCustomSteps) {
                chainSource.save(chatId, nextStep)
            }
        }
    }

    infix fun String.with(instance: Any): String {
        val writer = StringWriter()

        try {
            val markerTemplate = Template("template", this, templateConfiguration)
            markerTemplate.process(instance, writer)
        } catch (exc: Exception) {
            throw RuntimeException(exc)
        }

        return writer.toString()
    }
}
