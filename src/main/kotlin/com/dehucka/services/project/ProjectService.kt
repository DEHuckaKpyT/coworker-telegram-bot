package com.dehucka.services.project

import com.dehucka.models.Project


/**
 * Created on 25.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
interface ProjectService {
    suspend fun list(): List<Project>
    suspend fun get(name: String): Project?
}