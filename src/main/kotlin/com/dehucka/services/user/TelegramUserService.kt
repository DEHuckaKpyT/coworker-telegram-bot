package com.dehucka.services.user

import com.dehucka.models.TelegramUser


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
interface TelegramUserService {
    suspend fun create(chatId: Long, fullName: String): TelegramUser
    suspend fun firstOrNull(chatId: Long): TelegramUser?
    suspend fun first(chatId: Long): TelegramUser
}