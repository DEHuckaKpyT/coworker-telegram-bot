package com.dehucka.library.bot

import com.dehucka.library.source.ChainSource
import com.dehucka.library.source.ChainSourceImpl
import com.dehucka.library.source.MessageSource
import com.dehucka.library.source.MessageSourceImpl
import com.elbekd.bot.Bot
import com.elbekd.bot.types.Message
import com.elbekd.bot.types.UpdateMessage
import io.ktor.server.application.*


/**
 * Created on 18.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
class BotHandling(
    val application: Application,
    private val bot: Bot,
    private val username: String,
    private val messageSource: MessageSource = MessageSourceImpl(),
    private val chainSource: ChainSource = ChainSourceImpl()
) : TelegramBotMethods(bot, messageSource) {

    private val actionByCommand: MutableMap<String, suspend Message.(Pair<String?, String?>) -> Unit> = hashMapOf()
    private val actionByHandler: MutableMap<String, suspend Message.() -> Unit> = hashMapOf()

    private val commandRegex = Regex("^(/[a-zA-Z]+(?:_[a-zA-Z]+)*)(?:__([a-zA-Z0-9-_]+))?(?:@([a-zA-Z_]+))?(?: (.+))?")

    init {
        bot.onAnyUpdate { update ->
            when (update) {
                is UpdateMessage -> processUpdateMessage(update)
                else -> {}
            }
        }
    }

    fun command(command: String, action: suspend Message.(Pair<String?, String?>) -> Unit) {
        actionByCommand[command] = action
    }

    fun command(command: String, answerHandler: String, action: suspend Message.(Pair<String?, String?>) -> Unit) {
        actionByCommand[command] = {
            this.action(it)
            chainSource.save(chatId, answerHandler)
        }
    }

    fun messageHandler(handler: String, answerHandler: String, action: suspend Message.() -> Unit) {
        actionByHandler[handler] = {
            this.action()
            chainSource.save(chatId, answerHandler)
        }
    }

    fun messageHandler(handler: String, action: suspend Message.() -> Unit) {
        actionByHandler[handler] = {
            this.action()
            chainSource.save(chatId, null)
        }
    }

    suspend fun nextMessage(chatId: Long, handler: String) {
        chainSource.save(chatId, handler)
    }

    suspend fun end(chatId: Long) {
        chainSource.save(chatId, null)
    }

    fun handler(handler: String, action: suspend Message.() -> Unit) {
        actionByHandler[handler] = action
    }

    private suspend fun processUpdateMessage(update: UpdateMessage) {
        val message = update.message
        val text = message.text ?: return
        val chatId = message.chatId

        messageSource.save(chatId, message.from?.id, message.messageId, text)

        fetchCommand(text)?.run {
            actionByCommand[command]?.invoke(message, pathParam to lineParam)
                ?: sendMessage(chatId, "Команда '$command' не найдена")
        } ?: chainSource.get(chatId).tryExecute(chatId) {
            actionByHandler[handler]?.invoke(message)
                ?: sendMessage(chatId, "Цепочка закончена")
        }
    }

    private fun fetchCommand(input: String): CommandInput? {
        val find = commandRegex.find(input) ?: return null
        val groups = find.groups

        val command = groups[1]?.value ?: return null

        val username = groups[3]?.value
        if (username != null && username != this.username) return null

        return CommandInput(command, groups[2]?.value, groups[4]?.value)
    }

    private suspend fun <T> T.tryExecute(chatId: Long, block: suspend T.() -> Unit) {
        try {
            block()
        } catch (throwable: Throwable) {
            sendMessage(chatId, throwable.localizedMessage)
        }
    }
}

data class CommandInput(
    val command: String,
    val pathParam: String?,
    val lineParam: String?
)
