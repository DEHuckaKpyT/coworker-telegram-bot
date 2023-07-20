package com.dehucka.library.bot

import com.dehucka.library.source.MessageSource
import com.elbekd.bot.Bot
import com.elbekd.bot.model.ChatId
import com.elbekd.bot.model.toChatId
import com.elbekd.bot.types.*
import com.elbekd.bot.util.Action
import com.elbekd.bot.util.AllowedUpdate
import com.elbekd.bot.util.SendingDocument
import java.io.File


/**
 * Created on 20.07.2023.
 *<p>
 *
 * @author Denis Matytsin
 */
open class TelegramBotMethods(
    private val bot: Bot,
    private val messageService: MessageSource
) {

    // Telegram methods
    suspend fun getMe(): User = bot.getMe()

    suspend fun logOut(): Boolean = bot.logOut()

    suspend fun close(): Boolean = bot.close()

    suspend fun getUpdates(
        offset: Int?,
        limit: Int?,
        timeout: Int?,
        allowedUpdates: List<AllowedUpdate>?
    ): List<Update> =
        bot.getUpdates(
            offset,
            limit,
            timeout,
            allowedUpdates
        )

    suspend fun getMyCommands(
        scope: BotCommandScope?,
        languageCode: String?
    ): List<BotCommand> = bot.getMyCommands(scope, languageCode)

    suspend fun setMyCommands(
        commands: List<BotCommand>,
        scope: BotCommandScope?,
        languageCode: String?
    ): Boolean = bot.setMyCommands(commands, scope, languageCode)

    suspend fun deleteMyCommands(
        scope: BotCommandScope?,
        languageCode: String?
    ): Boolean = bot.deleteMyCommands(scope, languageCode)

    suspend fun sendMessage(
        chatId: Long,
        text: String,
        messageThreadId: Long? = null,
        parseMode: ParseMode? = null,
        entities: List<MessageEntity>? = null,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean? = null,
        protectContent: Boolean? = null,
        replyToMessageId: Long? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyKeyboard? = null
    ): Message {
        return bot.sendMessage(
            chatId = chatId.toChatId(),
            text = text,
            messageThreadId = messageThreadId,
            parseMode = parseMode,
            entities = entities,
            disableWebPagePreview = disableWebPagePreview,
            disableNotification = disableNotification,
            protectContent = protectContent,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
        ).also {
            messageService.save(chatId, it.from?.id, it.messageId, text)
        }
    }

    suspend fun forwardMessage(
        chatId: ChatId,
        fromChatId: ChatId,
        msgId: Long,
        messageThreadId: Long?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
    ) = bot.forwardMessage(
        chatId = chatId,
        fromChatId = fromChatId,
        msgId = msgId,
        messageThreadId = messageThreadId,
        disableNotification = disableNotification,
        protectContent = protectContent,
    )

    suspend fun copyMessage(
        chatId: ChatId,
        fromChatId: ChatId,
        messageId: Long,
        messageThreadId: Long?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.copyMessage(
        chatId = chatId,
        fromChatId = fromChatId,
        messageId = messageId,
        messageThreadId = messageThreadId,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendPhoto(
        chatId: ChatId,
        photo: SendingDocument,
        messageThreadId: Long?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        hasSpoiler: Boolean?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendPhoto(
        chatId = chatId,
        photo = photo,
        messageThreadId = messageThreadId,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        hasSpoiler = hasSpoiler,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendAudio(
        chatId: ChatId,
        audio: SendingDocument,
        messageThreadId: Long?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        duration: Long?,
        performer: String?,
        title: String?,
        thumb: File?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendAudio(
        chatId = chatId,
        audio = audio,
        messageThreadId = messageThreadId,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        duration = duration,
        performer = performer,
        title = title,
        thumb = thumb,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendDocument(
        chatId: ChatId,
        document: SendingDocument,
        messageThreadId: Long?,
        thumb: File?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        disableContentTypeDetection: Boolean?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendDocument(
        chatId = chatId,
        document = document,
        messageThreadId = messageThreadId,
        thumb = thumb,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        disableContentTypeDetection = disableContentTypeDetection,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendVideo(
        chatId: ChatId,
        video: SendingDocument,
        messageThreadId: Long?,
        duration: Long?,
        width: Long?,
        height: Long?,
        thumb: File?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        hasSpoiler: Boolean?,
        streaming: Boolean?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendVideo(
        chatId = chatId,
        video = video,
        messageThreadId = messageThreadId,
        duration = duration,
        width = width,
        height = height,
        thumb = thumb,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        hasSpoiler = hasSpoiler,
        streaming = streaming,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendAnimation(
        chatId: ChatId,
        animation: SendingDocument,
        messageThreadId: Long?,
        duration: Long?,
        width: Long?,
        height: Long?,
        thumb: File?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        hasSpoiler: Boolean?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendAnimation(
        chatId = chatId,
        animation = animation,
        messageThreadId = messageThreadId,
        duration = duration,
        width = width,
        height = height,
        thumb = thumb,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        hasSpoiler = hasSpoiler,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendVoice(
        chatId: ChatId,
        voice: SendingDocument,
        messageThreadId: Long?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        duration: Long?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendVoice(
        chatId = chatId,
        voice = voice,
        messageThreadId = messageThreadId,
        caption = caption,
        parseMode = parseMode,
        captionEntities = captionEntities,
        duration = duration,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendVideoNote(
        chatId: ChatId,
        note: SendingDocument,
        messageThreadId: Long?,
        duration: Long?,
        length: Long?,
        thumb: File?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendVideoNote(
        chatId = chatId,
        note = note,
        messageThreadId = messageThreadId,
        duration = duration,
        length = length,
        thumb = thumb,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendMediaGroup(
        chatId: ChatId,
        media: List<InputMedia>,
        messageThreadId: Long?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?
    ): ArrayList<Message> {
        if (media.size < 2) throw IllegalArgumentException("List must include 2-10 items")
        return bot.sendMediaGroup(
            chatId = chatId,
            media = media,
            messageThreadId = messageThreadId,
            disableNotification = disableNotification,
            protectContent = protectContent,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply
        )
    }

    suspend fun sendLocation(
        chatId: ChatId,
        latitude: Float,
        longitude: Float,
        messageThreadId: Long?,
        horizontalAccuracy: Float?,
        livePeriod: Long?,
        heading: Long?,
        proximityAlertRadius: Long?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendLocation(
        chatId = chatId,
        latitude = latitude,
        longitude = longitude,
        messageThreadId = messageThreadId,
        horizontalAccuracy = horizontalAccuracy,
        livePeriod = livePeriod,
        heading = heading,
        proximityAlertRadius = proximityAlertRadius,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun editMessageLiveLocation(
        latitude: Float,
        longitude: Float,
        horizontalAccuracy: Float?,
        heading: Long?,
        proximityAlertRadius: Long?,
        chatId: ChatId?,
        messageId: Long?,
        inlineMessageId: String?,
        replyMarkup: InlineKeyboardMarkup?
    ): Message {
        return bot.editMessageLiveLocation(
            latitude = latitude,
            longitude = longitude,
            horizontalAccuracy = horizontalAccuracy,
            heading = heading,
            proximityAlertRadius = proximityAlertRadius,
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            replyMarkup = replyMarkup
        )
    }

    suspend fun stopMessageLiveLocation(
        chatId: ChatId?,
        messageId: Long?,
        inlineMessageId: String?,
        replyMarkup: InlineKeyboardMarkup?
    ): Message {
        return bot.stopMessageLiveLocation(chatId, messageId, inlineMessageId, replyMarkup)
    }

    suspend fun sendVenue(
        chatId: ChatId,
        latitude: Float,
        longitude: Float,
        title: String,
        address: String,
        messageThreadId: Long?,
        foursquareId: String?,
        foursquareType: String?,
        googlePlaceId: String?,
        googlePlaceType: String?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendVenue(
        chatId = chatId,
        latitude = latitude,
        longitude = longitude,
        title = title,
        address = address,
        messageThreadId = messageThreadId,
        foursquareId = foursquareId,
        foursquareType = foursquareType,
        googlePlaceId = googlePlaceId,
        googlePlaceType = googlePlaceType,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendContact(
        chatId: ChatId,
        phoneNumber: String,
        firstName: String,
        messageThreadId: Long?,
        lastName: String?,
        vcard: String?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendContact(
        chatId = chatId,
        phoneNumber = phoneNumber,
        firstName = firstName,
        messageThreadId = messageThreadId,
        lastName = lastName,
        vcard = vcard,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun sendChatAction(
        chatId: ChatId,
        action: Action,
        messageThreadId: Long?,
    ) = bot.sendChatAction(
        chatId = chatId,
        action = action,
        messageThreadId = messageThreadId
    )

    suspend fun setChatMenuButton(chatId: Long?, menuButton: MenuButton?) =
        bot.setChatMenuButton(chatId = chatId, menuButton = menuButton)

    suspend fun getChatMenuButton(chatId: Long?) =
        bot.getChatMenuButton(chatId = chatId)

    suspend fun setMyDefaultAdministratorRights(
        rights: ChatAdministratorRights?,
        forChannels: Boolean?
    ) = bot.setMyDefaultAdministratorRights(rights = rights, forChannels = forChannels)

    suspend fun getMyDefaultAdministratorRights(
        forChannels: Boolean?
    ) = bot.getMyDefaultAdministratorRights(forChannels = forChannels)

    suspend fun getForumTopicIconStickers() = bot.getForumTopicIconStickers()

    suspend fun createForumTopic(
        chatId: ChatId,
        name: String,
        iconColor: Int?,
        iconCustomEmojiId: String?
    ) = bot.createForumTopic(
        chatId = chatId,
        name = name,
        iconColor = iconColor,
        iconCustomEmojiId = iconCustomEmojiId,
    )

    suspend fun editForumTopic(
        chatId: ChatId,
        messageThreadId: Long,
        name: String?,
        iconCustomEmojiId: String?
    ) = bot.editForumTopic(
        chatId = chatId,
        messageThreadId = messageThreadId,
        name = name,
        iconCustomEmojiId = iconCustomEmojiId,
    )

    suspend fun closeForumTopic(
        chatId: ChatId,
        messageThreadId: Long
    ) = bot.closeForumTopic(
        chatId = chatId,
        messageThreadId = messageThreadId,
    )

    suspend fun reopenForumTopic(
        chatId: ChatId,
        messageThreadId: Long
    ) = bot.reopenForumTopic(
        chatId = chatId,
        messageThreadId = messageThreadId,
    )

    suspend fun deleteForumTopic(
        chatId: ChatId,
        messageThreadId: Long
    ) = bot.deleteForumTopic(
        chatId = chatId,
        messageThreadId = messageThreadId,
    )

    suspend fun unpinAllForumTopicMessages(
        chatId: ChatId,
        messageThreadId: Long
    ) = bot.unpinAllForumTopicMessages(
        chatId = chatId,
        messageThreadId = messageThreadId,
    )

    suspend fun editGeneralForumTopic(
        chatId: ChatId,
        name: String
    ) = bot.editGeneralForumTopic(
        chatId = chatId,
        name = name,
    )

    suspend fun closeGeneralForumTopic(
        chatId: ChatId,
    ) = bot.closeGeneralForumTopic(
        chatId = chatId,
    )

    suspend fun reopenGeneralForumTopic(
        chatId: ChatId,
    ) = bot.reopenGeneralForumTopic(
        chatId = chatId,
    )

    suspend fun hideGeneralForumTopic(
        chatId: ChatId,
    ) = bot.hideGeneralForumTopic(
        chatId = chatId,
    )

    suspend fun unhideGeneralForumTopic(
        chatId: ChatId,
    ) = bot.unhideGeneralForumTopic(
        chatId = chatId,
    )

    suspend fun getUserProfilePhotos(
        userId: Long,
        offset: Long?,
        limit: Long?
    ) = bot.getUserProfilePhotos(
        userId = userId,
        offset = offset,
        limit = limit
    )

    suspend fun banChatSenderChat(
        chatId: ChatId,
        senderChatId: Long
    ): Boolean = bot.banChatSenderChat(chatId, senderChatId)

    suspend fun unbanChatSenderChat(
        chatId: ChatId,
        senderChatId: Long
    ): Boolean = bot.unbanChatSenderChat(chatId, senderChatId)

    suspend fun getFile(fileId: String) = bot.getFile(fileId)

    suspend fun banChatMember(
        chatId: ChatId,
        userId: Long,
        untilDate: Long?,
        revokeMessages: Boolean?
    ) = bot.banChatMember(chatId, userId, untilDate, revokeMessages)

    suspend fun unbanChatMember(
        chatId: ChatId,
        userId: Long,
        onlyIfBanned: Boolean?
    ) = bot.unbanChatMember(chatId, userId, onlyIfBanned)

    suspend fun restrictChatMember(
        chatId: ChatId,
        userId: Long,
        permissions: ChatPermissions,
        useIndependentChatPermissions: Boolean?,
        untilDate: Long?,
    ) = bot.restrictChatMember(
        chatId = chatId,
        userId = userId,
        permissions = permissions,
        useIndependentChatPermissions = useIndependentChatPermissions,
        untilDate = untilDate,
    )

    suspend fun promoteChatMember(
        chatId: ChatId,
        userId: Long,
        isAnonymous: Boolean?,
        canManageChat: Boolean?,
        canPostMessages: Boolean?,
        canEditMessages: Boolean?,
        canDeleteMessages: Boolean?,
        canManageVideoChats: Boolean?,
        canRestrictMembers: Boolean?,
        canPromoteMembers: Boolean?,
        canChangeInfo: Boolean?,
        canInviteUsers: Boolean?,
        canPinMessages: Boolean?,
        canManageTopics: Boolean?,
    ): Boolean = bot.promoteChatMember(
        chatId = chatId,
        userId = userId,
        isAnonymous = isAnonymous,
        canManageChat = canManageChat,
        canPostMessages = canPostMessages,
        canEditMessages = canEditMessages,
        canDeleteMessages = canDeleteMessages,
        canManageVideoChats = canManageVideoChats,
        canRestrictMembers = canRestrictMembers,
        canPromoteMembers = canPromoteMembers,
        canChangeInfo = canChangeInfo,
        canInviteUsers = canInviteUsers,
        canPinMessages = canPinMessages,
        canManageTopics = canManageTopics,
    )

    suspend fun exportChatInviteLink(chatId: ChatId) = bot.exportChatInviteLink(chatId)

    suspend fun setChatPhoto(
        chatId: ChatId,
        photo: Any
    ) = bot.setChatPhoto(chatId, photo)

    suspend fun deleteChatPhoto(chatId: ChatId) = bot.deleteChatPhoto(chatId)

    suspend fun setChatTitle(
        chatId: ChatId,
        title: String
    ) = bot.setChatTitle(chatId, title)

    suspend fun setChatDescription(
        chatId: ChatId,
        description: String
    ) = bot.setChatDescription(chatId, description)

    suspend fun pinChatMessage(
        chatId: ChatId,
        messageId: Long,
        disableNotification: Boolean?
    ) = bot.pinChatMessage(chatId, messageId, disableNotification)

    suspend fun unpinChatMessage(
        chatId: ChatId,
        messageId: Long?
    ) = bot.unpinChatMessage(chatId, messageId)

    suspend fun unpinAllChatMessages(chatId: ChatId): Boolean = bot.unpinAllChatMessages(chatId)

    suspend fun leaveChat(chatId: ChatId) = bot.leaveChat(chatId)

    suspend fun getChat(chatId: ChatId) = bot.getChat(chatId)

    suspend fun getChatAdministrators(chatId: ChatId) = bot.getChatAdministrators(chatId)

    suspend fun getChatMemberCount(chatId: ChatId) = bot.getChatMemberCount(chatId)

    suspend fun getChatMember(
        chatId: ChatId,
        userId: Long
    ) = bot.getChatMember(chatId, userId)

    suspend fun setChatStickerSet(
        chatId: ChatId,
        stickerSetName: String
    ) = bot.setChatStickerSet(chatId, stickerSetName)

    suspend fun deleteChatStickerSet(chatId: ChatId) = bot.deleteChatStickerSet(chatId)

    suspend fun answerCallbackQuery(
        callbackQueryId: String,
        text: String?,
        showAlert: Boolean?,
        url: String?,
        cacheTime: Long?
    ) = bot.answerCallbackQuery(
        callbackQueryId = callbackQueryId,
        text = text,
        showAlert = showAlert,
        url = url,
        cacheTime = cacheTime
    )

    suspend fun answerInlineQuery(
        inlineQueryId: String,
        results: List<InlineQueryResult>,
        cacheTime: Int?,
        isPersonal: Boolean?,
        nextOffset: String?,
        switchPmText: String?,
        switchPmParameter: String?
    ) = bot.answerInlineQuery(
        inlineQueryId = inlineQueryId,
        results = results,
        cacheTime = cacheTime,
        isPersonal = isPersonal,
        nextOffset = nextOffset,
        switchPmText = switchPmText,
        switchPmParameter = switchPmParameter
    )

    suspend fun answerWebAppQuery(webAppQueryId: String, result: InlineQueryResult) =
        bot.answerWebAppQuery(
            webAppQueryId = webAppQueryId,
            result = result
        )

    suspend fun editMessageText(
        chatId: ChatId?,
        messageId: Long?,
        inlineMessageId: String?,
        text: String,
        parseMode: ParseMode?,
        entities: List<MessageEntity>?,
        disableWebPagePreview: Boolean?,
        replyMarkup: InlineKeyboardMarkup?
    ): Message {
        return bot.editMessageText(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            text = text,
            parseMode = parseMode,
            entities = entities,
            disableWebPagePreview = disableWebPagePreview,
            replyMarkup = replyMarkup
        )
    }

    suspend fun editMessageCaption(
        chatId: ChatId?,
        messageId: Long?,
        inlineMessageId: String?,
        caption: String?,
        parseMode: ParseMode?,
        captionEntities: List<MessageEntity>?,
        replyMarkup: InlineKeyboardMarkup?
    ): Message {
        return bot.editMessageCaption(
            chatId = chatId,
            messageId = messageId,
            inlineMessageId = inlineMessageId,
            caption = caption,
            parseMode = parseMode,
            captionEntities = captionEntities,
            replyMarkup = replyMarkup
        )
    }

    suspend fun editMessageMedia(
        chatId: ChatId?,
        messageId: Long?,
        inlineMessageId: String?,
        media: InputMedia,
        replyMarkup: InlineKeyboardMarkup?
    ): Message {
        return bot.editMessageMedia(chatId, messageId, inlineMessageId, media, replyMarkup)
    }

    suspend fun editMessageReplyMarkup(
        chatId: ChatId?,
        messageId: Long?,
        inlineMessageId: String?,
        replyMarkup: InlineKeyboardMarkup?
    ): Message {
        return bot.editMessageReplyMarkup(chatId, messageId, inlineMessageId, replyMarkup)
    }

    suspend fun sendSticker(
        chatId: ChatId,
        sticker: Any,
        messageThreadId: Long?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ): Message {
        return bot.sendSticker(
            chatId = chatId,
            sticker = sticker,
            messageThreadId = messageThreadId,
            disableNotification = disableNotification,
            protectContent = protectContent,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
        )
    }

    suspend fun getStickerSet(name: String) = bot.getStickerSet(name)

    suspend fun getCustomEmojiStickers(customEmojiIds: List<String>) =
        bot.getCustomEmojiStickers(customEmojiIds)

    suspend fun uploadStickerFile(
        userId: Long,
        pngSticker: File
    ) = bot.uploadStickerFile(userId, pngSticker)

    suspend fun createNewStickerSet(
        userId: Long,
        name: String,
        title: String,
        emojis: String,
        pngSticker: Any?,
        tgsSticker: File?,
        webmSticker: File?,
        stickerType: String?,
        containsMask: Boolean?,
        maskPosition: MaskPosition?
    ): Boolean {
        return bot.createNewStickerSet(
            userId = userId,
            name = name,
            title = title,
            emojis = emojis,
            pngSticker = pngSticker,
            tgsSticker = tgsSticker,
            webmSticker = webmSticker,
            stickerType = stickerType,
            containsMask = containsMask,
            maskPosition = maskPosition,
        )
    }

    suspend fun addStickerToSet(
        userId: Long,
        name: String,
        emojis: String,
        pngSticker: Any?,
        tgsSticker: File?,
        webmSticker: File?,
        maskPosition: MaskPosition?
    ): Boolean {
        return bot.addStickerToSet(
            userId = userId,
            name = name,
            emojis = emojis,
            pngSticker = pngSticker,
            tgsSticker = tgsSticker,
            webmSticker = webmSticker,
            maskPosition = maskPosition,
        )
    }

    suspend fun setStickerPositionInSet(
        sticker: String,
        position: Int
    ) = bot.setStickerPositionInSet(sticker, position)

    suspend fun deleteStickerFromSet(sticker: String) = bot.deleteStickerFromSet(sticker)

    suspend fun setStickerSetThumb(name: String, userId: Long, thumb: Any?): Boolean {
        if (thumb !is File || thumb !is String) {
            throw IllegalArgumentException("Neither file nor string")
        }

        return bot.setStickerSetThumb(name, userId, thumb)
    }

    suspend fun sendGame(
        chatId: Long,
        gameShortName: String,
        messageThreadId: Long?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: InlineKeyboardMarkup?
    ) = bot.sendGame(
        chatId = chatId,
        gameShortName = gameShortName,
        messageThreadId = messageThreadId,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )

    suspend fun setGameScore(
        userId: Long,
        score: Long,
        force: Boolean?,
        disableEditMessage: Boolean?,
        chatId: ChatId.IntegerId?,
        messageId: Long?, inlineMessageId: String?
    ): Message {
        return bot.setGameScore(userId, score, force, disableEditMessage, chatId, messageId, inlineMessageId)
    }

    suspend fun getGameHighScores(
        userId: Long,
        chatId: ChatId.IntegerId?,
        messageId: Long?,
        inlineMessageId: String?
    ): List<GameHighScore> {
        return bot.getGameHighScores(userId, chatId, messageId, inlineMessageId)
    }

    suspend fun sendInvoice(
        chatId: ChatId,
        title: String,
        description: String,
        payload: String,
        providerToken: String,
        currency: String,
        prices: List<LabeledPrice>,
        messageThreadId: Long?,
        maxTipAmount: Int?,
        suggestedTipAmount: List<Int>?,
        startParameter: String?,
        providerData: String?,
        photoUrl: String?,
        photoSize: Int?,
        photoWidth: Int?,
        photoHeight: Int?,
        needName: Boolean?,
        needPhoneNumber: Boolean?,
        needEmail: Boolean?,
        needShippingAddress: Boolean?,
        sendPhoneNumberToProvider: Boolean?,
        sendEmailToProvider: Boolean?,
        isFlexible: Boolean?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: InlineKeyboardMarkup?
    ): Message = bot.sendInvoice(
        chatId = chatId,
        title = title,
        description = description,
        payload = payload,
        providerToken = providerToken,
        currency = currency,
        prices = prices,
        messageThreadId = messageThreadId,
        maxTipAmount = maxTipAmount,
        suggestedTipAmount = suggestedTipAmount,
        startParameter = startParameter,
        providerData = providerData,
        photoUrl = photoUrl,
        photoSize = photoSize,
        photoWidth = photoWidth,
        photoHeight = photoHeight,
        needName = needName,
        needPhoneNumber = needPhoneNumber,
        needEmail = needEmail,
        needShippingAddress = needShippingAddress,
        sendPhoneNumberToProvider = sendPhoneNumberToProvider,
        sendEmailToProvider = sendEmailToProvider,
        isFlexible = isFlexible,
        protectContent = protectContent,
        disableNotification = disableNotification,
        replyToMessageId = replyToMessageId
    )

    suspend fun createInvoiceLink(
        title: String,
        description: String,
        payload: String,
        providerToken: String,
        currency: String,
        prices: List<LabeledPrice>,
        maxTipAmount: Int?,
        suggestedTipAmount: List<Int>?,
        providerData: String?,
        photoUrl: String?,
        photoSize: Int?,
        photoWidth: Int?,
        photoHeight: Int?,
        needName: Boolean?,
        needPhoneNumber: Boolean?,
        needEmail: Boolean?,
        needShippingAddress: Boolean?,
        sendPhoneNumberToProvider: Boolean?,
        sendEmailToProvider: Boolean?,
        isFlexible: Boolean?
    ): String = bot.createInvoiceLink(
        title = title,
        description = description,
        payload = payload,
        providerToken = providerToken,
        currency = currency,
        prices = prices,
        maxTipAmount = maxTipAmount,
        suggestedTipAmount = suggestedTipAmount,
        providerData = providerData,
        photoUrl = photoUrl,
        photoSize = photoSize,
        photoWidth = photoWidth,
        photoHeight = photoHeight,
        needName = needName,
        needPhoneNumber = needPhoneNumber,
        needEmail = needEmail,
        needShippingAddress = needShippingAddress,
        sendPhoneNumberToProvider = sendPhoneNumberToProvider,
        sendEmailToProvider = sendEmailToProvider,
        isFlexible = isFlexible
    )

    suspend fun answerShippingQuery(
        shippingQueryId: String,
        ok: Boolean,
        shippingOptions: List<ShippingOption>?,
        errorMessage: String?
    ) = bot.answerShippingQuery(shippingQueryId, ok, shippingOptions, errorMessage)

    suspend fun answerPreCheckoutQuery(
        preCheckoutQueryId: String,
        ok: Boolean,
        errorMessage: String?
    ) = bot.answerPreCheckoutQuery(preCheckoutQueryId, ok, errorMessage)

    suspend fun setPassportDataErrors(
        userId: Long,
        errors: List<PassportElementError>
    ) = bot.setPassportDataErrors(userId, errors)

    suspend fun sendPoll(
        chatId: ChatId,
        question: String,
        options: List<String>,
        messageThreadId: Long?,
        isAnonymous: Boolean?,
        type: String?,
        allowsMultipleAnswers: Boolean?,
        correctOptionId: Long?,
        explanation: String?,
        explanationParseMode: String?,
        explanationEntities: List<MessageEntity>?,
        openPeriod: Long?,
        closeDate: Long?,
        isClosed: Boolean?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ): Message {
        if (openPeriod != null && closeDate != null) {
            throw IllegalArgumentException("openPeriod and closeDate can't be used together")
        }

        return bot.sendPoll(
            chatId = chatId,
            question = question,
            options = options,
            messageThreadId = messageThreadId,
            isAnonymous = isAnonymous,
            type = type,
            allowsMultipleAnswers = allowsMultipleAnswers,
            correctOptionId = correctOptionId,
            explanation = explanation,
            explanationParseMode = explanationParseMode,
            explanationEntities = explanationEntities,
            openPeriod = openPeriod,
            closeDate = closeDate,
            isClosed = isClosed,
            disableNotification = disableNotification,
            protectContent = protectContent,
            replyToMessageId = replyToMessageId,
            allowSendingWithoutReply = allowSendingWithoutReply,
            replyMarkup = replyMarkup
        )
    }

    suspend fun stopPoll(chatId: ChatId, messageId: Long, replyMarkup: InlineKeyboardMarkup?): Poll =
        bot.stopPoll(chatId, messageId, replyMarkup)

    suspend fun setChatPermissions(
        chatId: ChatId,
        permissions: ChatPermissions,
        useIndependentChatPermissions: Boolean?,
    ) = bot.setChatPermissions(
        chatId = chatId,
        permissions = permissions,
        useIndependentChatPermissions = useIndependentChatPermissions,
    )

    suspend fun createChatInviteLink(
        chatId: ChatId,
        name: String?,
        expireDate: Long?,
        memberLimit: Long?,
        createsJoinRequest: Boolean?,
    ): ChatInviteLink = bot.createChatInviteLink(
        chatId,
        name,
        expireDate,
        memberLimit,
        createsJoinRequest,
    )

    suspend fun editChatInviteLink(
        chatId: ChatId,
        inviteLink: String,
        name: String?,
        expireDate: Long?,
        memberLimit: Long?,
        createsJoinRequest: Boolean?,
    ): ChatInviteLink = bot.editChatInviteLink(
        chatId,
        inviteLink,
        name,
        expireDate,
        memberLimit,
        createsJoinRequest
    )

    suspend fun revokeChatInviteLink(
        chatId: ChatId,
        inviteLink: String
    ): ChatInviteLink = bot.revokeChatInviteLink(
        chatId,
        inviteLink
    )

    suspend fun approveChatJoinRequest(
        chatId: ChatId,
        inviteLink: String
    ): Boolean = bot.approveChatJoinRequest(
        chatId,
        inviteLink
    )

    suspend fun declineChatJoinRequest(
        chatId: ChatId,
        inviteLink: String
    ): Boolean = bot.declineChatJoinRequest(
        chatId,
        inviteLink
    )

    suspend fun setChatAdministratorCustomTitle(chatId: ChatId, userId: Long, customTitle: String) =
        bot.setChatAdministratorCustomTitle(chatId, userId, customTitle)

    suspend fun deleteMessage(chatId: ChatId, messageId: Long): Boolean =
        bot.deleteMessage(chatId, messageId)

    suspend fun sendDice(
        chatId: ChatId,
        messageThreadId: Long?,
        emoji: String?,
        disableNotification: Boolean?,
        protectContent: Boolean?,
        replyToMessageId: Long?,
        allowSendingWithoutReply: Boolean?,
        replyMarkup: ReplyKeyboard?
    ) = bot.sendDice(
        chatId = chatId,
        messageThreadId = messageThreadId,
        emoji = emoji,
        disableNotification = disableNotification,
        protectContent = protectContent,
        replyToMessageId = replyToMessageId,
        allowSendingWithoutReply = allowSendingWithoutReply,
        replyMarkup = replyMarkup
    )
    // Telegram methods end
}