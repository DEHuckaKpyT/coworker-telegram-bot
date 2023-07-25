package com.dehucka.services.user

import com.dehucka.library.database.execute
import com.dehucka.library.database.read
import com.dehucka.library.exception.CustomException
import com.dehucka.models.TelegramUser
import com.dehucka.models.TelegramUsers
import org.koin.core.annotation.Single

@Single
class TelegramUserServiceImpl : TelegramUserService {

    override suspend fun create(chatId: Long, fullName: String): TelegramUser = execute {
        TelegramUser.new {
            this.chatId = chatId
            this.fullName = fullName
        }
    }

    override suspend fun firstOrNull(chatId: Long): TelegramUser? = read {
        TelegramUser.find {
            TelegramUsers.chatId eq chatId
        }.limit(1)
            .firstOrNull()
    }

    override suspend fun first(chatId: Long): TelegramUser = read {
        TelegramUser.find {
            TelegramUsers.chatId eq chatId
        }.limit(1)
            .firstOrNull()
            ?: throw CustomException("Не найден пользователь по чату '$chatId'")
    }
}
