package ua.opu.continent.database.repository

import ua.opu.continent.database.dao.ChatsDao
import ua.opu.continent.database.model.Message
import ua.opu.continent.presentation.adapter.MessagesAdapter

class ChatsRepository(private val chatsDao: ChatsDao) {


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