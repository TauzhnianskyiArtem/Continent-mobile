package ua.opu.continent.database.repository

import ua.opu.continent.database.dao.PresenceDao
import ua.opu.continent.database.dao.impl.PresenceDaoFirebase

object PresenceRepository {
    private val presenceDao: PresenceDao = PresenceDaoFirebase

    suspend fun setUserPresence(presence: String) {
        presenceDao.setUserPresence(presence)
    }


    suspend fun bindToChangeReceiver(receiverUid: String, getStatus: (String) -> Unit) {
        presenceDao.bindToGetReceiverStatus(receiverUid, getStatus)
    }
}