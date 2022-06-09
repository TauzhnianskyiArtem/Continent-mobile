package ua.opu.continent.database.repository

import ua.opu.continent.database.dao.PresenceDao

class PresenceRepository(private val presenceDao: PresenceDao) {

    suspend fun setUserPresence(presence: String) {
        presenceDao.setUserPresence(presence)
    }


    suspend fun bindToChangeReceiver(receiverUid: String, getStatus: (String) -> Unit) {
        presenceDao.bindToGetReceiverStatus(receiverUid, getStatus)
    }
}