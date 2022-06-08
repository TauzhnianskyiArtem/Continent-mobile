package ua.opu.continent.database.dao

import ua.opu.continent.database.model.Message
import ua.opu.continent.presentation.adapter.MessagesAdapter

interface ChatsDao {

    suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter)

    suspend fun saveMessage(
        senderRoom: String,
        lastMsgObj: HashMap<String, Any>,
        receiverRoom: String,
        message: Message
    )
}