package com.dehucka.library.bot

import com.dehucka.library.source.message.MessageSource
import com.elbekd.bot.Bot
import com.elbekd.bot.model.toChatId
import com.elbekd.bot.types.*


/**
 * Created on 20.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
open class TelegramBotMethods(
    private val bot: Bot,
    private val messageService: MessageSource
) {

    // Telegram methods
    suspend fun getMe(): User = bot.getMe()

    suspend fun getMyCommands(
        scope: BotCommandScope?,
        languageCode: String?
    ): List<BotCommand> = bot.getMyCommands(scope, languageCode)

    suspend fun setMyCommands(
        commands: List<BotCommand>,
        scope: BotCommandScope?,
        languageCode: String?
    ): Boolean = bot.setMyCommands(commands, scope, languageCode)

    suspend fun deleteMyCommands(
        scope: BotCommandScope?,
        languageCode: String?
    ): Boolean = bot.deleteMyCommands(scope, languageCode)

    suspend fun sendMessage(
        chatId: Long,
        text: String,
        messageThreadId: Long? = null,
        parseMode: ParseMode? = null,
        entities: List<MessageEntity>? = null,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean? = null,
        protectContent: Boolean? = null,
        replyToMessageId: Long? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyKeyboard? = null
    ): Message {
        return bot.sendMessage(
            chatId = chatId.toChatId(),
            text = text,
            messageThreadId = messageThreadId,
            parseMode = parseMode,
            entities = entities,
            disableWebPagePreview = disableWebPagePreview,
            disableNotification = disableNotification,
            protectContent = protectContent,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
        ).also {
            messageService.save(chatId, it.from?.id, it.messageId, text)
        }
    }
    // Telegram methods end
}