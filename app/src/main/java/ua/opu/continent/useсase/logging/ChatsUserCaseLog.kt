package ua.opu.continent.useсase.logging

import android.util.Log
import ua.opu.continent.presentation.adapter.MessagesAdapter
import ua.opu.continent.presentation.dto.MessageCreateDto
import ua.opu.continent.useсase.ChatsUseCase

class ChatsUserCaseLog(private val originalUserCase: ChatsUseCase, private val tag: String?) : ChatsUseCase {

    override suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter) {
        Log.d(tag, "Function: bindToGetAllMessages, Sender Room: $senderRoom")
        originalUserCase.bindToGetAllMessages(senderRoom, messageAdapter)
    }

    override suspend fun sendMessage(message: MessageCreateDto) {
        Log.d(tag, "Function: sendMessage, Message: ${message.message}")
        originalUserCase.sendMessage(message)
    }

    override suspend fun sendMessagePhoto(messageCreateDto: MessageCreateDto) {
        Log.d(tag, "Function: sendMessagePhoto, Uri: ${messageCreateDto.uriPhoto}")
        originalUserCase.sendMessagePhoto(messageCreateDto)
    }
}