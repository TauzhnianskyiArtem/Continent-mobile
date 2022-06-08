package ua.opu.continent.useсase.logging

import android.util.Log
import ua.opu.continent.useсase.PresenceUseCase

class PresenceUserCaseLog(private val originalUserCase: PresenceUseCase, private val tag: String?) :
    PresenceUseCase {
    override suspend fun setUserPresence(presence: String) {
        Log.d(tag, "Function: setUserPresence")
        originalUserCase.setUserPresence(presence)
    }

    override suspend fun bindToGetReceiverStatus(receiverUid: String, getStatus: (String) -> Unit) {
        Log.d(tag, "Function: bindToGetReceiverStatus, receiverUid: $receiverUid")
        originalUserCase.bindToGetReceiverStatus(receiverUid, getStatus)
    }


}