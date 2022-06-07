package ua.opu.continent.useсase.impl

import androidx.annotation.WorkerThread
import ua.opu.continent.database.repository.PresenceRepository
import ua.opu.continent.useсase.PresenceUseCase

class PresenceUseCaseFirebase : PresenceUseCase {
    private val presenceRepository: PresenceRepository = PresenceRepository()

    @WorkerThread
    override suspend fun setUserPresence(presence: String){
        presenceRepository.setUserPresence(presence)
    }


    @WorkerThread
    override suspend fun bindToGetReceiverStatus(receiverUid: String, getStatus: (String) -> Unit){
        presenceRepository.bindToChangeReceiver(receiverUid, getStatus)
    }

    companion object{
        const val PRESENCE_ONLINE = "Online"
        const val PRESENCE_OFFLINE = "Offline"
        const val PRESENCE_TYPING = "typing ..."
    }
}