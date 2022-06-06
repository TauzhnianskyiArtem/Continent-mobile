package ua.opu.continent.database.dao.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ua.opu.continent.presentation.adapter.MessagesAdapter
import ua.opu.continent.database.dao.ChatsDao
import ua.opu.continent.database.model.Message

class ChatsDaoFirebase : ChatsDao {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()


    override suspend fun bindToGetAllMessages(senderRoom: String, messageAdapter: MessagesAdapter) {

        val messages = ArrayList<Message>()
        database.reference.child("chats")
            .child(senderRoom)
            .child("messages")
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
        database.reference.child("chats").child(senderRoom).updateChildren(lastMsgObj)
        database.reference.child("chats").child(receiverRoom).updateChildren(lastMsgObj)
        database.reference.child("chats")
            .child(senderRoom)
            .child("messages")
            .child(randomKey!!)
            .setValue(message).addOnSuccessListener {
                database.reference.child("chats")
                    .child(receiverRoom)
                    .child("messages")
                    .child(randomKey)
                    .setValue(message).addOnSuccessListener { }
            }
    }
}