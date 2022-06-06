package ua.opu.continent.database.dao.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ua.opu.continent.database.dao.PresenceDao

class PresenceDaoFirebase : PresenceDao {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override suspend fun setUserPresence(presence: String) {
        database.reference.child("presence").child(getCurrentId()).setValue(presence)
    }

    override suspend fun bindToGetReceiverStatus(receiverUid: String, getStatus: (String) -> Unit) {
        database.reference.child("presence").child(receiverUid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val status = snapshot.getValue(String::class.java)
                        if (status!!.isNotEmpty()) {
                           status.let(getStatus)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun getCurrentId(): String {
        val currentId = FirebaseAuth.getInstance().uid
        return currentId!!
    }
}
