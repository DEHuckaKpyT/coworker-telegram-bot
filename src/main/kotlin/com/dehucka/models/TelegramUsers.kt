package com.dehucka.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
object TelegramUsers : UUIDTable("telegram_user") {

    val chatId = long("chat_id").uniqueIndex()
    val fullName = varchar("full_name", 255)
    val createdDate = datetime("created_date").defaultExpression(CurrentDateTime)
}

class TelegramUser(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TelegramUser>(TelegramUsers)

    var chatId by TelegramUsers.chatId
    var fullName by TelegramUsers.fullName
    val createdDate by TelegramUsers.createdDate
}