package com.dehucka.library.source.callback

import com.dehucka.library.model.CallbackContent
import java.util.*


/**
 * Created on 23.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
interface CallbackContentSource {
    suspend fun save(content: String): CallbackContent
    suspend fun get(id: UUID): CallbackContent
}