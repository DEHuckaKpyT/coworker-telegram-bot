package com.dehucka.library.bot

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
    messageSource: MessageSource = MessageSourceImpl()
) : TelegramBotMethods(bot, messageSource) {

    private val actionByCommand: HashMap<String, suspend Message.(Pair<String?, String?>) -> Unit> = hashMapOf()
    private val actionByHandler: HashMap<String, suspend Message.() -> Unit> = hashMapOf()

    private val commandRegex = Regex("^/([a-zA-Z]+(?:_[a-zA-Z]+)*)(?:__([a-zA-Z0-9-_]+))?(?:@([a-zA-Z_]+))?(?: (.+))?")

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

    suspend fun nextMessage(chatId: Long, handler: String) {
    }

    fun handler(handler: String, action: suspend Message.() -> Unit) {
        actionByHandler[handler] = action
    }

    private suspend fun processUpdateMessage(update: UpdateMessage) {
        val message = update.message
        val text = message.text ?: return

        fetchCommand(text)?.run {
            actionByCommand[command]?.invoke(message, pathParam to lineParam)
                ?: sendMessage(message.chatId, "Команда не найдена")
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
}

data class CommandInput(
    val command: String,
    val pathParam: String?,
    val lineParam: String?
)
