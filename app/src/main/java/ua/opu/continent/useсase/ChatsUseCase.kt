package ua.opu.continent.useсase

import ua.opu.continent.presentation.adapter.MessagesAdapter
import ua.opu.continent.presentation.dto.MessageCreateDto

interface ChatsUseCase {

    suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter)

    suspend fun sendMessage(message: MessageCreateDto)

    suspend fun sendMessagePhoto(
        messageCreateDto: MessageCreateDto
    )
}