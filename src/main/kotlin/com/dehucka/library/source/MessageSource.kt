package com.dehucka.library.source

import com.dehucka.library.model.TelegramMessage


/**
 * Created on 20.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
interface MessageSource {

    suspend fun save(chatId: Long, fromId: Long?, messageId: Long, text: String?): TelegramMessage
}