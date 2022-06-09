package ua.opu.continent.database.dao.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ua.opu.continent.database.dao.ChatsDao
import ua.opu.continent.database.model.Message
import ua.opu.continent.presentation.adapter.MessagesAdapter

class ChatsDaoFirebase(private var database: FirebaseDatabase) : ChatsDao {


    override suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter) {

        val messages = ArrayList<Message>()
        database.reference.child(CHATS_KEY)
            .child(senderRoom)
            .child(MESSAGES_KEY)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages.clear()
                    for (snapshot1 in snapshot.children) {
                        val message: Message? = snapshot1.getValue(Message::class.java)
                        message!!.messageId = snapshot1.key
                        messages.add(message)
                    }
                    messageAdapter.submitList(messages.toList())
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override suspend fun saveMessage(
        senderRoom: String,
        lastMsgObj: HashMap<String, Any>,
        receiverRoom: String,
        message: Message
    ) {
        val randomKey = database.reference.push().key
        database.reference.child(CHATS_KEY).child(senderRoom).updateChildren(lastMsgObj)
        database.reference.child(CHATS_KEY).child(receiverRoom).updateChildren(lastMsgObj)
        database.reference.child(CHATS_KEY)
            .child(senderRoom)
            .child(MESSAGES_KEY)
            .child(randomKey!!)
            .setValue(message).addOnSuccessListener {
                database.reference.child(CHATS_KEY)
                    .child(receiverRoom)
                    .child(MESSAGES_KEY)
                    .child(randomKey)
                    .setValue(message).addOnSuccessListener { }
            }
    }

    companion object {
        const val CHATS_KEY = "chats"
        const val MESSAGES_KEY = "messages"
    }
}