package com.dehucka.library.source

import com.dehucka.library.model.Chain


/**
 * Created on 20.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
interface ChainSource {
    suspend fun save(chatId: Long, handler: String?): Chain
    suspend fun get(chatId: Long): Chain
}