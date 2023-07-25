package com.dehucka.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
object Projects : UUIDTable("project") {

    val name = text("name").uniqueIndex()
}

class Project(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Project>(Projects)

    var name by Projects.name
}