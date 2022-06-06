package ua.opu.continent.useсase

interface PresenceUseCase {

    suspend fun setUserPresence(presence: String)


    suspend fun bindToGetReceiverStatus(receiverUid: String, getStatus: (String) -> Unit)
}