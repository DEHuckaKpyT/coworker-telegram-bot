package com.dehucka.library.bot

import com.dehucka.library.bot.data.CommandInput


/**
 * Created on 21.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
private val commandRegex by lazy { Regex("^(/[a-zA-Z]+(?:_[a-zA-Z]+)*)(?:__([a-zA-Z0-9-_]+))?(?:@([a-zA-Z_]+))?(?: (.+))?") }

fun TelegramBotChaining.fetchCommand(input: String): CommandInput? {
    val find = commandRegex.find(input) ?: return null
    val groups = find.groups

    val command = groups[1]?.value ?: return null

    val username = groups[3]?.value
    if (username != null && username != this.username) return null

    return CommandInput(command, groups[2]?.value, groups[4]?.value)
}