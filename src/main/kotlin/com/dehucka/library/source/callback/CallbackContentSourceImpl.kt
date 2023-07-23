package com.dehucka.library.source.callback

import com.dehucka.library.database.execute
import com.dehucka.library.database.read
import com.dehucka.library.exception.CustomException
import com.dehucka.library.model.CallbackContent
import java.util.*

class CallbackContentSourceImpl : CallbackContentSource {

    override suspend fun save(content: String): CallbackContent = execute {
        CallbackContent.new {
            this.content = content
        }
    }

    override suspend fun get(id: UUID): CallbackContent = read {
        CallbackContent.findById(id)
            ?: throw CustomException("Содержание для callback'а не найдено :(")
    }
}