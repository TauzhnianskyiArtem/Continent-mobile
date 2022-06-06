package ua.opu.continent.useсase.impl

import androidx.annotation.WorkerThread
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ua.opu.continent.presentation.adapter.MessagesAdapter
import ua.opu.continent.database.repository.ChatsRepository
import ua.opu.continent.database.model.Message
import ua.opu.continent.presentation.dto.MessageCreateDto
import ua.opu.continent.useсase.ChatsUseCase
import java.util.*

class ChatsUseCaseImpl : ChatsUseCase {

    private val chatsRepository: ChatsRepository = ChatsRepository()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()

    @WorkerThread
    override suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter) {
        chatsRepository.bindToGetAllMessages(senderRoom, messageAdapter)
    }

    @WorkerThread
    override suspend fun sendMessage(messageCreateDto: MessageCreateDto) {
        val senderUid = FirebaseAuth.getInstance().uid
        val date = Date()

        val message = Message.Builder()
            .message(messageCreateDto.message!!)
            .senderId(senderUid!!)
            .timeStamp(date.time)
            .build()

        val lastMsgObj = HashMap<String, Any>()
        lastMsgObj["lastMsg"] = message.message!!
        lastMsgObj["lastMsgTime"] = date.time
        chatsRepository.saveMessage(
            messageCreateDto.senderRoom!!, lastMsgObj,
            messageCreateDto.receiverRoom!!, message
        )
    }

    @WorkerThread
    override suspend fun sendMessagePhoto(
        messageCreateDto: MessageCreateDto
    ) {
        val senderUid = FirebaseAuth.getInstance().uid

        val calendar = Calendar.getInstance()
        val reference = storage.reference.child("chats")
            .child(calendar.timeInMillis.toString())
        reference.putFile(messageCreateDto.uriPhoto!!).addOnCompleteListener { task ->
            task.let(messageCreateDto.onCompleteListener)

            if (task.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { uri ->
                    val filePath = uri.toString()
                    val date = Date()
                    val message = Message.Builder()
                        .message("photo")
                        .senderId(senderUid!!)
                        .imageUrl(filePath)
                        .timeStamp(date.time)
                        .build()

                    val lastMsgObj = HashMap<String, Any>()
                    lastMsgObj["lastMsgTime"] = date.time

                    GlobalScope.launch {
                        chatsRepository.saveMessage(
                            messageCreateDto.senderRoom!!,
                            lastMsgObj,
                            messageCreateDto.receiverRoom!!,
                            message
                        )
                    }
                }
            }
        }
    }
}