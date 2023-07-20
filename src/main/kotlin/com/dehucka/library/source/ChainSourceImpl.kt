package com.dehucka.library.source

import com.dehucka.library.database.execute
import com.dehucka.library.database.read
import com.dehucka.library.model.Chain

class ChainSourceImpl : ChainSource {

    override suspend fun save(chatId: Long, handler: String?): Chain = execute {
        Chain.findById(chatId)?.apply {
            this.handler = handler
        } ?: Chain.new(chatId) {
            this.handler = handler
        }
    }

    override suspend fun get(chatId: Long): Chain = read { Chain[chatId] }
}