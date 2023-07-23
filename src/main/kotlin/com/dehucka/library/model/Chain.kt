package com.dehucka.library.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable


/**
 * Created on 20.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
object Chains : LongIdTable("chain", "chat_id") {

    val step = varchar("step", 255).nullable()
}

class Chain(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Chain>(Chains)

    var chatId by Chains.id
    var step by Chains.step
}