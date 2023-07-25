package com.dehucka.services.project

import com.dehucka.library.database.read
import com.dehucka.models.Project
import com.dehucka.models.Projects
import org.koin.core.annotation.Single

@Single
class ProjectServiceImpl : ProjectService {

    override suspend fun list(): List<Project> = read {
        Project.all().toList()
    }

    override suspend fun get(name: String): Project? = read {
        Project.find {
            Projects.name eq name
        }.firstOrNull()
    }
}