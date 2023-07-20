package com.dehucka.library.source

import com.dehucka.library.database.execute
import com.dehucka.library.model.TelegramMessage

class MessageSourceImpl : MessageSource {

    override suspend fun save(
        chatId: Long,
        fromId: Long?,
        messageId: Long,
        text: String?
    ): TelegramMessage = execute {
        TelegramMessage.new {
            this.chatId = chatId
            this.fromId = fromId
            this.messageId = messageId
            this.text = text
        }
    }
}