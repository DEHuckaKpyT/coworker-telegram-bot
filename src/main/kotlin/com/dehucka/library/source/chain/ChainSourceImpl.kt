package com.dehucka.library.source.chain

import com.dehucka.library.database.execute
import com.dehucka.library.database.read
import com.dehucka.library.model.Chain

class ChainSourceImpl : ChainSource {
    override suspend fun save(chatId: Long, step: String?): Chain = execute {
        Chain.findById(chatId)?.apply {
            this.step = step
        } ?: Chain.new(chatId) {
            this.step = step
        }
    }

    override suspend fun save(chatId: Long, step: String?, content: String?): Chain = execute {
        Chain.findById(chatId)?.apply {
            this.step = step
            this.content = content
        } ?: Chain.new(chatId) {
            this.step = step
            this.content = content
        }
    }

    override suspend fun saveContent(chatId: Long, content: String?): Chain = execute {
        Chain.findById(chatId)?.apply {
            this.content = content
        } ?: Chain.new(chatId) {
            this.content = content
        }
    }

    override suspend fun get(chatId: Long): Chain = read { Chain[chatId] }
}