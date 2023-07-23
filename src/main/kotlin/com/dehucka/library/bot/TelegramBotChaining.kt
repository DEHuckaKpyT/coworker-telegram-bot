package com.dehucka.library.bot

import com.dehucka.library.bot.data.CommandInput
import com.dehucka.library.exception.CustomException
import com.dehucka.library.source.ChainSource
import com.dehucka.library.source.MessageSource
import com.elbekd.bot.Bot
import com.elbekd.bot.types.CallbackQuery
import com.elbekd.bot.types.Message
import com.elbekd.bot.types.ParseMode.MarkdownV2
import com.elbekd.bot.types.Update
import com.elbekd.bot.types.UpdateMessage
import io.ktor.server.application.*


/**
 * Created on 21.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
open class TelegramBotChaining(
    val application: Application,
    private val bot: Bot,
    val username: String,
    private val messageSource: MessageSource,
    protected val chainSource: ChainSource
) : TelegramBotMethods(bot, messageSource) {

    protected val actionByCommand: MutableMap<String, suspend Message.(Pair<String?, String?>) -> Unit> = hashMapOf()
    protected val actionByStep: MutableMap<String, suspend Message.() -> Unit> = hashMapOf()
    protected val actionByCallback: MutableMap<String, suspend CallbackQuery.() -> Unit> = hashMapOf()

    private val whenCommandNotFound: suspend (Long, String) -> Unit = { chatId, command ->
        sendMessage(
            chatId = chatId,
            text = "Введена неизвестная команда `$command`\\. Посмотреть возможные действия можно, вызвав команду /help\\.",
            parseMode = MarkdownV2
        )
    }
    private val whenKnownError: suspend (Long, String) -> Unit = { chatId, message ->
        sendMessage(chatId, text = message)
    }
    private val whenUnknownError: suspend (Long) -> Unit = { chatId ->
        sendMessage(chatId, "Произошла непредвиденная ошибка. Обратитесь к разработчику.")
    }
    private val whenStepNotFound: suspend (Long) -> Unit = { chatId ->
        sendMessage(chatId, "Неожидаемое сообщение. Посмотреть возможные действия можно, вызвав команду /help.")
    }

    init {
        bot.onCallbackQuery {
            val it1 = it
            println(it1)
        }

        bot.onAnyUpdate { update ->
            processUpdate(update)
        }
    }

    suspend fun nextStep(chatId: Long, handler: String) {
        chainSource.save(chatId, handler)
    }

    suspend fun finalizeChain(chatId: Long) {
        chainSource.save(chatId, null)
    }

    private suspend fun processUpdate(update: Update) {
        if (update !is UpdateMessage) return

        val message = update.message
        val text = message.text ?: return
        val chatId = message.chatId

        tryExecute(chatId) {
            messageSource.save(chatId, message.from?.id, message.messageId, text)

            fetchCommand(text)?.let {
                processCommand(it, message)
            } ?: processMessage(message)
        }
    }

    private suspend fun processCommand(input: CommandInput, message: Message) = with(input) {
        actionByCommand[command]
            ?.invoke(message, pathParam to lineParam)
            ?: whenCommandNotFound(message.chatId, command)
    }

    private suspend fun processMessage(message: Message) = with(message) {
        chainSource.get(chatId).step?.let { step ->
            actionByStep[step]
        }?.run {
            this()
        } ?: whenStepNotFound(chatId)
    }

    private suspend fun tryExecute(chatId: Long, block: suspend () -> Unit) {
        try {
            block()
        } catch (exc: CustomException) {
            whenKnownError(chatId, exc.localizedMessage)
        } catch (throwable: Throwable) {
            application.log.error("Unexpected error while handling message in chat $chatId", throwable)
            whenUnknownError(chatId)
        }
    }
}