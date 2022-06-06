package ua.opu.continent.database.dao

interface PresenceDao {

    suspend fun setUserPresence(presence: String)
    suspend fun bindToGetReceiverStatus(receiverUid: String, getStatus: (String) -> Unit)
}
