package ua.opu.continent.database.repository

import ua.opu.continent.presentation.adapter.MessagesAdapter
import ua.opu.continent.database.dao.ChatsDao
import ua.opu.continent.database.dao.impl.ChatsDaoFirebase
import ua.opu.continent.database.model.Message

class ChatsRepository {
    private val chatsDao: ChatsDao = ChatsDaoFirebase()

    suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter) {
        chatsDao.bindToGetAllMessages(senderRoom, messageAdapter)
    }

    suspend fun saveMessage(
        senderRoom: String,
        lastMsgObj: HashMap<String, Any>,
        receiverRoom: String,
        message: Message
    ) {
        chatsDao.saveMessage(senderRoom, lastMsgObj, receiverRoom, message)
    }
}